package edu.ib.appteczkaandroid;

import android.content.Context;


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

import java.util.ArrayList;


public class RxNormService {

    private Context context;

    public RxNormService(Context context) {
        this.context = context;

    }



    public void getDrugsId(String drugName, Context context, IOnStringResponse onResponse, int reqId){
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
                        try {
                            JsonObject jakos = results.getAsJsonObject("idGroup");

                            JsonArray jj = jakos.getAsJsonArray("rxnormId");
                            System.out.println(jj);
                            JsonElement a = jj.get(0);
                            onResponse.onResponse(a.getAsString(), reqId, true);

                        }catch(Exception e) {
                            System.out.println("No such drug");
                            onResponse.onResponse(e.getMessage(), reqId, false);


                    }

                }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error");
            }

        });

        queue.add(stringRequest);


    }


    public void getInteractionsWithTwoDrugs(String firstDrug, String secondDrug, IOnInteractionsGet interactionsGet){

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
                        interactionsGet.onInteractions(description.toString(),firstDrug,secondDrug,true);

                    } catch (Exception e) {
                        System.out.println("No description");
                        interactionsGet.onInteractions(e.getMessage(),firstDrug,secondDrug,false);

                    }
                }, error -> {
            System.out.println("error");

        });
        queue.add(stringRequest);
    }

    public void getInteractionsWithAll(ArrayList<String> drugIds, IOGetAllInteractions ioGetAllInteractions){
        RequestQueue queue = Volley.newRequestQueue(context);
        String urlPart = "https://rxnav.nlm.nih.gov/REST/interaction/list.json?rxcuis=";
        StringBuilder sb = new StringBuilder(urlPart);
        for (int i = 0; i < drugIds.size()-1; i++) {
            sb.append(drugIds.get(i));
            sb.append("+");

        }
        sb.append(drugIds.get(drugIds.size()-1));
        String url = sb.toString();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                res -> {
                    JsonObject results = gson.fromJson(res.toString(), JsonObject.class);

                    try {

                        JsonArray jakos =   results.getAsJsonArray("fullInteractionTypeGroup");
                        System.out.println(jakos.toString());
                        JsonObject j = jakos.get(0).getAsJsonObject();
                        System.out.println(j.toString());
                        JsonArray jj = j.getAsJsonArray("fullInteractionType");
                        System.out.println(jj);
                        ArrayList<String> descriptionList = new ArrayList<>();
                        for (int i = 0; i < jj.size(); i++) {
                            JsonObject obj = jj.get(i).getAsJsonObject();
                            System.out.println(i);
                            JsonArray interactionPair = obj.getAsJsonArray("interactionPair");
                            JsonObject interactionPairObject = interactionPair.get(0).getAsJsonObject();
                            JsonElement description = interactionPairObject.get("description");
                            descriptionList.add(description.toString());
                            System.out.println(description.toString());

                        }


                        ioGetAllInteractions.onAllInteractions(descriptionList,drugIds,true);

                    } catch (Exception e) {
                        System.out.println("No description");
                        ArrayList<String> errorList = new ArrayList<>();
                        errorList.add("error");
                        ioGetAllInteractions.onAllInteractions(errorList,drugIds,false);

                    }
                }, error -> {
            System.out.println("error");

        });
        queue.add(stringRequest);
    }

}
