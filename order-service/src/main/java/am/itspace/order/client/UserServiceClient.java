package am.itspace.order.client;

import am.itspace.order.dto.UserResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface UserServiceClient {

  @GetExchange("/api/users/{id}")
  UserResponse getUserDetails(@PathVariable Long id);

}
