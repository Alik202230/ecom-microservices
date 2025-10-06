package am.itspace.user_service.controller;

import am.itspace.user_service.dto.UserRequest;
import am.itspace.user_service.dto.UserResponse;
import am.itspace.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/api/users")
  public List<UserResponse> getUsers() {
    return userService.getUsers();
  }

  @PostMapping("/api/save")
  public String saveUser(@RequestBody UserRequest request) {
    userService.saveUser(request);
    return "Saved";
  }

}
