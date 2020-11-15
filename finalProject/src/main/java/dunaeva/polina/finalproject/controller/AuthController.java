package dunaeva.polina.finalproject.controller;

import dunaeva.polina.finalproject.config.jwt.JwtProvider;
import dunaeva.polina.finalproject.entity.User;
import dunaeva.polina.finalproject.payload.AuthRequest;
import dunaeva.polina.finalproject.payload.AuthResponse;
import dunaeva.polina.finalproject.payload.RegistrationRequest;
import dunaeva.polina.finalproject.payload.ResetRequest;
import dunaeva.polina.finalproject.service.RedisService;
import dunaeva.polina.finalproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private RedisService redisService;
    @Autowired
    public JavaMailSender emailSender;

    @PostMapping("/register")
    public String registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        User u = new User();
        u.setPassword(registrationRequest.getPassword());
        u.setEmail(registrationRequest.getEmail());
        userService.saveUser(u, registrationRequest.getRole());
        return "OK";
    }

    @PostMapping("/auth")
    public AuthResponse auth(@RequestBody AuthRequest request) {
        User user = userService.findByLoginAndPassword(request.getEmail(), request.getPassword());
        String token = jwtProvider.generateToken(user.getEmail());
        return new AuthResponse(token);
    }

    @PostMapping("/auth/forgot_password")
    public String forgotPassword(@RequestBody String email) {
        User user = userService.findByEmail(email);
        String token = UUID.randomUUID().toString();
        redisService.setData(token, user);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Reset Password");
        message.setText("Your token is "+token);
        this.emailSender.send(message);

        return "OK";
    }

    @PostMapping("/auth/reset")
    public String resetPassword(@RequestBody ResetRequest resetRequest) {
        User user = redisService.getValue(resetRequest.getToken());
        if (user != null) {
            user.setPassword(resetRequest.getPassword());
            userService.saveUser(user);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());
            message.setSubject("Reset Password");
            message.setText("Your password was successfully changed");
            this.emailSender.send(message);
            return "OK";
        }
        return "FAILED RESET PASSWORD";
    }

    @GetMapping("/auth/check_code")
    public String resetPassword(@RequestBody String token) {
        //проверить есть ли код в редисе
        return redisService.keyExist(token)?
                "Token is exist":
                "Token is expire";
    }
}
