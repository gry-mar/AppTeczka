package edu.ib.appteczkaandroid;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.logging.Logger;

public class RxNormService {

    //protected static String id ="";
    private Context context;
    private TextView textView;
    public static String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RxNormService(Context context, TextView textView) {
        this.context = context;
        this.textView = textView;
    }



    public void getDrugsId(String drugName, Context context){
        RequestQueue queue = Volley.newRequestQueue(context);
        String urlPart = "https://rxnav.nlm.nih.gov/REST/rxcui.json?name=";
        StringBuilder sb = new StringBuilder(urlPart);
        sb.append(drugName);
        String url = sb.toString();
        System.out.println(url);
        String rxNormID = null;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JsonObject results = gson.fromJson(response.toString(), JsonObject.class);
                        //Bundle bundle = new Bundle();
                        try {
                            JsonObject jakos = results.getAsJsonObject("idGroup");

                            JsonArray jj = jakos.getAsJsonArray("rxnormId");
                            System.out.println(jj);
                            JsonElement a = jj.get(0);
                             id = a.getAsString();
                             //Intent i = new Intent(context,CompareTwoActivity.class);
                             //bundle.putString("DRUG_ID", id);



                        }catch(Exception e) {
                            System.out.println("No such drug");
                            textView.setText("Nie znaleziono takiego leku");


                    }

                }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error");
            }

        });

        queue.add(stringRequest);
        //System.out.println(id);


    }

    public String returnData(String data){
        return data;
    }

    public void getInteractionsWithTwoDrugs(String firstDrug, String secondDrug){

        System.out.println(firstDrug);
        System.out.println(secondDrug);
        RequestQueue queue = Volley.newRequestQueue(context);
        String urlPart = "https://rxnav.nlm.nih.gov/REST/interaction/list.json?rxcuis=";
        StringBuilder sb = new StringBuilder(urlPart);
        sb.append(firstDrug);
        sb.append("+");
        sb.append(secondDrug);
        String url = sb.toString();
        System.out.println(url);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                res -> {
                    JsonObject results = gson.fromJson(res.toString(), JsonObject.class);

                    try {
                        //LinkedTreeMap<String, Object>k =((ArrayList<LinkedTreeMap<String, Object>>) results.get("fullInteractionTypeGroup")).get(0);
                        JsonArray jakos =   results.getAsJsonArray("fullInteractionTypeGroup");
                        System.out.println(jakos.toString());
                        JsonObject j = jakos.get(0).getAsJsonObject();
                        System.out.println(j.toString());
                        JsonArray jj = j.getAsJsonArray("fullInteractionType");
                        System.out.println(jj);
                        JsonObject jjj = jj.get(0).getAsJsonObject();
                        System.out.println(jjj);
                        JsonArray interactionPair = jjj.getAsJsonArray("interactionPair");
                        JsonObject interactionPairObject = interactionPair.get(0).getAsJsonObject();
                        JsonElement description = interactionPairObject.get("description");
                        textView.setText(description.toString());

                        // System.out.println(jj.toString());
//                                  JsonArray i = j.get(2).getAsJsonArray();
//                                System.out.println(i.toString());
//                                  JsonArray h = i.get(2).getAsJsonArray();
//                                System.out.println(h.toString());
//                                  JsonObject g = h.get(3).getAsJsonObject();
//                                  String desc = g.toString();
//                                System.out.println(desc);
//                                HashMap<String,Object> lvl1 =  results.get("fullInteractionTypeGroup").get(0);
//                                 lvl2 = (LinkedTreeMap<String, Object>) lvl1.get("fullInteractionType");
//                                Type type = new TypeToken<Map<String, Object>>(){}.getType();
//                                Map<String, Object> myMap = gson.fromJson(String.valueOf(lvl1), type);
                        //HashMap<String, Object> lvl2 = (HashMap<String, Object>) lvl1.get("fullInteractionType");
//                                for(String key: lvl1.keySet()){
//                                    System.out.println(key);
//                                }

                        //ArrayList<LinkedTreeMap<String, Object>> dd = (ArrayList<LinkedTreeMap<String, Object>>) k.get("fullInteractionType");
//                                LinkedTreeMap<String, Object> o = (LinkedTreeMap<String, Object>) dd.get(0);
//                                ArrayList<LinkedTreeMap<String, Object>> o2 = (ArrayList<LinkedTreeMap<String, Object>>) o.get("interactionPair");
//                                LinkedTreeMap<String, Object> o3 = (LinkedTreeMap<String, Object>) o2.get(0);
//                                Object descr = o3.get("description");
//                                System.out.println(descr);
//                                tvResult.setText(descr.toString());
//                                for(LinkedTreeMap<String, Object> item : o){
//                                    String de = (String) item.get("description");
//                                    System.out.println(item.keySet());
//                                }
                        //System.out.println(dd.getClass());
                        //System.out.println(lvl1.get("fullInteractionType"));
                        //System.out.println(lvl1.getClass());





                    } catch (Exception e) {
                        System.out.println("No description");
                        textView.setText("Nie znaleziono interakcji, sprawdź poprawnie nazwy leków lub skorzystaj z samouczka");

                    }
                }, error -> {
            System.out.println("error");

        });
        queue.add(stringRequest);
//        return stringRequest.toString();
    }

}
