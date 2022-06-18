package com.pass.taskmanager.repositories

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.pass.taskmanager.models.Person
import com.pass.taskmanager.models.Project
import com.pass.taskmanager.utils.Response
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.asDeferred
import kotlinx.coroutines.tasks.await

object ProjectRepository {
    private const val KEY_PROJECT = "project"
    private val baseRef = Firebase.firestore.collection(KEY_PROJECT)

    fun getProject(projectID : String) = callbackFlow<Response<Project>> {
        trySend(Response.Loading)
        val listener = baseRef.document(projectID).addSnapshotListener { value, error ->
            if (value != null) {
                val project = value.toObject<Project>()!!.also {
                    it.pid = projectID;
                }
                trySend(Response.Success(project))
            }
        }
        awaitClose {
            listener.remove()
        }
    }

    fun getTaskIDs(projectID: String) = callbackFlow<Response<List<String>>> {
        trySend(Response.Loading)
        val listener = baseRef.document(projectID).addSnapshotListener { value, error ->
            trySend(Response.Success(value!!.get("taskIDs") as List<String>))
        }

    }


    suspend fun setProject(project: Project) : DocumentReference {
        val docRef = baseRef.add(project).await()
        return docRef
    }

    suspend fun getProjectList(projectIDList : List<String>) : List<Project> {
        var projectPromiseList= mutableListOf<Deferred<DocumentSnapshot>>()
        for (pid in projectIDList){
            projectPromiseList += baseRef.document(pid).get().asDeferred()
        }
        return projectPromiseList.awaitAll().map { it.toObject<Project>()!! }
    }
}