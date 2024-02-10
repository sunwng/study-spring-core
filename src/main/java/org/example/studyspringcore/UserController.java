package org.example.studyspringcore;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/users")
    void saveUser(
        @RequestParam String name,
        @RequestParam String email
    ) {
        userService.create(name, email);
    }
}
