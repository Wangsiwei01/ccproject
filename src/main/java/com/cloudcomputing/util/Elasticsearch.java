package com.cloudcomputing.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Map;

import org.elasticsearch.client.HttpAsyncResponseConsumerFactory;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.apache.http.entity.ContentType;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.nio.entity.NStringEntity;

public class Elasticsearch {
    private static final String host = "search-tweetmap-7fveonbkb55njxr2dcra36d7w4.us-east-1.es.amazonaws.com"; // e.g. my-test-domain.us-east-1.es.amazonaws.comd
    
    public static String ElasticIndex(String json) throws IOException {
        String index = "crimedata";
        String type = "current";

        RestClient client = RestClient.builder(new HttpHost(host, 443, "https")).build();

        HttpEntity entity = new NStringEntity(json, ContentType.APPLICATION_JSON);

        Response response = client.performRequest("POST", "/" + index + "/" + type + "/",
            Collections.<String, String>emptyMap(), entity);
        
        client.close();

        return response.toString();
    }
    public static String ElasticFetchByDistance(String lat, String lon, String range) {
        // TODO add asyc
        //Map<String, String> params = Collections.singletonMap("distance", "1km");
        Response response;
        StringBuilder sb = new StringBuilder();
        
        try {
            //response = client.performRequest("GET", "/tweet/_search/{");
            String str = "{\n" + 
                    "  \"from\": 0,\n"+
                    "  \"size\": 5000,\n"+
                    "  \"query\": {\n" + 
                    "    \"bool\": {\n" + 
                    "      \"must\": {\n" +                               
                    "       \"match_all\" : {}\n" + 
                    "},\n" +
                    "           \"filter\": {\n"+
                    "        \"geo_distance\": {\n" + 
                    "          \"distance\": \""+ range +"km\", \n" + 
                    "          \"location\": { \n" + 
                    "            \"lat\":  "+ lon +",\n" + 
                    "            \"lon\":  "+ lat +"\n" + 
                    "          }\n" + 
                    "        }\n" + 
                    "      }\n" + 
                    "    }\n" + 
                    "  }\n" + 
                    "}";
            RestClient client = RestClient.builder(new HttpHost(host, 443, "https")).build();
            HttpEntity entity = new NStringEntity(str, ContentType.APPLICATION_JSON);
            response = client.performRequest("GET", "/crimedata/current/_search", Collections.<String, String>emptyMap(), entity);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String temp;
            while((temp = reader.readLine()) != null) {
                sb.append(temp);
                System.out.println(temp);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        return sb.toString();
        
    }
    
    
    public static String ElasticFetchByDistance100(String lat, String lon, String range) {
        // TODO add asyc
        //Map<String, String> params = Collections.singletonMap("distance", "1km");
        Response response;
        StringBuilder sb = new StringBuilder();
        
        try {
            //response = client.performRequest("GET", "/tweet/_search/{");
            String str = "{\n" + 
                    "  \"from\": 0,\n"+
                    "  \"size\": 100,\n"+
                    "  \"query\": {\n" + 
                    "    \"bool\": {\n" + 
                    "      \"must\": {\n" +                               
                    "       \"match_all\" : {}\n" + 
                    "},\n" +
                    "           \"filter\": {\n"+
                    "        \"geo_distance\": {\n" + 
                    "          \"distance\": \""+ range +"km\", \n" + 
                    "          \"location\": { \n" + 
                    "            \"lat\":  "+ lon +",\n" + 
                    "            \"lon\":  "+ lat +"\n" + 
                    "          }\n" + 
                    "        }\n" + 
                    "      }\n" + 
                    "    }\n" + 
                    "  }\n" + 
                    "}";
            RestClient client = RestClient.builder(new HttpHost(host, 443, "https")).build();
            HttpEntity entity = new NStringEntity(str, ContentType.APPLICATION_JSON);
            response = client.performRequest("GET", "/crimedata/current/_search", Collections.<String, String>emptyMap(), entity);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String temp;
            while((temp = reader.readLine()) != null) {
                sb.append(temp);
                System.out.println(temp);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        return sb.toString();
        
    }
    public static String ElasticFetchByKeywords(String keywords) {
        // TODO add asyc
        //Map<String, String> params = Collections.singletonMap("distance", "1km");
        Response response;
        StringBuilder sb = new StringBuilder();
        RestClient client = RestClient.builder(new HttpHost(host, 443, "https")).build();
        try {            
            response = client.performRequest("GET", "/tweets_type/tweet_type/_search?q=text:"+keywords);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String temp;
            while((temp = reader.readLine()) != null) {
                sb.append(temp);
                System.out.println(temp);
            }            
            client.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sb.toString();      
    }
    public static String ElasticFetchAll() {
        // TODO add asyc
        //Map<String, String> params = Collections.singletonMap("distance", "1km");
        Response response;
        StringBuilder sb = new StringBuilder();
        
        try {
            //response = client.performRequest("GET", "/tweet/_search/{");
            String str = "{\n" + 
                    "  \"from\": 0,\n"+
                    "  \"size\": 100,\n"+
                    "  \"query\": {\n" +                             
                    "   \"match_all\" : {}\n" + 
                    "  }\n" + 
                    "}";
            RestClient client = RestClient.builder(new HttpHost(host, 443, "https")).build();
            HttpEntity entity = new NStringEntity(str, ContentType.APPLICATION_JSON);
            response = client.performRequest("GET", "/crimedata/current/_search", Collections.<String, String>emptyMap(), entity);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String temp;
            while((temp = reader.readLine()) != null) {
                sb.append(temp);
                System.out.println(temp);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        return sb.toString();     
    }    
    //parse the response to string
    public static String readResponse(Response response) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String temp;
            while((temp = reader.readLine()) != null) {
                sb.append(temp);
            }
            reader.close();           
        } catch (UnsupportedOperationException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sb.toString();
    }
}