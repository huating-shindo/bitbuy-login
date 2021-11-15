package bitbuy.user.service;

import bitbuy.user.error.LoginFailedException;
import bitbuy.user.model.User;
import bitbuy.user.repository.RoleRepository;
import bitbuy.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    public User registerNewUserAccount(User userDto) throws Exception{
        Optional<User> op = userRepository.findByUsername(userDto.getUsername());
        if (op.isPresent()) {
            throw new Exception("There is an account with that username: "
                    + userDto.getUsername());
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRoles(new ArrayList<>(roleRepository.findAll()));
        return userRepository.save(user);
    }

    public Optional<User> loginUser(String username, String password) throws LoginFailedException {
        Optional<User> op = userRepository.findByUsername(username);
        return op.map(u -> {
            User matchUser = null;
            if(passwordEncoder.matches(password, u.getPassword())) {
                matchUser = u;
            } else {
                throw new LoginFailedException("Password incorrect");
            }
            return Optional.of(matchUser);
        }).orElseThrow(() -> new LoginFailedException("No user found"));
    }

    public Optional<User> getUserInfo(long uuid) {
        Optional<User> op = userRepository.findById(uuid);
        return op;
    }

    public Optional<User> updateUser(long uuid, User userReq) {
        Optional<User> op = userRepository.findById(uuid);
        if(op.isPresent()) {
            User user = op.get();
            user.setPassword(userReq.getPassword());
            user.setUsername(userReq.getUsername());
            user.setEmail(userReq.getEmail());
            user.setPhone(userReq.getPhone());
            userRepository.save(user);
        }
        return op;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }
}
