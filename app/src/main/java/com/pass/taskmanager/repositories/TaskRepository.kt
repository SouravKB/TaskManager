package com.pass.taskmanager.repositories

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.pass.taskmanager.utils.Response
import com.pass.taskmanager.models.Task
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.asDeferred
import kotlinx.coroutines.tasks.await

object TaskRepository {
    private const val KEY_PROJECT = "project"
    private val baseRef = Firebase.firestore.collection(KEY_PROJECT)

    fun getTask(taskID : String) = callbackFlow<Response<Task>> {
        trySend(Response.Loading)
        val listener = baseRef.document(taskID).addSnapshotListener { value, error ->
            if (value != null) {
                val project = value.toObject<Task>()!!.also {
                    it.tid = taskID;
                }
                trySend(Response.Success(project))
            }
        }
        awaitClose {
            listener.remove()
        }
    }

    fun getUserIDs(taskID: String) = callbackFlow<Response<List<String>>> {
        trySend(Response.Loading)
        val listener = baseRef.document(taskID).addSnapshotListener { value, error ->
            trySend(Response.Success(value!!.get("userIDs") as List<String>))
        }
        awaitClose {
            listener.remove()
        }
    }


    suspend fun setTask(task: Task) : DocumentReference {
        val docRef = baseRef.add(task).await()
        return docRef
    }

    suspend fun getTasktList(taskIDList : List<String>) : List<Task> {
        var taskPromiseList= mutableListOf<Deferred<DocumentSnapshot>>()
        for (tid in taskIDList){
            taskPromiseList += baseRef.document(tid).get().asDeferred()
        }
        return taskPromiseList.awaitAll().map { it.toObject<Task>()!! }
    }
}
