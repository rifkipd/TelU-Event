package org.tryhard.teluevent.model.event

import android.text.Editable
import com.google.firebase.database.Exclude
import java.io.Serializable
import java.util.*




data class Event(

    var title: String ?= null,
    var place: String?= null,
    var date: String?= null,
    var desc: String?= null,
    @Exclude
    var key:String?="",):Serializable {


}