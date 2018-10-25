package io.debezium.examples.ticketmsa.order;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.aerogear.kafka.SimpleKafkaProducer;
import org.aerogear.kafka.cdi.annotation.KafkaConfig;
import org.aerogear.kafka.cdi.annotation.Producer;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.debezium.examples.ticketmsa.order.model.Order;

@Path("/orders")
@ApplicationScoped
@KafkaConfig(bootstrapServers = "#{KAFKA_SERVICE_HOST}:#{KAFKA_SERVICE_PORT}")
public class OrderService {

    @Inject
    @ConfigProperty(name="order.topic.name", defaultValue="orders")
    private String topicName;

    @PersistenceContext
    private EntityManager entityManager;

    @Producer
    private SimpleKafkaProducer<Integer, JsonObject> kafka;

    @POST
    @Transactional
    @Produces("application/json")
    @Consumes("application/json")
    public Order addOrder(Order order) {
        order = entityManager.merge(order);
        kafka.send(topicName, order.getId(), order.toJson());
        return order;
    }
}
