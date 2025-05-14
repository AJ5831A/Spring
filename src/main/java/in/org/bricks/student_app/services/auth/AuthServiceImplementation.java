package in.org.bricks.student_app.services.auth;

import in.org.bricks.student_app.dtos.SignupRequest;
import in.org.bricks.student_app.dtos.UserDto;
import in.org.bricks.student_app.entities.User;
import in.org.bricks.student_app.enums.UserRole;
import in.org.bricks.student_app.repositries.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
 public class AuthServiceImplementation implements AuthService{

    private  final UserRepository userRepository;

    public AuthServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(SignupRequest signupRequest) {
        User user = new User();
         user.setName(signupRequest.getName());
         user.setEmail(signupRequest.getEmail());
         user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
         user.setUserRole(UserRole.CUSTOMER);
         User userCreated = userRepository.save(user);
         UserDto createdUserDto = new UserDto();
         createdUserDto.setId(userCreated.getId());
         createdUserDto.setName(userCreated.getName());
         createdUserDto.setEmail(userCreated.getEmail());
         createdUserDto.setUserRole(userCreated.getUserRole());
        return createdUserDto;
    }
}
