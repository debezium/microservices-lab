package io.debezium.examples.eventr.rest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import io.debezium.examples.eventr.model.Event;
import io.debezium.examples.eventr.model.Order;

@Path("/admin")
@Stateless
public class AdminResource {

	@PersistenceContext(unitName="order-PU-JTA")
	private EntityManager orderEm;

	@PersistenceContext//(unitName="business")
	private EntityManager businessEm;

	public AdminResource() {
	}

	@GET
	@Path("/populate")
	public void populate() throws Exception {
		clearDatabase(businessEm);
		clearOrders(orderEm);


		Event event1 = new Event();
		event1.setName("Spruce Bringsteen Live");
		event1.setPrice(new BigDecimal("59.99"));
		event1.setDate(new Date(2019, 2, 23));
		orderEm.persist(event1);

		Event event2 = new Event();
		event2.setName("Cedric Hapton In Concert");
		event2.setPrice(new BigDecimal("49.99"));
		event2.setDate(new Date(2019, 3, 21));
		orderEm.persist(event2);

		Event event3 = new Event();
		event3.setName("Conny Fresh Prison Rock");
		event3.setPrice(new BigDecimal("69.99"));
		event3.setDate(new Date(2019, 5, 9));
		orderEm.persist(event3);

		Order order1 = new Order();
		order1.setDate(new Date());
		order1.setEvent(event1);
		order1.setPayment(new BigDecimal("59.99"));
		order1.setCustomer("Bob Smith");
		orderEm.persist(order1);

		Order order2 = new Order();
		order2.setDate(new Date());
		order2.setEvent(event2);
		order2.setPayment(new BigDecimal("49.99"));
		order2.setCustomer("Berta Muller ");
		orderEm.persist(order2);
	}

	private void clearOrders(EntityManager em) {
		List<?> all = em.createQuery( "from Order" ).getResultList();
		for ( Order object : (List<Order>) all ) {
			em.remove( object );
		}

		all = em.createQuery( "from Event" ).getResultList();
		for ( Event object : (List<Event>)all ) {
			em.remove( object );
		}
	}

	private void clearDatabase(EntityManager em) {
		List<Object> all = em.createQuery( "from java.lang.Object" ).getResultList();
		for ( Object object : all ) {
			em.remove( object );
		}
	}
}
