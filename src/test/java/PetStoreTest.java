import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.javafaker.Faker;
import dto.Category;
import dto.PetDTO;
import dto.PetResponseDTO;
import dto.Tag;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.PetApi;

import java.util.ArrayList;
import java.util.List;


public class PetStoreTest {
  private PetApi petApi = new PetApi();

  private Faker faker = new Faker();
  private int petId;


  @Test
  @DisplayName("Создание и получение питомца с минимальным количеством параметров")
  public void addPetMinTest() {
    PetDTO petDTO = PetDTO.builder()
        .id(faker.number().numberBetween(1, 1000))
        .name("Sharik")
        .status("available")
        .photoUrls(new ArrayList<>())
        .tags(new ArrayList<>())
        .build();
    PetResponseDTO actualPet = petApi.addNewPet(petDTO).extract().body().as(PetResponseDTO.class);
    PetResponseDTO petGetResponse = petApi.getPet(petDTO.getId()).extract().as(PetResponseDTO.class);
    petId = petGetResponse.getId();
    assertAll("",
        () -> assertEquals(petDTO.getId(), actualPet.getId(), "Incorrect id"),
        () -> assertEquals(petDTO.getName(), actualPet.getName(), "Incorrect name"),
        () -> assertEquals(petDTO.getStatus(), actualPet.getStatus(), "Incorrect status")
    );
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
        .id(faker.number().numberBetween(1, 1000))
        .name("Dogs")
        .build();
    tags.add(Tag.builder()
        .id(faker.number().numberBetween(1, 1000))
        .name("Friends")
        .build());
    photoUrls.add("https://w.forfun.com/fetch/75/756ac8864b07aaf0393073b2741213ad.jpeg");
    PetDTO petDTO = PetDTO.builder()
        .category(category)
        .id(faker.number().numberBetween(1, 1000))
        .name("Tuzik_test")
        .tags((List<Tag>) tags)
        .status("available")
        .photoUrls(photoUrls)
        .build();
    PetResponseDTO actualPet = petApi.addNewPet(petDTO).extract().body().as(PetResponseDTO.class);
    PetResponseDTO petGetResponse = petApi.getPet(petDTO.getId()).extract().as(PetResponseDTO.class);
    petId = petGetResponse.getId();
    assertAll("",
        () -> assertEquals(petDTO.getId(), actualPet.getId(), "Incorrect id"),
        () -> assertEquals(petDTO.getName(), actualPet.getName(), "Incorrect name"),
        () -> assertEquals(petDTO.getStatus(), actualPet.getStatus(), "Incorrect status"),
        () -> assertEquals(petDTO.getCategory(), actualPet.getCategory(), "Incorrect category"),
        () -> assertEquals(petDTO.getTags(), actualPet.getTags(), "Incorrect tags"),
        () -> assertEquals(petDTO.getPhotoUrls(), actualPet.getPhotoUrls(), "Incorrect status")
    );
    assertAll("",
        () -> assertEquals(petDTO.getId(), petGetResponse.getId(), "Incorrect id"),
        () -> assertEquals(petDTO.getName(), petGetResponse.getName(), "Incorrect name"),
        () -> assertEquals(petDTO.getStatus(), petGetResponse.getStatus(), "Incorrect status"),
        () -> assertEquals(petDTO.getCategory(), petGetResponse.getCategory(), "Incorrect category"),
        () -> assertEquals(petDTO.getTags(), petGetResponse.getTags(), "Incorrect tags"),
        () -> assertEquals(petDTO.getPhotoUrls(), petGetResponse.getPhotoUrls(), "Incorrect status")
    );
  }

  @AfterEach
  public void cleaningPet() {
    petApi.deletePet(petId);
  }
}