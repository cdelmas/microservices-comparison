package io.github.cdelmas.spike.sparkjava;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.github.cdelmas.spike.common.domain.Car;
import spark.Request;
import spark.Response;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class HelloResource {

    public String hello (Request request, Response response) {
        String token = readToken(request);
        HttpResponse<Car[]> carHttpResponse;
        try {
            carHttpResponse = Unirest.get("https://localhost:8099/cars")
                    .header("Accept", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .asObject(Car[].class);
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
        Car[] cars = carHttpResponse.getBody();
        List<String> carNames = Arrays.stream(cars)
                .map(Car::getName)
                .collect(toList());
        return "We have these cars available: " + carNames;
    }

    private String readToken(Request request) {
        String authorization = request.headers("Authorization");
        return authorization.substring(authorization.indexOf(' ') + 1);
    }
}
