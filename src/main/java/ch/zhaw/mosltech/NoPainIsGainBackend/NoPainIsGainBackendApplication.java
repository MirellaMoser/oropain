package ch.zhaw.mosltech.NoPainIsGainBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the NoPainIsGain backend service.
 * <p>
 * This class serves as the entry point for the Spring Boot application. It is annotated with
 * {@link SpringBootApplication}, which enables auto-configuration, component scanning, and property support
 * typical for Spring applications. By calling {@link SpringApplication#run}, it boots the Spring application context,
 * starting the embedded web server and initializing the Spring environment.
 * </p>
 */
@SpringBootApplication // Marks this application as a Spring Boot application.
public class NoPainIsGainBackendApplication {

    /**
     * The main method that launches the Spring Boot application.
     * <p>
     * Uses {@link SpringApplication#run} to bootstrap the application, passing in the current class as
     * a parameter along with command-line arguments. This method kicks off the auto-configuration and server
     * startup process.
     * </p>
     * 
     * @param args Command-line arguments passed during application startup.
     */
    public static void main(String[] args) {
        SpringApplication.run(NoPainIsGainBackendApplication.class, args);
    }
}