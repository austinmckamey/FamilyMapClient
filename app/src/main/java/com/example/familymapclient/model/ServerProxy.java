package com.example.familymapclient.model;

import android.util.Log;

import com.example.shared.models.Event;
import com.example.shared.models.Person;
import com.example.shared.results.*;
import com.example.shared.requests.*;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.TreeMap;

public class ServerProxy {

    public String login(String serverHost, String serverPort, LoginRequest request) {
        String result = null;
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/login");

            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.connect();

            Gson gson = new Gson();

            String reqData = gson.toJson(request);
            OutputStream reqBody = http.getOutputStream();
            writeString(reqData,reqBody);
            reqBody.close();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();

                result = readString(respBody);
            }
            else {
                InputStream respBody = http.getErrorStream();

                result = readString(respBody);
            }

        } catch (IOException e) {
            result = "{\"message\":\"error: Invalid server Host or Port\",\"success\":false}";
            e.printStackTrace();
        }
        return result;
    }

    public String register(String serverHost, String serverPort, RegisterRequest request) {
        String result = null;
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/register");

            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.connect();

            Gson gson = new Gson();

            String reqData = gson.toJson(request);
            OutputStream reqBody = http.getOutputStream();
            writeString(reqData,reqBody);
            reqBody.close();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();

                result = readString(respBody);
            }
            else {
                InputStream respBody = http.getErrorStream();

                result = readString(respBody);
            }

        } catch (IOException e) {
            result = "{\"message\":\"error: Invalid server Host or Port\",\"success\":false}";
            e.printStackTrace();
        }
        return result;
    }

    public void getFamily(String serverHost, String serverPort) {
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/person/");

            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setDoOutput(false);

            DataCache dataCache = DataCache.getInstance();

            http.addRequestProperty("Authorization", dataCache.getAuthToken());
            http.connect();

            Gson gson = new Gson();

            if(http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);

                FamilyResult familyResult = gson.fromJson(respData, FamilyResult.class);
                TreeMap<String,Person> family = new TreeMap<>();
                for(Person p : familyResult.getData()) {
                    family.put(p.getPersonID(), p);
                }
                dataCache.setPeople(family);
            }
            else {
                //Log.v("SERVER ERROR GET FAMILY", "HTTP_NOT_OK");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getEvents(String serverHost, String serverPort) {
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/event/");

            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setDoOutput(false);

            DataCache dataCache = DataCache.getInstance();

            http.addRequestProperty("Authorization", dataCache.getAuthToken());
            http.connect();

            Gson gson = new Gson();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);

                AllEventsResult allEventsResult = gson.fromJson(respData, AllEventsResult.class);
                TreeMap<String, Event> events = new TreeMap<>();
                for(Event e : allEventsResult.getData()) {
                    events.put(e.getEventID(), e);
                }
                dataCache.setEvents(events);
            }
            else {
                //Log.v("SERVER ERROR GET EVENTS", "HTTP_NOT_OK");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    private static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
