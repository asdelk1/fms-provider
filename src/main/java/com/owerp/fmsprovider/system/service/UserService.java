package com.owerp.fmsprovider.system.service;

import com.owerp.fmsprovider.system.advice.EntityNotFoundException;
import com.owerp.fmsprovider.system.model.data.User;
import com.owerp.fmsprovider.system.model.data.UserGroup;
import com.owerp.fmsprovider.system.model.dto.UserDTO;
import com.owerp.fmsprovider.system.repository.UserRepository;
import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repo;
    private final BCryptPasswordEncoder encoder;
    private final EntityModelMapper modelMapper;
    private final EmailService emailService;

    public UserService(UserRepository repo, BCryptPasswordEncoder encoder, EntityModelMapper modelMapper, EmailService emailService) {
        this.repo = repo;
        this.encoder = encoder;
        this.modelMapper = modelMapper;
        this.emailService = emailService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = this.repo.findByUsername(username);
        if (user.isPresent()) {
            Set<SimpleGrantedAuthority> authorities = new HashSet<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            return new org.springframework.security.core.userdetails.User(
                    user.get().getUsername(),
                    user.get().getPassword(),
                    authorities
            );
        } else {
            throw new UsernameNotFoundException(username + " is not a valid user.");
        }
    }

    public UserDTO saveUser(UserDTO dto) {
        User user = this.modelMapper.getEntity(dto, User.class);
        boolean isNewUser = dto.getId() == null;
        if (!isNewUser) {
            User existingUser = this.repo.getById(dto.getId());
            user.setPassword(existingUser.getPassword()); // no need to update the password for basic information changes. dto will always have empty password
        }
        user = this.repo.save(user);
        if(isNewUser){
            this.sendPasswordResetEmail(user);
        }
        return this.modelMapper.getDTO(user, UserDTO.class);
    }

    public List<UserDTO> getUsers() {
        List<User> users = this.repo.findAll();
        return users.stream().map(u -> this.modelMapper.getDTO(u, UserDTO.class)).collect(Collectors.toList());
    }

    public User getUser(long id) {
        User user = this.repo.getById(id);
        if (user != null) {
            return user;
        }
        throw new EntityNotFoundException("User", id);
    }

    public User setUserActiveState(UserDTO dto) {
        User user = this.getUser(dto.getId());
        user.setActive(!user.isActive());
        user = this.repo.save(user);
        return user;
    }

    public void updatePermissions(User user) {
        Set<UserGroup> userGroups = user.getGroups();
        Set<String> permissions = new HashSet<>();
        userGroups.forEach(u -> permissions.addAll(u.getGrantedPermissions()));
        user.setGrantedPermissions(permissions);
        this.repo.save(user);
    }

    public void deleteUser(long id) {
        User user = this.getUser(id);
        this.repo.delete(user);
    }

    @PostConstruct
    public void addDefaultAdminUser() {
        final String username = "admin@gmail.com";
        Optional<User> user = this.repo.findByUsername(username);
        if (user.isEmpty()) {
            User defaultAdminUser = new User();
            defaultAdminUser.setUsername(username);
            defaultAdminUser.setPassword(this.encoder.encode("admin"));
            defaultAdminUser.setEmail(username);
            defaultAdminUser.setFirstName("Ayrton");
            defaultAdminUser.setLastName("Senna");
            defaultAdminUser.setEpfNo("001");
            defaultAdminUser.setActive(true);
            this.repo.save(defaultAdminUser);
        }
    }

    /**
     * This method will email to given user with a token to reset password
     */
    public void sendPasswordResetEmail(User user) {
        this.emailService.sendEmail("");
    }
}
