package am.itspace.user_service.mapper;

import am.itspace.user_service.dto.UserRequest;
import am.itspace.user_service.dto.UserResponse;
import am.itspace.user_service.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

  User toUserRequest(UserRequest request);

  @Mapping(target = "role", source = "userRole")
  @Mapping(target = "createdDateTime", source = "createdAt")
  UserResponse toUserResponse(User user);

}
