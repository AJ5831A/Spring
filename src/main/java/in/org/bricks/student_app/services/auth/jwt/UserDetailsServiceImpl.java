package in.org.bricks.student_app.services.auth.jwt;

import in.org.bricks.student_app.entities.User;
import in.org.bricks.student_app.repositries.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findFirstByEmail(email);
        if(optionalUser.isEmpty()) throw new UsernameNotFoundException("User not Found" , null);
        return new org.springframework.security.core.userdetails.User(optionalUser.get().getEmail() , optionalUser.get().getPassword() , new ArrayList<>());
    }
}
