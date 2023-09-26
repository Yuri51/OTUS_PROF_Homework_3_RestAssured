package dto;

import lombok.*;

import java.util.Collections;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class PetResponseDTO {
  private Category category;
  private Integer id;
  private String name;
  private List<String> photoUrls;
  private String status;
  private List<Tag> tags;
}

