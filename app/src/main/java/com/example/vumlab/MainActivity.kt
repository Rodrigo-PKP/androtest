package com.example.vumlab

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.toObject
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_row.view.*
import kotlinx.coroutines.*

//val db = Firebase.firestore

//val storage = Firebase.storage

val database : FirestoreHelper = FirestoreHelper()

val TAG: String = "Debug"

val newUser = User(userID = "DW00", firstName = "Ramón", lastName = "López Velarde", currentBill = "None", email = "first@last.com", currentOrder = "None",profileImageURI = "bit.ly")
val newPartner = Partner(partnerID = "R86M", ownerID = "EP13", name = "Room 86", description = "Un bar diferente.")

val ENTR = Category(categoryID = "ENTR", categoryName = "Entradas", categoryBannerURI = "bit.ly")
val SOUP = Category(categoryID = "SOUP", categoryName = "Sopas", categoryBannerURI = "bit.ly")
val MAIN = Category(categoryID = "MAIN", categoryName = "Platos fuertes", categoryBannerURI = "bit.ly")
val DESS = Category(categoryID = "DESS", categoryName = "Postres", categoryBannerURI = "bit.ly")
val BEVR = Category(categoryID = "BEVR", categoryName = "Bebidas", categoryBannerURI = "bit.ly")



class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)























        val bg_color = Color.parseColor("#32a852")

        val rowOne = hashMapOf("path" to "ER11","type" to "user")
        val rowTwo = hashMapOf("path" to "JN99", "type" to "user")
        val rowThree = hashMapOf("path" to "WT27", "type" to "user")
        val rowFour = hashMapOf("path" to "Users", "type" to "collection")
        val rowFive = hashMapOf("path" to "Partners/XJ84/callsToService", "type" to "customCollection")

        val data = arrayOf(rowOne, rowTwo, rowThree, rowFour, rowFive)


        fun preprocesarContenido(databaseReference: FirebaseFirestore, dataArray: Array<HashMap<*, *>>): Array<HashMap<*, *>> {
            val data = dataArray
            var content = HashMap<String, String>(data.size)

            for (query in data) {
                val path = query["path"]
                val type = query["type"]

                val chunk = when(type) {
                    "collection" -> {

                    }
                    "document" -> {

                    }
                    "custom" -> {

                    }
                    else -> Log.e("vumlab:", "Something went wrong")
                }
            }

            TODO("Preprocesar información aquí para revisar si resuelve comportamiento bloqueante/retraso en UI de la implementación actual")
        }


        List.setBackgroundColor(bg_color)
        List.adapter = CustomAdapter(content = data)


        //val dbRef = db.collection( "Users").document("WT27").collection("testCollection").document(newUser.userID.toString())
        //dbRef.set(newUser)
        //    .addOnSuccessListener { Log.d(TAG, "vumlab: DocumentSnapshot successfully written!") }
        //    .addOnFailureListener { e -> Log.w(TAG, "vumlab: Error writing document", e) }

        /*
        dbRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }






            .addOnSuccessListener { result ->
                for (document in result) {
                    //TextView.text = "${document.id} => ${document.data}"
                    Log.i(TAG, "Consulta de Firestore: ${document.data}. es documento un hashmap?  ${document is HashMap<*,*>}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error al consultar Firestore: $exception")
            }

        */
    }



    private class CustomAdapter(content: Array<HashMap<String, String>>): BaseAdapter() {

        private val adapter_content: Array<HashMap<String, String>> = content
        private var userModel: User? = null


        override fun getCount(): Int {
            return adapter_content.size
        }
        override fun getItemId(position: Int): Long {
            return position.toLong()
        }
        override fun getItem(position: Int): Any {
            return "Probando, probando"
        }
        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View     {
            val layoutInflater = LayoutInflater.from(viewGroup!!.context)
            val rowMain = layoutInflater.inflate(R.layout.main_row, viewGroup, false)

            // Here we populate each row with Firestore data
            val currentContent : HashMap<String, String> = adapter_content[position]
            val titleSlot = rowMain.title //findViewById<TextView>(R.id.title)
            val descriptionSlot = rowMain.description //findViewById<TextView>(R.id.description)

            when (currentContent["type"]) {
                "collection" -> {
                    var counter = 0
                    var data = ""
                    FirestoreHelper().collectionQuery(currentContent["path"]!!).get()
                        .addOnSuccessListener { documents ->
                            for (document in documents) {

                                data += document.data.toString()
                                counter++
                            }

                            titleSlot.text = "${currentContent["path"]!!}: ${counter.toString()} Documents pulled "
                            descriptionSlot.text = data

                        }
                        .addOnFailureListener { exception -> Log.e(TAG, "collection query failed in this manner: ", exception) }



                }
                "document" -> {
                    //title = currentContent["path"]!!
                    //var title: String
                    var data: String? = null
                    FirestoreHelper().documentQuery(currentContent["path"]!!).get()
                        .addOnSuccessListener {document ->
                            userModel = document.toObject<User>() //data.toString()

                            titleSlot.text = userModel?.firstName!!
                            descriptionSlot.text = userModel?.email!!
                        }
                        .addOnFailureListener { exception -> Log.e(TAG, "document query failed in this manner: ", exception) }
                }
                "user" -> {

                    //title = currentContent["path"]!!
                    //var title: String
                    FirestoreHelper().instantiateUser(currentContent["path"]!!)


                    titleSlot.text = userModel?.firstName
                    descriptionSlot.text = userModel?.email
                    /*
                    database.documentQuery(currentContent["path"]!!).get()
                        .addOnSuccessListener {document ->
                            userModel = document.toObject<User>()

                            titleSlot.text = userModel?.firstName!!
                            descriptionSlot.text = userModel?.email!!
                        }
                        .addOnFailureListener { exception -> Log.e(TAG, "document query failed in this manner: ", exception) }

                     */

                }
                "customCollection" -> {
                    var counter = 0
                    var data = ""
                    FirestoreHelper().collectionQuery(currentContent["path"]!!).whereIn("table", listOf("8, 3, 10")).get()
                        .addOnSuccessListener { snapshot ->
                            if (snapshot == null) {
                                Log.e(TAG, "custom collection query failed somehow")
                            } else {
                                for (document in snapshot) {
                                    data += document.data.toString()
                                    counter++
                                }
                                titleSlot.text = "${currentContent["path"]}: ${counter.toString()} Documents pulled"
                                descriptionSlot.text = data
                            }
                        }

                }
                else -> {
                    Log.e(TAG, "When block failed")
                    titleSlot.text = "default title"
                    descriptionSlot.text = "default description"
                }
            }

            return rowMain



            fun populateRowWithCollection(ref: CollectionReference): Int {
                var counter = 0
                ref.get().addOnSuccessListener { documents -> for (document in documents) counter++}
                return counter
            }

            fun populateRowWithDocument(ref: DocumentReference) {

                ref.get().addOnSuccessListener {  }
            }

            fun resolveModelFromPath() {
                TODO()
            }

            /*
            val docRef = documentQuery(db, currentContent["path"]!!)
            val titleSlot = rowMain.findViewById<TextView>(R.id.title)
            val descriptionSlot = rowMain.findViewById<TextView>(R.id.description)

            //var doc: User?
            docRef.get()
                .addOnSuccessListener { documentSnapshot ->
                    Log.i("vumlab:", "SUCCESS!!!!")

                    // Coercionando un DocumentSnapshot a un Modelo
                    userModel = documentSnapshot.toObject<User>()

                    titleSlot.text = userModel?.firstName
                    descriptionSlot.text = userModel?.email
                    return@addOnSuccessListener
                }
                .addOnFailureListener { exception -> Log.e("vumlab:", "FAILED", exception) }
            */
        }
    }

}
