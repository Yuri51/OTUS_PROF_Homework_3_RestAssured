import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import dto.Category;
import dto.PetDTO;
import dto.PetResponseDTO;
import dto.Tag;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.PetApi;
import java.util.ArrayList;
import java.util.List;



public class PetStoreTest {

  PetApi petApi = new PetApi();

  @Test
  @DisplayName("Создание питомца с минимальным количеством параметров")
  public void addPetMinTest() {
    PetDTO petDTO = PetDTO.builder()
        .id(123L)
        .name("Sharik")
        .status("available")
        .photoUrls(new ArrayList<>())
        .tags(new ArrayList<>())
        .build();
    ValidatableResponse response = petApi.addNewPet(petDTO)
        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/Pet.json"));
    PetResponseDTO actualPet = response.extract().body().as(PetResponseDTO.class);
    assertAll("",
        () -> assertEquals(petDTO.getId(), actualPet.getId(), "Incorrect id"),
        () -> assertEquals(petDTO.getName(), actualPet.getName(), "Incorrect name"),
        () -> assertEquals(petDTO.getStatus(), actualPet.getStatus(), "Incorrect status")
    );
  }

  @Test
  @DisplayName("Получение питомца с минимальным набором параметров")
  public void getPetMinParameterTest() {
    PetDTO petDTO = PetDTO.builder()
        .id(435L)
        .name("Bobik")
        .status("available")
        .photoUrls(new ArrayList<>())
        .tags(new ArrayList<>())
        .build();
    ValidatableResponse response = petApi.addNewPet(petDTO)
        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/Pet.json"));
    PetResponseDTO actualPet = response.extract().body().as(PetResponseDTO.class);
    PetResponseDTO petGetResponse = petApi.getPet(petDTO.getId()).extract().as(PetResponseDTO.class);
    assertAll("",
        () -> assertEquals(petDTO.getId(), petGetResponse.getId(), "Incorrect id"),
        () -> assertEquals(petDTO.getName(), petGetResponse.getName(), "Incorrect name"),
        () -> assertEquals(petDTO.getStatus(), petGetResponse.getStatus(), "Incorrect status")
    );
  }

  @Test
  @DisplayName("Создание питомца с максимальным количеством параметров")
  public void addPetMaxTest() {
    List<String> photoUrls = new ArrayList<>();
    List<Tag> tags = new ArrayList<>();
    Category category = Category.builder()
        .id(134L)
        .name("Dogs")
        .build();
    tags.add(Tag.builder()
        .id(11L)
        .name("Friends")
        .build());
    photoUrls.add("https://w.forfun.com/fetch/75/756ac8864b07aaf0393073b2741213ad.jpeg");
    PetDTO petDTO = PetDTO.builder()
        .category(category)
        .id(123L)
        .name("Tuzik")
        .tags((List<Tag>) tags)
        .status("available")
        .photoUrls(photoUrls)
        .build();
    ValidatableResponse response = petApi.addNewPet(petDTO)
        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/Pet.json"));
    PetResponseDTO actualPet = response.extract().body().as(PetResponseDTO.class);
    assertAll("",
        () -> assertEquals(petDTO.getId(), actualPet.getId(), "Incorrect id"),
        () -> assertEquals(petDTO.getName(), actualPet.getName(), "Incorrect name"),
        () -> assertEquals(petDTO.getStatus(), actualPet.getStatus(), "Incorrect status"),
        () -> assertEquals(petDTO.getCategory(), actualPet.getCategory(), "Incorrect category"),
        () -> assertEquals(petDTO.getTags(), actualPet.getTags(), "Incorrect tags"),
        () -> assertEquals(petDTO.getPhotoUrls(), actualPet.getPhotoUrls(), "Incorrect status")
    );
  }
}

