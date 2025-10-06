package am.itspace.user_service.dto;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

  private String firstName;
  private String lastName;
  private String email;
  private String phone;
  private AddressDto address;

}
