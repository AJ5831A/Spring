package in.org.bricks.student_app.services.auth;

import in.org.bricks.student_app.dtos.SignupRequest;
import in.org.bricks.student_app.dtos.UserDto;

public interface AuthService {
    UserDto createUser(SignupRequest signupRequest);
}
