package org.tryhard.teluevent.api

import org.tryhard.teluevent.model.event.Event

interface EventClickHandler {
    fun clickedEventItem(event: Event)
}