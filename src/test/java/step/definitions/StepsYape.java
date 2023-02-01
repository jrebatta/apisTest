package step.definitions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import utils.Excel;
import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;


public class StepsYape extends Excel {

    private Response response;
    private final String BASE_URL = "https://restful-booker.herokuapp.com";

    public String token;

    public Map<String, String> defaultHeaders(){
        Map<String, String> dHeaders = new HashMap<String, String>();
        dHeaders.put("Content-Type", "application/json");

        return dHeaders;
    }

    public Map<String, String> tokentHeaders() throws IOException {
        Map<String, String> tHeaders = new HashMap<String, String>();
        tHeaders.put("Content-Type", "application/json");
        tHeaders.put("Accept", "application/json");
        tHeaders.put("Cookie", "token="+getCellValue("Hoja1",1,0));

        return tHeaders;
    }

    @Given("Obtener llamada a {string} con {string} y {string}")
    public void obtenerLlamadaAConY(String url, String user, String pass) throws URISyntaxException {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification req = RestAssured.given().headers(defaultHeaders()).log().all().
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
        Assert.assertNotNull(token);
    }

    @Given("Obtener llamada a {string}")
    public void obtenerLlamadaA(String url) throws URISyntaxException {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification req = RestAssured.given().headers(defaultHeaders()).log().all();
        response = req.when().get(new URI(url));
    }

    @Then("se obtiene respuesta de los IDs")
    public void seObtieneRespuestaDeLosIDs() {
        String ids = response.then().extract().body().jsonPath().getString("bookingid");
        Assert.assertNotNull(ids);
    }

    @Given("crear bookin {string} con datos {string} {string} {string} {string} {string} {string} {string}")
    public void crearBookinConDatos(String url, String firstname, String lastname, String totalprice, String depositpaid, String checkin, String checkout, String additionalneeds) throws URISyntaxException {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification req = RestAssured.given().headers(defaultHeaders()).log().all().
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
    public void seObtieneElIdDelBookingCreadoYSeGuardaElNombreYApellido() throws IOException, JSONException {
        String bookid = response.then().extract().body().jsonPath().getString("bookingid");
        JSONObject bookingData = new JSONObject(response.then().extract().body().jsonPath().getJsonObject("booking").toString());
        String nombre = bookingData.getString("firstname");
        String apellido = bookingData.getString("lastname");
        setCellValue("Hoja1",1,1,bookid);
        setCellValue("Hoja1",1,2,nombre);
        setCellValue("Hoja1",1,3,apellido);
        Assert.assertNotNull(bookid);
        Assert.assertNotNull(nombre);
        Assert.assertNotNull(apellido);

    }

    @Given("Obtener llamada a {string} con id")
    public void obtenerLlamadaAConId(String url) throws URISyntaxException, IOException {
        RestAssured.baseURI = BASE_URL;
        String id = getCellValue("Hoja1",1,1);
        url = url+"/"+id;
        RequestSpecification req = RestAssured.given().headers(defaultHeaders()).log().all();
        response = req.when().get(new URI(url));

    }

    @Then("se obtiene el id y se valida con el obtenido previamente")
    public void seObtieneElIdYSeValidaConElObtenidoPreviamente() throws IOException {
        String bookid = response.then().extract().body().jsonPath().getString("bookingid");
        String bookIdExcel = getCellValue("Hoja1",1,1);
        assertThat(bookid).contains(bookIdExcel);
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
        RequestSpecification req = RestAssured.given().headers(defaultHeaders()).log().all();
        response = req.when().get(new URI(url));
    }

    @Given("Obtener llamada post a {string} con id y actualizar {string} y {string}")
    public void obtenerLlamadaPostAConIdYActualizarY(String url, String firstname, String lastname) throws IOException, URISyntaxException {
        RestAssured.baseURI = BASE_URL;
        String id = getCellValue("Hoja1",1,1);
        url = url+"/"+id;
        RequestSpecification req = RestAssured.given().headers(tokentHeaders()).
                body("{ \"firstname\" : \""+firstname+ "\", " +
                        "\"lastname\" : \""+lastname+"\"}").log().all();
        response = req.when().patch(new URI(url));
    }

    @Then("se valida que el {string} y {string} hayan cambiado")
    public void seValidaQueElYHayanCambiado(String firstname, String lastname) {
        String nombre = response.then().extract().body().jsonPath().getString("firstname");
        String apellido = response.then().extract().body().jsonPath().getString("lastname");
        Assert.assertEquals(nombre,firstname);
        Assert.assertEquals(apellido,lastname);

    }

    @Given("Obtener llamada a {string} con id a eliminar")
    public void obtenerLlamadaAConIdAEliminar(String url) throws IOException, URISyntaxException {
        RestAssured.baseURI = BASE_URL;
        String id = getCellValue("Hoja1",1,1);
        url = url+"/"+id;
        RequestSpecification req = RestAssured.given().headers(tokentHeaders()).log().all();
        response = req.when().delete(new URI(url));
    }

    @When("se valida otro codigo de respuesta{string}")
    public void seValidaOtroCodigoDeRespuesta(String responseMessage) {
        int responseCode = response.then().log().all().extract().statusCode();
        Assert.assertEquals(responseMessage, responseCode+"");
    }

    @Then("se obtiene respuesta y se valida")
    public void seObtieneRespuestaYSeValida() {
        String bookid = response.then().extract().body().asString();
        Assert.assertEquals(bookid,"Created");
    }

    @Then("se obtiene mensaje de datos incorrectos")
    public void seObtieneMensajeDeDatosIncorrectos() {
        String reason = response.then().extract().body().jsonPath().getString("reason");
        Assert.assertEquals(reason,"Bad credentials");
    }

    @Then("no se obtiene respuesta de los IDs")
    public void noSeObtieneRespuestaDeLosIDs() {
        String ids = response.then().extract().body().asString();
        Assert.assertEquals(ids,"Not Found");
    }
}