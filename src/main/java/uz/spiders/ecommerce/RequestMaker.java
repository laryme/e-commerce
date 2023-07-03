package uz.spiders.ecommerce;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RequestMaker {

    private static final int THREAD_COUNT = 1000;

    public static void main(String[] args) throws IOException {
        URL url = new URL("https://laryme.jprq.live/api/v1/product/1");
        //URL url = new URL("http://localhost:8080/api/v1/product/1");
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);

        while (true){
            for (int i = 0; i < THREAD_COUNT; i++) {
                int finalI = i;
                executorService.submit(() -> {
                    try {
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestProperty("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsb2NoaW5iZWs0MzNAZ21haWwuY29tIiwiaWF0IjoxNjg4Mzc4ODU4LCJleHAiOjE2ODg0NjUyNTh9.OQGZQq3P2_vY9o8JX-ZKjeQNxuNiJl6s2ZjFib_mcWw");
                        connection.connect();
                        int responseCode = connection.getResponseCode();
                        System.out.println("Thread " + finalI + " - Response code: " + responseCode);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }

        //executorService.shutdown();
    }
}