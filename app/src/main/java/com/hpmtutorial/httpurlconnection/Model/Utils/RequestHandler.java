package com.hpmtutorial.httpurlconnection.Model.Utils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

public class RequestHandler {
    public static String sendPost(String r_url, JSONObject postDataParams) throws Exception {
        URL url = new URL(r_url);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setReadTimeout(20000);
        conn.setConnectTimeout(20000);
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);

        String jsonInputString = postDataParams.toString(); //

        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
        writer.write(jsonInputString);
        writer.flush();
        writer.close();
        os.close();

        int responseCode = conn.getResponseCode(); // To Check for 200
        if (responseCode == HttpsURLConnection.HTTP_OK) {

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder("");
            String line = "";
            while ((line = in.readLine()) != null) {
                sb.append(line);
                break;
            }
            in.close();
            return sb.toString();
        }
        return null;
    }

    public static String sendGet(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // connection ok
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } else {
            return "";
        }
    }

    public static String sendDelete(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection httpCon = (HttpURLConnection) obj.openConnection();
        httpCon.setDoOutput(true);
        httpCon.setRequestProperty(
                "Content-Type", "application/json; utf-8");
        httpCon.setRequestMethod("DELETE");
        httpCon.connect();
        return String.valueOf(httpCon.getResponseCode());
    }

    public static String sendPut(String url, JSONObject putData) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection httpCon = (HttpURLConnection) obj.openConnection();
        httpCon.setDoOutput(true);
        httpCon.setRequestProperty(
                "Content-Type", "application/json; utf-8");
        httpCon.setReadTimeout(20000);
        httpCon.setConnectTimeout(20000);
        httpCon.setRequestMethod("PUT");

        String jsonInputString = putData.toString();

        OutputStreamWriter out = new OutputStreamWriter(
                httpCon.getOutputStream());
        out.write(jsonInputString);
        out.close();
        httpCon.getInputStream();
        return String.valueOf(httpCon.getResponseCode());
    }
}
