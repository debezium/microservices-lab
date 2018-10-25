package io.debezium.examples.eventr.repository;

import java.util.List;

import javax.ejb.Stateful;

import io.debezium.examples.eventr.model.Order;

@Stateful
public class OrderRepository extends Repository<Order> {

    public OrderRepository() {
        super(Order.class);
    }

    public List<Order> getByEventId(int eventId) {
        return entityManager.createQuery("FROM Order o WHERE o.event.id=?1", Order.class).setParameter(1, eventId).getResultList();
    }

    public List<Order> getByCustomerOrEventName(String term) {
        return entityManager
                .createQuery( "FROM Order o WHERE o.customer LIKE :term OR o.event.name LIKE :term", Order.class )
                .setParameter( "term", "%" + term + "%" )
                .getResultList();
    }
}
