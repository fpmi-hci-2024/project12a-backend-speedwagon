package by.speedteam.speedwagon.services;

import by.speedteam.speedwagon.models.ERole;
import by.speedteam.speedwagon.models.Role;
import by.speedteam.speedwagon.models.User;
import by.speedteam.speedwagon.payload.requests.users.RegisterRequest;
import by.speedteam.speedwagon.payload.requests.users.UpdateUserRequest;
import by.speedteam.speedwagon.payload.responses.users.UserDto;
import by.speedteam.speedwagon.repositories.RoleRepository;
import by.speedteam.speedwagon.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserService(PasswordEncoder encoder, RoleRepository roleRepository, UserRepository userRepository) {
        this.encoder = encoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> usersDto = new ArrayList<>();

        for (User user : users) {
            UserDto userDto = convertToDto(user);
            usersDto.add(userDto);
        }

        return usersDto;
    }

    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }

    public UserDto getUserDtoById(long id) {
        Optional<User> userData = userRepository.findById(id);
        if (userData.isPresent()) {
            return convertToDto(userData.get());
        } else {
            throw new RuntimeException("User with id = " + id + " not found");
        }
    }

    public UserDto getUserByEmail(String email) {
        Optional<User> userData = userRepository.findByEmail(email);
        if (userData.isPresent()) {
            return convertToDto(userData.get());
        } else {
            throw new RuntimeException("User with email = " + email + " not found");
        }
    }

    public boolean isEmailAlreadyExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean isPhoneAlreadyExists(String phone) {
        return userRepository.existsByPhone(phone);
    }

    public User createUser(User user) {
        if (isEmailAlreadyExists(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        if (isPhoneAlreadyExists(user.getPhone())) {
            throw new RuntimeException("Phone already exists");
        }
        return userRepository.save(user);
    }

    public UserDto registerUser(RegisterRequest registerRequest) {
        Role role = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Role " + ERole.ROLE_USER + " not found"));

        User newUser = new User(
                registerRequest.getEmail(),
                encoder.encode(registerRequest.getPassword()),
                registerRequest.getPhone(),
                registerRequest.getFirstname(),
                registerRequest.getLastname(),
                registerRequest.getSurname(),
                role
        );

        return convertToDto(userRepository.save(newUser));
    }

    public void assignAdminRole(long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User existingUser = user.get();

            Optional<Role> roleData = roleRepository.findByName(ERole.ROLE_ADMIN);
            if (roleData.isPresent()) {
                existingUser.setRole(roleData.get());
            } else {
                throw new RuntimeException("Role " + ERole.ROLE_ADMIN + " not found");
            }
            userRepository.save(existingUser);
        } else {
            throw new RuntimeException("User with id = " + id + " not found");
        }
    }

    public UserDto updateUser(long id, UpdateUserRequest updateUserRequest) {
        Optional<User> userData = userRepository.findById(id);
        if (userData.isPresent()) {
            User existingUser = userData.get();
            String newEmail = updateUserRequest.getEmail();
            String newPassword = updateUserRequest.getPassword();
            String newPhone = updateUserRequest.getPhone();
            String newFirstname = updateUserRequest.getFirstname();
            String newLastname = updateUserRequest.getLastname();
            String newSurname = updateUserRequest.getSurname();
            if (newEmail != null) {
                existingUser.setEmail(newEmail);
            }
            if (newPassword != null) {
                existingUser.setPassword(encoder.encode(newPassword));
            }
            if (newPhone != null) {
                existingUser.setPhone(newPhone);
            }
            if (newFirstname != null) {
                existingUser.setFirstname(newFirstname);
            }
            if (newLastname != null) {
                existingUser.setLastname(newLastname);
            }
            existingUser.setSurname(newSurname);
            return convertToDto(userRepository.save(existingUser));
        } else {
            throw new RuntimeException("User with id = " + id + " not found");
        }
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    private UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setPhone(user.getPhone());
        userDto.setFirstname(user.getFirstname());
        userDto.setLastname(user.getLastname());
        userDto.setSurname(user.getSurname());
        userDto.setRole(user.getRole().getName());
        return userDto;
    }
}
