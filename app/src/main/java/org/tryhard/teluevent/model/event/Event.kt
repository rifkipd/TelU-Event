package org.tryhard.teluevent.model.event

import android.text.Editable
import java.util.*

data class Event(var title: String ?= null, var place: String?= null, var date: String?= null, var desc: String?= null) {

}