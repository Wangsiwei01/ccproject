package com.cloudcomputing.ccproject;

import org.json.JSONObject;

import com.cloudcomputing.util.Elasticsearch;
import com.cloudcomputing.util.SafetyLevelCalculator;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;

public class SafetyPredictVerticle extends AbstractVerticle {
    private final EventBus eventBus;
    private final Vertx vertx;
    
    public SafetyPredictVerticle(Vertx vertx) {
        this.vertx = vertx;
        eventBus = vertx.eventBus();
    }
    @Override
    public void start() throws Exception {
        MessageConsumer<String> consumer = eventBus.consumer("safety");
        System.out.println("Listening to safety...");
        consumer.handler(message -> {
            String body = message.body();    
            JSONObject jsonobject = new JSONObject(body);
            String latitude = jsonobject.getString("latitude");
            String longitude = jsonobject.getString("longitude");
            System.out.println("SafetyPredictVerticle: I have received a message: " + body);
            double result = SafetyLevelCalculator.calculateWeightedResult(latitude, longitude);
            System.out.println("SafetyPredictVerticle result: " + result);
            message.reply(result);
        });        
    }
}
