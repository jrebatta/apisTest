package step.definitions;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Assert;
import utils.Excel;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class StepsYape extends Excel {

    private Scenario scenario;
    private Response response;
    private final String BASE_URL = "https://restful-booker.herokuapp.com";

    public String token;

    @Before
    public void before(Scenario scenarioVal) {
        this.scenario = scenarioVal;
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
        RestAssured.baseURI = BASE_URL;
        RequestSpecification req = RestAssured.given().headers("Content-Type","application/json").log().all().
                body("{ \"username\" : \""+user+ "\", " +
                        "\"password\" : \""+pass+"\"}");
        response = req.when().post(new URI(url));
    }

    @When("se valida el codigo de respuesta{string}")
    public void seValidaElCodigoDeRespuesta(String responseMessage) {
        int responseCode = response.then().log().all().extract().statusCode();
        Assert.assertEquals(responseMessage, responseCode+"");
    }

    @Then("se obtiene el token")
    public void seObtieneElToken() throws IOException {
        token = response.then().extract().body().jsonPath().getString("token");
        setCellValue("Hoja1",0,0,"123abcd");
        System.out.println(getCellValue("Hoja1",0,0));
        System.out.println(token);
    }

    @Given("Obtener llamada a {string}")
    public void obtenerLlamadaA(String url) throws URISyntaxException {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification req = RestAssured.given().headers("Content-Type","application/json").log().all();
        response = req.when().get(new URI(url));
    }

    @Then("se obtiene respuesta de los IDs")
    public void seObtieneRespuestaDeLosIDs() throws JSONException {
        String ids = response.then().extract().body().jsonPath().getString("bookingid");
//        JSONArray resJson = new JSONArray(response.asString());
//        System.out.println(ids);
//        System.out.println(resJson.length());
    }

    //    @Then("Response  is array total {string}")
//    public void responseIsArrayWith(String arg0) throws JSONException {
//        response.then().statusCode(200);
//        response = response.then().extract().response();
//        scenario.log("Response Received == " + response.asPrettyString());
//        JSONArray resJson = new JSONArray(response.asString());
//        Assert.assertEquals(resJson.length()+"",arg0);


}