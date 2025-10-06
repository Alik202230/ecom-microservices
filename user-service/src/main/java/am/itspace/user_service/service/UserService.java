package am.itspace.user_service.service;

import am.itspace.user_service.dto.UserRequest;
import am.itspace.user_service.dto.UserResponse;
import am.itspace.user_service.mapper.UserMapper;
import am.itspace.user_service.model.User;
import am.itspace.user_service.model.enums.UserRole;
import am.itspace.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public List<UserResponse> getUsers() {
    log.info("Retrieving all users");
    return userRepository.findAll().stream()
        .map(this.userMapper::toUserResponse)
        .toList();
  }

  public void saveUser(UserRequest request) {
    User user = this.userMapper.toUserRequest(request);
    user.setUserRole(UserRole.CUSTOMER);
    this.userRepository.save(user);
    log.info("Saved with id {} persisted", user.getId());
  }

}
