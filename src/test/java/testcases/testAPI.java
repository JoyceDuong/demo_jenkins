package testcases;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

public class testAPI {

    public static void main(String[] args) {
        RestAssured.baseURI = "https://sleek-mailer-ts-staging.sleek.sg/api/";
        RestAssured.basePath = "inbox/";



        Response response = given().header("Authorization", "ad09f7f9-70c9-4a95-b1d1-3ef4f25a93f1").header("Content-Type", "application/json").get("burl@gmail.com");
        ArrayList<Map<String,String>> mai = (ArrayList<Map<String, String>>) response.as(ArrayList.class);
        String id = mai.get(0).get("id");

        System.out.println(id);


}}



