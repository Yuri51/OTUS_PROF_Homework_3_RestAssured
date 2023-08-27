import static org.hamcrest.Matchers.equalTo;

import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.PetApi;

public class PetStoreNegativeTest {

  private PetApi petApi = new PetApi();

  @Test
  @DisplayName("Получение питомца с несуществующим id")
  public void getNotExistPetTest() {
    ValidatableResponse response = petApi.getNotExistPet(7676556)
        .body("code", equalTo(1))
        .body("type", equalTo("error"))
        .body("message", equalTo("Pet not found"));
  }
}
