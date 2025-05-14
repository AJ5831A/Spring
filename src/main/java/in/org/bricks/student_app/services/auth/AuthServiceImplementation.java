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
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthServiceImplementation(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDto createUser(SignupRequest signupRequest) {
        User user = new User();
         user.setName(signupRequest.getName());
         user.setEmail(signupRequest.getEmail());
         user.setPassword(bCryptPasswordEncoder.encode(signupRequest.getPassword()));
         user.setUserRole(UserRole.CUSTOMER);
         User userCreated = userRepository.save(user);
         UserDto createdUserDto = new UserDto();
         createdUserDto.setId(createdUserDto.getId());
         createdUserDto.setName(createdUserDto.getName());
         createdUserDto.setEmail(createdUserDto.getEmail());
         createdUserDto.setUserRole(createdUserDto.getUserRole());
        return null;
    }
}
