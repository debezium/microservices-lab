package io.debezium.examples.eventr.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import io.debezium.examples.eventr.model.Event;
import io.debezium.examples.eventr.repository.EventRepository;

@RequestScoped
@Path("/events")
@Produces("application/json")
@Consumes("application/json")
public class EventResource {

    @Inject
    private EventRepository repository;

    @POST
    public Response create(final Event event) {
        repository.create(event);
        return Response
                .created(UriBuilder.fromResource(EventResource.class).path(String.valueOf(event.getId())).build())
                .build();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    public Response findById(@PathParam("id") final int id) {
        final Event event = repository.getById(id);
        if (event == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(event).build();
    }

    @GET
    public List<Event> listAll() {
        return repository.getAll();
    }

    @PUT
    @Path("/{id:[0-9][0-9]*}")
    public Response update(@PathParam("id") int id, final Event event) {
        repository.save(event);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id:[0-9][0-9]*}")
    public Response deleteById(@PathParam("id") final int id) {
        repository.delete(id);
        return Response.noContent().build();
    }

}
