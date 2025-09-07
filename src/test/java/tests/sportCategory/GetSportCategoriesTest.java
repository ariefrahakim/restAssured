package tests.sportCategory;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.io.FileReader;
import java.io.IOException;

import static io.restassured.RestAssured.given;

public class GetSportCategoriesTest {

    private String token;

    @BeforeClass
    public void setup() throws Exception {
        // Set base URI dari config.properties
        RestAssured.baseURI = ConfigReader.getProperty("baseUrl");

        // Baca token dari file src/test/resources/json/token.json
        FileReader reader = new FileReader("src/resources/json/token.json");
        JSONObject tokenJson = new JSONObject(new JSONTokener(reader));
        token = tokenJson.getString("token");
        reader.close();

        System.out.println("Token loaded: " + token);
    }

    @Test
    public void GetSportCategories() throws IOException {

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .when()
                .get("/sport-categories?is_paginate=true&per_page=&page=" )
                .then()
                .extract().response();

        // Print response
        System.out.println("Response: " + response.asString());

        // Validasi status code
        Assert.assertEquals(response.getStatusCode(), 200);

        // Validasi error = false
        Boolean error = response.jsonPath().getBoolean("error");
        Assert.assertFalse(error, "Expected error = false");

    }
}
