package in.org.bricks.student_app.dtos;

import in.org.bricks.student_app.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private UserRole userRole;
}
