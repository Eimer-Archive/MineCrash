package com.imjustdoom.minecrash.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.imjustdoom.minecrash.dto.out.ErrorDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class NetworkUtil {

    private static final Gson GSON = new Gson();
    private static final String USER_AGENT = "MineCrash 1.0";

    private static final String BASE_URL = "http://localhost:8080/error/";

    // Get
    private static final URL SUBMITTED_COUNT;

    // Post
    private static final URL CHECK;

    public static int getSubmittedCount() throws IOException {
        JsonObject object = sendGet(SUBMITTED_COUNT);
        return object.get("count").getAsInt();
    }

    public static String sendErrorForCheck(String error) throws IOException {
        try {
            return sendPost(CHECK, GSON.toJson(ErrorDto.create(error), ErrorDto.class));
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    private static JsonObject sendGet(URL url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            JsonObject object = GSON.fromJson(reader, JsonElement.class).getAsJsonObject();
            reader.close();
            return object;
        } else {
            throw new IOException("Failed to connect to the endpoint");
        }
    }

    private static String sendPost(URL url, String body) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");

        // For POST only - START
        con.setDoOutput(true);
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = body.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        // For POST only - END

        int responseCode = con.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            return response.toString();
        } else {
            System.out.println("POST request did not work.");
            throw new IOException("Error sending POST");
        }
    }

    static {
        try {
            SUBMITTED_COUNT = URI.create(BASE_URL + "submittedCount").toURL();
            CHECK = URI.create(BASE_URL + "check").toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
