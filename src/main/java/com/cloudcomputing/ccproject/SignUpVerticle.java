package com.cloudcomputing.ccproject;

import org.json.JSONObject;

import com.cloudcomputing.util.DataPersistence;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;

public class SignUpVerticle extends AbstractVerticle {
    private final EventBus eventBus;
    private final Vertx vertx;
    
    public SignUpVerticle(Vertx vertx) {
        this.vertx = vertx;
        eventBus = vertx.eventBus();
    }
    @Override
    public void start() throws Exception {
        MessageConsumer<String> consumer = eventBus.consumer("signup");
        System.out.println("Listening to signup...");
        consumer.handler(message -> {
            String body = message.body();   
            JSONObject jsonobject = new JSONObject(body);
            String username = jsonobject.getString("username");
            String password = jsonobject.getString("password");
            String email = jsonobject.getString("email");
            String phone = jsonobject.getString("phone");
            System.out.println("SignUpVerticle: I have received a message: " + body);
            boolean result = DataPersistence.UserSignUp(username, password, email, phone);
            message.reply(result);
        });
    }
}
