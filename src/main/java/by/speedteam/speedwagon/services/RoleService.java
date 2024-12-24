package by.speedteam.speedwagon.services;

import by.speedteam.speedwagon.models.ERole;
import by.speedteam.speedwagon.models.Role;
import by.speedteam.speedwagon.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Optional<Role> getRoleById(long id) {
        return roleRepository.findById(id);
    }

    public Optional<Role> findRoleByName(ERole name) {
        return roleRepository.findByName(name);
    }

    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    public Role updateRole(long id, Role role) {
        Optional<Role> roleData = roleRepository.findById(id);
        if (roleData.isPresent()) {
            Role existingRole = roleData.get();
            existingRole.setName(role.getName());
            return roleRepository.save(existingRole);
        } else {
            throw new RuntimeException("Role with id = " + id + " not found");
        }
    }

    public void deleteRole(long id) {
        roleRepository.deleteById(id);
    }
}
