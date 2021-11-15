package bitbuy.user.web;

import bitbuy.user.service.UserService;
import bitbuy.user.model.Status;
import bitbuy.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;

    @CrossOrigin()
    @PreAuthorize("permitAll()")
    @PostMapping("/register")
    public Status registerUser(@Valid @RequestBody User newUser) {
        try {
            userService.registerNewUserAccount(newUser);
        } catch (Exception e) {
            return Status.FAILURE;
        }
        return Status.SUCCESS;
    }

    @CrossOrigin()
    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@Valid @RequestBody User userReq) {
        Optional<User> op = userService.loginUser(userReq.getUsername(), userReq.getPassword());
        return ResponseEntity.of(op);
    }

    @CrossOrigin()
//    @PreAuthorize("permitAll()")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/users/{uuid}")
    public ResponseEntity<User> getUserInfo(@PathVariable long uuid) {
        Optional<User> op = userService.getUserInfo(uuid);
        return ResponseEntity.of(op);
    }

    @CrossOrigin()
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/users/{uuid}")
    public ResponseEntity<User> updateUser(@PathVariable long uuid, @Valid @RequestBody User userReq) {
        Optional<User> op = userService.updateUser(uuid, userReq);
        return ResponseEntity.of(op);
    }

    @CrossOrigin()
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<List<User>> list() {
        return ResponseEntity.of(Optional.ofNullable(userService.getAll()));
    }

}