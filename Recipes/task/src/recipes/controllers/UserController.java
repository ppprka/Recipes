package recipes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import recipes.models.User;
import recipes.services.UserDetailService;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class UserController {

    private final UserDetailService userService;

    @Autowired
    public UserController(UserDetailService service) {
        this.userService = service;
    }

    @PostMapping(path = "/api/register", consumes = APPLICATION_JSON_VALUE)
    public void register(@Valid @RequestBody User user) {
        userService.registerNewUser(user.getEmail(), user.getPassword());
    }


}
