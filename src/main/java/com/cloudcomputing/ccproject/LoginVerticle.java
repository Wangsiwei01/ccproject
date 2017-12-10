package com.cloudcomputing.ccproject;

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
            System.out.println("I have received a message: " + body);
            message.reply("true");
        });        
    }
}
