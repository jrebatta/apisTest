package step.definitions;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Assert;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;


public class StepsYape {

    private Scenario scenario;
    private Response response;
    private final String BASE_URL = "https://restful-booker.herokuapp.com";

    @Before
    public void before(Scenario scenarioVal) {
        this.scenario = scenarioVal;
    }

    public Map<String,String> defaultHeaders(){
        Map<String, String> dHeaders = new HashMap<String,String>();
        dHeaders.put("Content-Type","application/json");
        return dHeaders;
    }

//    @Given("Get Call to {string}")
//    public void get_call_to_url(String url) throws Exception {
//        RestAssured.baseURI = BASE_URL;
//        RequestSpecification req = RestAssured.given();
//        response = req.when().get(new URI(url));
//    }
//
//    @Then("Response Code {string} is validated")
//    public void response_is_validated(String responseMessage) {
//        int responseCode = response.then().extract().statusCode();
//        Assert.assertEquals(responseMessage, responseCode+"");
//    }
//
//    @Then("Response  is array total {string}")
//    public void responseIsArrayWith(String arg0) throws JSONException {
//        response.then().statusCode(200);
//        response = response.then().extract().response();
//        scenario.log("Response Received == " + response.asPrettyString());
//        JSONArray resJson = new JSONArray(response.asString());
//        Assert.assertEquals(resJson.length()+"",arg0);
//
//
//    }

    @Given("Obtener llamada a {string} con {string} y {string}")
    public void obtenerLlamadaAConY(String url, String user, String pass) throws URISyntaxException {
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("username",user);
        map.put("password",pass);
        RestAssured.baseURI = BASE_URL;
        RequestSpecification req = RestAssured.given().headers(defaultHeaders()).log().all().body(map.toString());
        response = req.when().post(new URI(url));
    }

    @When("se valida el codigo de respuesta{string}")
    public void seValidaElCodigoDeRespuesta(String responseMessage) {
        int responseCode = response.then().extract().statusCode();
        Assert.assertEquals(responseMessage, responseCode+"");
    }

    @Then("se obtiene el token")
    public void seObtieneElToken() {
//        System.out.println(response.then().log().all());
    }

}