package com.camelrest.apitester;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.http.common.HttpOperationFailedException;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;

@Component
public class RouteBuilder extends org.apache.camel.builder.RouteBuilder {
    @Override
    public void configure() throws Exception {
        onException(HttpOperationFailedException.class)
                .handled(true)
                .process(exchange -> {
                    Exception exception = (Exception) exchange.getProperty(Exchange.EXCEPTION_CAUGHT);
                    System.out.println(exception.getMessage());
                });


        restConfiguration().component("servlet").host("localhost").port(8081).bindingMode(RestBindingMode.auto);

        rest("api").description("Camel Movie Rest Service")
                .consumes("application/json")
                .produces("application/json")
                .get("/movies/2").to("direct:GetAMovie")
                .get("/movies").to("direct:GetAllMovies")
                .post("/movies").to("direct:InsertAMovie")
                .put("/movies/2").to("direct:UpdateAMovie")
                .delete("movies/1").to("direct:DeleteAMovie")
                .get("/movies/available").to("direct:GetAvailableMovies");


        from("direct:GetAMovie")
                .routeId("GetAMovie")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .to("http://localhost:8080/api/movies/2?bridgeEndpoint=true")
                .log("${body}");

        from("direct:GetAllMovies")
                .routeId("GetAllMovies")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .to("http://localhost:8080/api/movies?bridgeEndpoint=true")
                .log("${body}");

        from("direct:GetAvailableMovies")
                .routeId("GetAvailableMovies")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .to("http://localhost:8080/api/movies/available?bridgeEndpoint=true")
                .log("${body}");


        from("direct:InsertAMovie")
                .routeId("InsertAMovie")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader(Exchange.CONTENT_TYPE,constant("application/json"))
                .process(exchange -> exchange.getIn().setBody(new Movie("TestInsertCamel","TestingInsertCamel",true)))
                .marshal().json(JsonLibrary.Jackson)
                .to("http://localhost:8080/api/movies?bridgeEndpoint=true")
                .log("${body}");


        from("direct:UpdateAMovie")
                .routeId("UpdateAMovie")
                .setHeader(Exchange.HTTP_METHOD,constant("PUT"))
                .setHeader(Exchange.CONTENT_TYPE,constant("application/json"))
                .process(exchange -> exchange.getIn().setBody(new Movie("TestUpdateCamel","TestingUpdateCamel",true)))
                .marshal().json(JsonLibrary.Jackson)
                .to("http://localhost:8080/api/movies/2?bridgeEndpoint=true")
                .log("${body}");


        from("direct:DeleteAMovie")
                .routeId("DeleteAMovie")
                .setHeader(Exchange.HTTP_METHOD, constant("DELETE"))
                .to("http://localhost:8080/api/movies/1?bridgeEndpoint=true");

    }
}
