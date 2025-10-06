package am.itspace.order.dto;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
  private String street;
  private String city;
  private String state;
  private String zipCode;
  private String country;
}
