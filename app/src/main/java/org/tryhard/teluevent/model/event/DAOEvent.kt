package org.tryhard.teluevent.model.event

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import kotlin.collections.HashMap

class DAOEvent {
    private val databaseReference: DatabaseReference
    fun add(event: Event?): Task<Void> {
        return databaseReference.push().setValue(event)
    }

    fun update(key:String, hashMap: MutableMap<String, Any>):Task<Void>{
        return databaseReference.child(key).updateChildren(hashMap)
    }

    fun remove(key:String):Task<Void>{
        return databaseReference.child(key).removeValue()
    }

    init {
        val db = FirebaseDatabase.getInstance()
        databaseReference = db.getReference(Event::class.java.simpleName)
    }
}