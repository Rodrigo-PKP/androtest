package com.example.vumlab

import android.util.Log
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await


//val test = FirestoreHelper.instantiateUser("WT27")
private var mAuth = FirebaseAuth.getInstance()
private val db = Firebase.firestore
private var GS = GlobalScope

class FirestoreHelper {
    companion object
}

suspend fun FirestoreHelper.loginWithEmail(email: String, password: String): AuthResult? {
    //FirestoreHelper().loginWithEmail("rodrigoffvh@gmail.com", "Templodespejo5")
    return try {
        val data = mAuth
            .signInWithEmailAndPassword(email, password)
            .await()
        data
    } catch (e: Exception) {
        return null
    }
}

fun FirestoreHelper.collectionQuery(path: String): CollectionReference {

    val res: CollectionReference?
    res = db.collection(path)
    return res
}

fun FirestoreHelper.documentQuery(path: String): DocumentReference {

    val res: DocumentReference?
    res = db.document(path)
    return res
}

fun FirestoreHelper.instantiateUser(userID: String): Any? {
    //userID must not contain an ending slash
    var data: DocumentSnapshot? = null
    var res: User? = null

    GS.launch(Dispatchers.IO) {
        data = withContext(Dispatchers.IO) {
            return@withContext try {
                FirestoreHelper().documentQuery("Users/${userID}").get().await()  //.toObject<User>()
            } catch (e: Exception) {
                null
            }
        }
    }
    //res = if (!data) User() else data!!.toObject<User>()
    //res = data!!.toObject<User>()
    //return res

}


/*
coroutineScope {
 FirestoreHelper().documentQuery("Users/${userID}").get().await().toObject<User>()


.addOnSuccessListener { documentSnapshot ->
    res = documentSnapshot.toObject<User>()
    return@addOnSuccessListener
}
.addOnFailureListener { exception ->
    Log.e("Debug", "This happened...", exception)
    return@addOnFailureListener
}

*/














fun FirestoreHelper.addDocumentsToCollection() {}


/*
class FirestoreHelper() {

    companion object Foo {
        private val db = FirebaseFirestore.getInstance()

        fun collectionQuery(path: String) : CollectionReference {

            val res: CollectionReference?
            res = db.collection(path)
            return res
        }

        fun documentQuery(path: String) : DocumentReference {

            val res : DocumentReference?
            res = db.document(path)
            return res
        }

        fun instantiateUser(userID: String): User {
            //userID must not contain an ending slash

            var res: User? = null

            this.documentQuery("Users/${userID}").get()
                .addOnSuccessListener { documentSnapshot ->
                    res = documentSnapshot.toObject<User>()
                }
                .addOnFailureListener { exception ->
                    Log.e(TAG, "This happened...", exception)
                }

            return res!!

        }




    }
}
 */