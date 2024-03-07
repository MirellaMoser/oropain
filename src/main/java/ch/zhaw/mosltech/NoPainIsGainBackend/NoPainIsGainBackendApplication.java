package ch.zhaw.mosltech.NoPainIsGainBackend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class NoPainIsGainBackendApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(NoPainIsGainBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
		System.out.println("HASH:"+ enc.encode("password"));
	}

	
}
