# Invoice Microservice
Pre-requisities
* Kafka broker service


## Deploy service to OpenShift from source

```
$ oc new-app --name=invoice-msa fabric8/s2i-java:latest~https://github.com/debezium/microservices-lab \
    --context-dir=ticket-msa/invoice \
    -e AB_PROMETHEUS_OFF=true \
    -e KAFKA_SERVICE_HOST=my-cluster-kafka-bootstrap \
    -e KAFKA_SERVICE_PORT=9092 \
    -e JAVA_OPTIONS=-Djava.net.preferIPv4Stack=true

$ oc patch service invoice-msa -p '{ "spec" : { "ports" : [{ "name" : "8080-tcp", "port" : 8080, "protocol" : "TCP", "targetPort" : 8080 }] } } }'

$ oc expose svc invoice-msa
```

## Development workflow
Use `oc port-forward` from local machine to get connections to database and Kafka broker running in OpenShift.

Run `mvn clean thorntail:run` to test local changes - Thorntail runs on port 8280 to avoid clash with VirtualBox port-forwarding.

Run `oc start-build invoice-msa --from-file=target/invoice-thorntail.jar --wait` to test local changes inside the cluster
