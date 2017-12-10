package com.cloudcomputing.ccproject;

import io.vertx.core.Vertx;

public class Main {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        ServerVerticle server = new ServerVerticle(vertx);
        LoginVerticle login = new LoginVerticle(vertx);
        try {
            login.start();
            Thread.currentThread().sleep(1000);;
            server.start();
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
