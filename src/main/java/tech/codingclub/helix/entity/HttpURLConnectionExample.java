package tech.codingclub.helix.entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpURLConnectionExample {

    //HTTP GET request
    public static String sendGet(String url) throws IOException {
        StringBuilder result = new StringBuilder();

        URL url1 = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();

    }

    public static void main(String[] args) throws IOException {

        System.out.println(sendGet("https://codingclub.tech/test-get-request?name=Prasanth"));
//        System.out.println(sendGet("https://example.com")); //This will return a html code of that page

    }


}
