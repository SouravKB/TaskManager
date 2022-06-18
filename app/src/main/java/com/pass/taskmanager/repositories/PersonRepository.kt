package com.pass.taskmanager.repositories

import android.content.ContentValues.TAG
import android.util.Log
import com.google.android.gms.tasks.Task as GTask
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.pass.taskmanager.models.Person
import com.pass.taskmanager.models.Response
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.asDeferred
import kotlinx.coroutines.tasks.await
import java.util.concurrent.Flow
import kotlin.coroutines.coroutineContext

object PersonRepository {

    private const val KEY_PERSON = "person"
    private const val KEY_GROUPS = "groups"

    private val baseRef = Firebase.firestore.collection(KEY_PERSON)

    fun getPerson(userID : String) = callbackFlow<Response<Person>> {
        trySend(Response.Loading)
        val listener = baseRef.document(userID).addSnapshotListener { value, error ->
            if (value != null) {
                val person = value.toObject<Person>()!!.also {
                    it.uid = userID;
                }
                trySend(Response.Success(person))
            }
        }
        awaitClose {
            listener.remove()
        }
    }


    suspend fun setPerson(person: Person) : DocumentReference{
        val docRef = baseRef.add(person).await()
        return docRef
    }


    fun getgroupIDs(userID: String) = callbackFlow<Response<List<String>>> {
        trySend(Response.Loading)
        val listener = baseRef.document(userID).addSnapshotListener { value, error ->
            trySend(Response.Success(value!!.get("groupID") as List<String>))
        }
    }

    suspend fun getPersonList(userIDList: List<String> ) : List<Person>{
        var personAwaitList= mutableListOf<Deferred<DocumentSnapshot>>()
        for (userID in userIDList){
            personAwaitList += baseRef.document(userID).get().asDeferred()
        }
        return personAwaitList.awaitAll().map { it.toObject<Person>()!! }
    }
}