package com.owerp.fmsprovider.system.service;

import com.owerp.fmsprovider.system.model.data.User;
import com.owerp.fmsprovider.system.model.dto.UserDTO;
import com.owerp.fmsprovider.system.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repo;
    private final BCryptPasswordEncoder encoder;
    @Autowired
    private ModelMapper modelMapper;

    public UserService(UserRepository repo, BCryptPasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
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

    public UserDTO addUser(UserDTO userDTO){
        User user = new User();
        this.repo.save(user);
        return this.modelMapper.map(user, UserDTO.class);
    }

    @PostConstruct
    public void addDefaultAdminUser() {
        final String username = "admin@gmail.com";
        Optional<User> user = this.repo.findByUsername(username);
        if(user.isEmpty()){
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
}
