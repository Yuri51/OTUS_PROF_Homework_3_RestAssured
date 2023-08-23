package services;

import static io.restassured.RestAssured.given;

import dto.PetDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;

public class PetApi {

  private static final String BASE_URI = System.getProperty("base.uri");
  private static final String ADD_PET_PATH = "/pet";
  private static final String DELETE_PET_PATH = "/pet/{petId}";

  private static final String GET_PET_PATH = "/pet/{petId}";

  private RequestSpecification requestSpecification;
  private ResponseSpecification responseSpecification;

  public PetApi() {
    requestSpecification = given()
        .baseUri(BASE_URI)
        .contentType(ContentType.JSON)
        .log().all();

    responseSpecification = RestAssured.expect()
        .statusCode(200)
        .statusLine("HTTP/1.1 200 OK")
        .contentType(ContentType.JSON)
        .time(Matchers.lessThan(10000L))
        .log().all();
  }

  public ValidatableResponse addNewPet(PetDTO petDTO) {
    return given(requestSpecification)
        .body(petDTO)
        .when()
        .post(ADD_PET_PATH)
        .then()
        .spec(responseSpecification)
        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/Pet.json"))
        .log().all();
  }

  public ValidatableResponse getPet(long id) {
    return given(requestSpecification)
        .when()
        .get(GET_PET_PATH, id)
        .then()
        .spec(responseSpecification)
        .log().all();
  }

  public ValidatableResponse getNotExistPet(long id) {
    return given(requestSpecification)
        .when()
        .get(GET_PET_PATH, id)
        .then()
        .log().all();
  }

  public ValidatableResponse deletePet(long id) {
    return given(requestSpecification)
        .when()
        .delete(DELETE_PET_PATH, id)
        .then()
        .spec(responseSpecification)
        .log().all();
  }
}
