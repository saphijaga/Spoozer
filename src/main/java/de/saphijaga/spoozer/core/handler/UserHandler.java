package de.saphijaga.spoozer.core.handler;

import de.saphijaga.spoozer.persistence.domain.User;
import de.saphijaga.spoozer.core.service.UserService;
import de.saphijaga.spoozer.persistence.service.UserPersistenceService;
import de.saphijaga.spoozer.web.details.UserDetails;
import de.saphijaga.spoozer.web.domain.request.ChangePasswordRequest;
import de.saphijaga.spoozer.web.domain.request.RegisterUserRequest;
import de.saphijaga.spoozer.web.domain.request.SaveUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 * Created by samuel on 16.10.15.
 */
@Component
public class UserHandler implements UserService {

    @Autowired
    private UserPersistenceService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<UserDetails> registerUser(RegisterUserRequest request) {
        Optional<User> user = userService.saveUser(toUser(request));
        return toUserDetails(user);
    }

    private User toUser(RegisterUserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        return user;
    }

    @Override
    public Optional<UserDetails> getUserDetails(String id) {
        Optional<User> user = userService.getUser(id);
        return toUserDetails(user);
    }

    @Override
    public Optional<UserDetails> getUserDetailsByUsername(String username) {
        Optional<User> user = userService.getUserByUsername(username);
        return toUserDetails(user);
    }

    private Optional<UserDetails> toUserDetails(Optional<User> user) {
        if (!user.isPresent()) {
            return empty();
        }
        UserDetails details = new UserDetails();
        details.setId(user.get().getId());
        details.setUsername(user.get().getUsername());
        details.setEmail(user.get().getEmail());
        details.setName(user.get().getName());
        return of(details);
    }


    @Override
    public Optional<UserDetails> saveUserDetails(UserDetails userDetails, SaveUserRequest request) {
        Optional<User> user = userService.getUser(userDetails.getId());
        if (user.isPresent()) {
            updateUser(user.get(), request);
        }
        Optional<User> userUpdate = userService.saveUser(user.get());

        return toUserDetails(userUpdate);
    }

    @Override
    public Optional<UserDetails> changeUserPassword(UserDetails userDetails, ChangePasswordRequest changePasswordRequest) {
        Optional<User> user = userService.getUser(userDetails.getId());
        Optional<User> userUpdate = userService.getUser(userDetails.getId());
        if (user.isPresent()) {
            if (passwordEncoder.matches(changePasswordRequest.getOldpassword(), user.get().getPassword())){
                user.get().setPassword(passwordEncoder.encode(changePasswordRequest.getPassword()));
                userUpdate = userService.saveUser(user.get());
            }
        }
        return toUserDetails(userUpdate);
    }

    private void updateUser(User user, SaveUserRequest request) {
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
    }

}