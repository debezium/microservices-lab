package io.debezium.examples.eventr.repository;

import javax.ejb.Stateful;

import io.debezium.examples.eventr.model.Event;

@Stateful
public class EventRepository extends Repository<Event> {

    public EventRepository() {
        super(Event.class);
    }
}
