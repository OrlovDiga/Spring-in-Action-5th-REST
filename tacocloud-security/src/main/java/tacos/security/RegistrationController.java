package tacos.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tacos.data.UserRepo;

/**
 * @author Orlov Diga
 */
@Slf4j
@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserRepo userRepo;
    private final PasswordEncoder encoder;

    @Autowired
    public RegistrationController(UserRepo userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    @GetMapping
    public String registrationForm() {
        log.info("Sent registration form");

        return "registration";
    }

    @PostMapping
    public String processRegistration(RegistrationForm form) {
        log.info("Received form: {}", form);

        userRepo.save(form.toUser(encoder));

        return "redirect:/login";
    }
}
