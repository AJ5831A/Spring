package in.org.bricks.student_app.controllers;

import in.org.bricks.student_app.dtos.AuthenticationRequest;
import in.org.bricks.student_app.dtos.AuthenticationResponse;
import in.org.bricks.student_app.dtos.SignupRequest;
import in.org.bricks.student_app.dtos.UserDto;
import in.org.bricks.student_app.services.auth.AuthService;
import in.org.bricks.student_app.services.auth.jwt.UserDetailsServiceImpl;
import in.org.bricks.student_app.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService, JwtUtil jwtUtil){
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest){
        UserDto createdUserDto = authService.createUser(signupRequest);
        if(createdUserDto == null){
            return new ResponseEntity<>("User not created. Call your Organisation!" , HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createdUserDto , HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest , HttpServletResponse response) throws IOException {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail() , authenticationRequest.getPassword()));
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect username or password");
        }catch (DisabledException disabledException){
            response.sendError(HttpServletResponse.SC_NOT_FOUND , "User not active");
            return null;
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final String jwt = jwtUtil.genrateToken(userDetails.getUsername());
        return new AuthenticationResponse(jwt);
    }

}
