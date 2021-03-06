package com.cloudcomputing.ccproject;

import org.json.JSONObject;

import com.cloudcomputing.util.DataPersistence;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;

public class LoginVerticle extends AbstractVerticle {  
    private final EventBus eventBus;
    private final Vertx vertx;
    
    public LoginVerticle(Vertx vertx) {
        this.vertx = vertx;
        eventBus = vertx.eventBus();
    }
    @Override
    public void start() throws Exception {
        MessageConsumer<String> consumer = eventBus.consumer("login");
        System.out.println("Listening to login...");
        consumer.handler(message -> {
            String body = message.body();    
            JSONObject jsonobject = new JSONObject(body);
            String username = jsonobject.getString("username");
            String password = jsonobject.getString("password");
            System.out.println("LoginVerticle: I have received a message: " + body);
            boolean result = DataPersistence.LoginCheck(username, password);
            System.out.println("LoginVerticle result: " + result);
            message.reply(result);
        });        
    }
}
