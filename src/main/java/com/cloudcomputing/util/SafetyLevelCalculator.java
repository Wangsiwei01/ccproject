package com.cloudcomputing.util;

import org.json.JSONArray;
import org.json.JSONObject;

public class SafetyLevelCalculator {
    public static double calculateWeightedResult(String lat, String lon) {       
        double weightedResult = 0.0;
        int withIn100 = calculateHistoryNumber(lat, lon, "0.1");
        int withIn300 = calculateHistoryNumber(lat, lon, "0.3") - withIn100;
        int withIn500 = calculateHistoryNumber(lat, lon, "0.5") - withIn100 - withIn300;
        weightedResult = (double) withIn100 + 0.5 * (double) withIn300 + 0.2 * (double)withIn500;              
        return weightedResult;
        
    }
    
    public static int calculateHistoryNumber(String lat, String lon, String range) {
        String res = Elasticsearch.ElasticFetchByDistance(lat, lon, range);
        JSONObject jsonObject = new JSONObject(res);
        JSONArray jsonArray = jsonObject.getJSONObject("hits").getJSONArray("hits");
        return jsonArray.length();
    }
}
