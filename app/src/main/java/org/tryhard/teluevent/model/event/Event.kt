package org.tryhard.teluevent.model.event

import android.os.Parcelable
import android.text.Editable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.util.*



@Parcelize
data class Event(
    val title: String? = "",
    val place: String? = "",
    val date: String? = "",
    val desc: String? = "",
    @Exclude
    var key: String? = "",
):Parcelable {




}