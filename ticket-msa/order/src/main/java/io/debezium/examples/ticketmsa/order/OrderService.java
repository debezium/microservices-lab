package io.debezium.examples.ticketmsa.order;

import java.math.BigDecimal;

import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.aerogear.kafka.SimpleKafkaProducer;
import org.aerogear.kafka.cdi.annotation.KafkaConfig;
import org.aerogear.kafka.cdi.annotation.Producer;

import io.debezium.examples.ticketmsa.order.model.Order;

@Path("/")
@ApplicationScoped
@KafkaConfig(bootstrapServers = "#{KAFKA_SERVICE_HOST}:#{KAFKA_SERVICE_PORT}")
public class OrderService {

    private static final String TOPIC_ORDER = "orders";

    @PersistenceContext
    private EntityManager entityManager;

    @Producer
    private SimpleKafkaProducer<Integer, JsonObject> kafka;

    @GET
    @Transactional
    @Produces("application/json")
    public Order addOrder() {
        Order order = new Order("John", "Doe", "john.doe@example.com", new BigDecimal(1000));
        order = entityManager.merge(order);
        kafka.send(TOPIC_ORDER, order.getId(), order.toJson());
        return order;
    }
}
