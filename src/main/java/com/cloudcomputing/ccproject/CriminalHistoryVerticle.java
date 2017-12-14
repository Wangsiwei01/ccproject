package com.cloudcomputing.ccproject;

import org.json.JSONArray;
import org.json.JSONObject;

import com.cloudcomputing.util.DataPersistence;
import com.cloudcomputing.util.Elasticsearch;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;

public class CriminalHistoryVerticle extends AbstractVerticle {
    private final EventBus eventBus;
    private final Vertx vertx;
    
    public CriminalHistoryVerticle(Vertx vertx) {
        this.vertx = vertx;
        eventBus = vertx.eventBus();
    }
    @Override
    public void start() throws Exception {
        MessageConsumer<String> consumer = eventBus.consumer("history");
        System.out.println("Listening to history...");
        consumer.handler(message -> {
            String body = message.body();    
            JSONObject jsonobject = new JSONObject(body);
            String latitude = jsonobject.getString("latitude");
            String longitude = jsonobject.getString("longitude");
            System.out.println("CriminalHistoryVerticle: I have received a message: " + body);
            String result = Elasticsearch.ElasticFetchByDistance100(latitude, longitude, "0.1");
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONObject("hits").getJSONArray("hits");
            System.out.println("CriminalHistoryVerticle result: " + jsonArray);
            message.reply(jsonArray.toString());
        });        
    }
}
