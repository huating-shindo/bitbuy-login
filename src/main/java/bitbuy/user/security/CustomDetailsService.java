package bitbuy.user.security;

import bitbuy.user.model.CustomUser;
import bitbuy.user.model.Role;
import bitbuy.user.model.User;
import bitbuy.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomDetailsService implements UserDetailsService {
    @Autowired
    UserRepository oauthDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        Optional<User> user = null;
        try {
            user = oauthDao.findByUsername(username);
            return user.map(u ->
                    {
                        List<String> roleList = new ArrayList<>();
                        for(Role role:u.getRoles()) {
                            roleList.add(role.getRoleName());
                        }
                        UserDetails ud =  CustomUser.builder()
                            .username(u.getUsername())
                            .password(u.getPassword())
                            .roles(roleList.toArray(new String[0])).build();
                        return ud;
                    }
            ).orElseThrow(() -> new Exception());
        } catch (Exception e) {
            e.printStackTrace();
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }
    }


}