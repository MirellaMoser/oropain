package ch.zhaw.mosltech.NoPainIsGainBackend.security;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ch.zhaw.mosltech.NoPainIsGainBackend.entity.User;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.UserRepository;

/**
 * Custom implementation of {@link UserDetailsService} to load user-specific
 * data.
 * <p>
 * This service is used by Spring Security to retrieve user details from the
 * database using the
 * {@link UserRepository}. It is part of the authentication process, enabling
 * the security framework
 * to perform user authentication and authorization.
 * </p>
 */
@Service
public class DBUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository; // Repository for accessing user data.

    /**
     * Loads the user by their username (login name).
     * <p>
     * This method fetches a user by their login name from the database. If the user
     * is found,
     * it constructs a {@link UserDetails} object for authentication purposes. If
     * the user is not found,
     * it throws a {@link UsernameNotFoundException}.
     * </p>
     *
     * @param loginName The login name of the user to load.
     * @return A {@link UserDetails} instance representing the user.
     * @throws UsernameNotFoundException if the user with the given login name is
     *                                   not found.
     */
    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findById(loginName);

        // Check if user is present in the repository.
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found with login name: " + loginName);
        }

        // Define user authorities. Here, every user is granted with "User" authority by
        // default.
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.get().getRoleName()));

        // Create and return a Spring Security User object with the loginName, password,
        // and authorities.
        return new org.springframework.security.core.userdetails.User(
                loginName,
                user.get().getPasswordHash(),
                authorities);
    }
}