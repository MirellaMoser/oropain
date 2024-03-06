package ch.zhaw.mosltech.NoPainIsGainBackend.boundary;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserService {
    
    @GetMapping("/me")
    public String getCurrentUserName(Principal principal) 
    {
        return principal.getName();
    }
}
