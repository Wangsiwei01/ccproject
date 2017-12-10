package com.cloudcomputing.ccproject;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpServer;

public class ServerVerticle extends AbstractVerticle {
    private final EventBus eventBus;
    private final Vertx vertx;
    private String loginResponse = "false";
    
    public ServerVerticle(Vertx vertx) {
        this.vertx = vertx;
        eventBus = vertx.eventBus();        
    }
    
    // to test
    @Override
    public void start() throws Exception {
        EventBus eb = vertx.eventBus();
        eb.send("login", "Username Password", res -> {
            if (res.succeeded()) {
                if (res.result().body().equals("true")) {
                    loginResponse = "true";
                    System.out.println("User authenticated!");
                }
                System.out.println("succeeded");
            } else {
                System.out.println("not succeeded");
            }
            
        });
        
        //use for http
//        //container.deployVerticle("com.geekcap.vertxexamples.AuditVerticle");
//        HttpServer server = vertx.createHttpServer();
//        server.requestHandler(req -> {
//            String requestType = req.getParam("type");
//            
//            if (requestType.equals("login")) {
//                EventBus eb = vertx.eventBus();
//                eb.send("login", req.getParam("content"), res -> {
//                    //if (ar.succeeded()) ?
//                    if (res.result().toString().equals("true")) {
//                        loginResponse = "true";
//                    }
//                    else {
//                        loginResponse = "false";
//                    }
//                });
//            }
//            req.response()
//            .putHeader("content-type", "text/plain")
//            .end(loginResponse);
//        }).listen(8080);
//        System.out.println("HTTP server started on port 8080");
    }
}
