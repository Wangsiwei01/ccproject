package com.cloudcomputing.ccproject;

import org.json.JSONObject;

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
    private String signupResponse = "false";
    
    public ServerVerticle(Vertx vertx) {
        this.vertx = vertx;
        eventBus = vertx.eventBus();        
    }
    
    // to test
    @Override
    public void start() throws Exception {
        
//      HttpServer server = vertx.createHttpServer();
//      server.requestHandler(req -> {
//          
//          String requestType = req.getParam("type");
//          System.out.println("resquest received: " + requestType);
//          if ("login".equals(requestType)) {
//              eventBus.send("login", req.getParam("content"), res -> {
//              //eventBus.send("login", "abcabc", res -> {
//                  //if (ar.succeeded()) ?
//                  if ("true".equals(res.result().toString())) {
//                      loginResponse = "true";
//                  }
//                  else {
//                      loginResponse = "false";
//                  }
//              });
//          }
//          req.response()
//          .putHeader("content-type", "text/plain")
//          .end(loginResponse);
//      }).listen(8080);
//        JSONObject jsonobject = new JSONObject();
//        jsonobject.put("username", "xiaoming");
//        jsonobject.put("password", "1234563");
////        jsonobject.put("email", "xiaoming@abc.com");
////        jsonobject.put("phone", "123456678");
//        eventBus.send("login", jsonobject.toString(), res -> {
//            if(res.succeeded()) {  
//                if (res.result().body().toString().equals("true")) {
//                    signupResponse = "true";
//                    System.out.println(res.result().body());
//                    System.out.println("User created!");
//                    System.out.println("succeeded");
//                } else {
//                    System.out.println("not succeeded");
//                }
//            }            
//        });
        
        //use for http

        HttpServer server = vertx.createHttpServer();
        server.requestHandler(req -> {
            if (req != null) {
                req.bodyHandler(body -> {
                    System.out.println("body: " + body.toString());
                    JSONObject jsonBody = new JSONObject(body.toString());
                    String requestType = jsonBody.getString("type");
                    if ("login".equals(requestType)) {
                        eventBus.send("login", body.toString(), res -> {
                            if(res.succeeded()) {  
                                if (res.result().body().toString().equals("true")) {
                                    loginResponse = "Success";
                                    System.out.println(res.result().body());
                                    System.out.println("User login success!");
                                } else {
                                    System.out.println("User login failed!");
                                    loginResponse = "false";
                                }
                            }
                            req.response()
                            .putHeader("content-type", "text/plain")
                            .end(loginResponse);
                        });
                    }
                    if ("signup".equals(requestType)) {
                        eventBus.send("signup", body.toString(), res -> {
                            if(res.succeeded()) {  
                                if (res.result().body().toString().equals("true")) {
                                    signupResponse = "Success";
                                    System.out.println(res.result().body());
                                    System.out.println("User signup success!");
                                } else {
                                    System.out.println("User signup failed!");
                                    signupResponse = "false";
                                }
                            } 
                            req.response()
                            .putHeader("content-type", "text/plain")
                            .end(signupResponse);
                        });                    

                    }
                    
                    
                });
                
            }
        }).listen(8080);
        System.out.println("HTTP server started on port 8080");
    }
}
