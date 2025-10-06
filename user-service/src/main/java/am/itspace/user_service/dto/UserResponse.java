package am.itspace.user_service.dto;

import am.itspace.user_service.model.enums.UserRole;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String phone;
  private UserRole role;
  private AddressDto address;
  private LocalDateTime createdDateTime;
}
