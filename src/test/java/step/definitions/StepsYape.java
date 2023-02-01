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
import org.junit.Test;
import utils.Excel;
import static org.assertj.core.api.Assertions.assertThat;
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
        setCellValue("Hoja1",1,0,token);
        System.out.println(getCellValue("Hoja1",1,0));
        Assert.assertNotNull(token);
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
        System.out.println(ids);
        Assert.assertNotNull(ids);
//        JSONArray resJson = new JSONArray(response.asString());
//        System.out.println(ids);
//        System.out.println(resJson.length());
    }

    @Given("crear bookin {string} con datos {string} {string} {string} {string} {string} {string} {string}")
    public void crearBookinConDatos(String url, String firstname, String lastname, String totalprice, String depositpaid, String checkin, String checkout, String additionalneeds) throws URISyntaxException {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification req = RestAssured.given().headers("Content-Type","application/json").log().all().
                body("{ \"firstname\" : \""+firstname+ "\", " +
                        "\"lastname\" :  \""+lastname+"\","+
                        "\"totalprice\" : \""+totalprice+"\","+
                        "\"depositpaid\" : \""+depositpaid+"\"," +
                        "\"bookingdates\" : {\"checkin\" : \""+checkin+"\","+
                                            "\"checkout\": \""+checkout+"\"},"+
                        "\"additionalneeds\":\""+additionalneeds+"\""+ "}");
        response = req.when().post(new URI(url));
    }

    @Then("se obtiene el id del booking creado y se guarda el nombre y apellido del booking creado")
    public void seObtieneElIdDelBookingCreadoYSeGuardaElNombreYApellido() throws IOException {
        String nombre = response.then().extract().body().jsonPath().getString("bookingid");
        String bookid = response.then().extract().body().jsonPath().getJsonObject("firstname");
        String apellido = response.then().extract().body().jsonPath().getJsonObject("lastname");
        setCellValue("Hoja1",1,2,nombre);
        setCellValue("Hoja1",1,3,apellido);
        setCellValue("Hoja1",1,1,bookid);
        Assert.assertNotNull(nombre);
        Assert.assertNotNull(apellido);
        Assert.assertNotNull(bookid);
    }

    @Given("Obtener llamada a {string} con id")
    public void obtenerLlamadaAConId(String url) throws URISyntaxException, IOException {
        RestAssured.baseURI = BASE_URL;
        String id = getCellValue("Hoja1",1,1);
        url = url+"/"+id;
        System.out.println(url);
        RequestSpecification req = RestAssured.given().headers("Content-Type","application/json").log().all();
        response = req.when().get(new URI(url));

    }

//    @Then("se obtiene los datos del id")
//    public void seObtieneLosDatosDelId() {
//        String data = response.then().extract().body().jsonPath().toString();
//        String data2 = response.then().extract().body().jsonPath().getString("firstname");
//        System.out.println(data);
//        System.out.println(data2);
//
//    }

//    @Given("Obtener id booking {string} por {string} y {string}")
//    public void obtenerIdBookingPorY(String url, String firstname, String lastname) throws IOException, URISyntaxException {
//        RestAssured.baseURI = BASE_URL;
//        String nombre = getCellValue("Hoja1",1,2);
//        String apellido = getCellValue("Hoja1",1,3);
//        url = url+"/?firstname"+token;
//        RequestSpecification req = RestAssured.given().headers("Content-Type","application/json").params()log().all();
//        response = req.when().get(new URI(url));
//    }

    @Then("se obtiene el id y se valida con el obtenido previamente")
    public void seObtieneElIdYSeValidaConElObtenidoPreviamente() throws IOException, InterruptedException {
        String bookid = response.then().extract().body().jsonPath().getString("bookingid");
        String bookIdExcel = getCellValue("Hoja1",1,1);
        assertThat(bookid).contains(bookIdExcel);
        System.out.println(bookid);

//        String[] textElements = bookid.split(",");
//        bookid = textElements[0].replace("[","").trim();
//        Assert.assertEquals(bookid,bookIdExcel);
//        System.out.println(getCellValue("Hoja1",1,1));

    }

    @Then("se obtiene los datos del id y se almacena el nombre y el apellido")
    public void seObtieneLosDatosDelIdYSeAlmacenaElNombreYElApellido() throws IOException {
//        String data = response.then().extract().body().jsonPath().toString();
        String nombre = response.then().extract().body().jsonPath().getString("firstname");
        setCellValue("Hoja1",1,2,nombre);
        String apellido = response.then().extract().body().jsonPath().getString("lastname");
        setCellValue("Hoja1",1,3,apellido);
        Assert.assertNotNull(nombre);
        Assert.assertNotNull(apellido);

    }

    @Given("Obtener id booking {string} por nombre y apellido")
    public void obtenerIdBookingPorNombreYApellido(String url) throws IOException, URISyntaxException {
        RestAssured.baseURI = BASE_URL;
        String nombre = getCellValue("Hoja1",1,2);
        String apellido = getCellValue("Hoja1",1,3);
        url = url+"/?firstname="+nombre+"&lastname="+apellido;
        RequestSpecification req = RestAssured.given().headers("Content-Type","application/json").log().all();
        response = req.when().get(new URI(url));
    }

//    @Then("se guarda el nombre y apellido del booking creado")
//    public void seGuardaElNombreYApellidoDelBookingCreado() throws IOException {
//        String nombre = response.then().extract().body().jsonPath().getString("firstname");
//        String apellido = response.then().extract().body().jsonPath().getString("lastname");
//        setCellValue("Hoja1",1,2,nombre);
//        setCellValue("Hoja1",1,3,apellido);
//        System.out.println(getCellValue("Hoja1",1,1));
//        Assert.assertNotNull(nombre);
//        Assert.assertNotNull(apellido);
//
//    }


    //    @Then("Response  is array total {string}")
//    public void responseIsArrayWith(String arg0) throws JSONException {
//        response.then().statusCode(200);
//        response = response.then().extract().response();
//        scenario.log("Response Received == " + response.asPrettyString());
//        JSONArray resJson = new JSONArray(response.asString());
//        Assert.assertEquals(resJson.length()+"",arg0);


}