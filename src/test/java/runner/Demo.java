package runner;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Demo {

//    @Test
//    public void testGetUsers(){
//        baseURI = "https://reqres.in/api";
//
//        given()
//                .when()
//                .get("/users")
//                .then()
//                .statusCode(200)
//                .body("data[1].first_name", equalTo("Janet"));
//    }

    @Test
    public void testPostUser(){
        baseURI = "https://reqres.in/api";

        Map<String, Object> map = new HashMap<String,Object>();
        map.put("name", "Alejandra");
        map.put("job", "Costumer Success");

        given()
                .log().all()
                .body(map.toString())
                .when()
                .post("/users")
                .then()
                .log().all()
                .statusCode(201);
    }

    @Test
    public void testPostUsers(){
        baseURI = "https://restful-booker.herokuapp.com";

        Map<String, Object> map = new HashMap<String,Object>();
        map.put("username", "admin");
        map.put("password", "password123");

        given()
                .log().all()
                .body(map.toString())
                .when()
                .post("/auth")
                .then()
                .log().all()
                .statusCode(200);
    }
}