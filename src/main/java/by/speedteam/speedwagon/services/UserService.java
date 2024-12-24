package by.speedteam.speedwagon.services;

import by.speedteam.speedwagon.models.ERole;
import by.speedteam.speedwagon.models.Role;
import by.speedteam.speedwagon.models.User;
import by.speedteam.speedwagon.payload.requests.RegisterRequest;
import by.speedteam.speedwagon.repositories.RoleRepository;
import by.speedteam.speedwagon.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserService(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean isEmailAlreadyExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean isPhoneAlreadyExists(String phone) {
        return userRepository.existsByPhone(phone);
    }

    public User registerUser(RegisterRequest registerRequest) {
        Role role = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Role " + ERole.ROLE_USER + " not found"));

        User newUser = new User(
                registerRequest.getEmail(),
                registerRequest.getPassword(),
//                encoder.encode(registerRequest.getPassword()),
                registerRequest.getPhone(),
                registerRequest.getFirstname(),
                registerRequest.getLastname(),
                registerRequest.getSurname(),
                role
        );

        return userRepository.save(newUser);
    }

    public User updateUser(long id, User updatedUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFirstname(updatedUser.getFirstname());
        user.setLastname(updatedUser.getLastname());
        user.setSurname(updatedUser.getSurname());
        user.setEmail(updatedUser.getEmail());
        user.setPhone(updatedUser.getPhone());
        user.setRole(updatedUser.getRole());

        return userRepository.save(user);
    }

    public void deleteUser(long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }
}
