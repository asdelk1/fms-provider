package com.owerp.fmsprovider.system.service;

import com.owerp.fmsprovider.system.advice.EntityNotFoundException;
import com.owerp.fmsprovider.system.model.data.User;
import com.owerp.fmsprovider.system.model.data.UserGroup;
import com.owerp.fmsprovider.system.model.dto.UserDTO;
import com.owerp.fmsprovider.system.model.dto.UserGroupDTO;
import com.owerp.fmsprovider.system.repository.UserGroupRepository;
import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserGroupService {

    private final UserGroupRepository repo;
    private final EntityModelMapper modelMapper;

    public UserGroupService(UserGroupRepository repo, EntityModelMapper modelMapper) {
        this.repo = repo;
        this.modelMapper = modelMapper;
    }

    public List<UserGroup> getUserGroups(){
        return this.repo.findAll();
    }

    public UserGroup getUserGroup(Long id){
        Optional<UserGroup> ug = this.repo.findById(id);
        return ug.orElseThrow(() ->new EntityNotFoundException("User Group", id));
    }

    public UserGroup saveUserGroup(UserGroupDTO dto){

        UserGroup entity = this.modelMapper.getEntity(dto, UserGroup.class);
        return this.repo.save(entity);
    }

    public UserGroup addUsers(Long groupId, Set<UserDTO> users){

        Set<User> userEntities = users.stream().map(u -> this.modelMapper.getEntity(u, User.class)).collect(Collectors.toSet());
        UserGroup group = this.getUserGroup(groupId);
        group.getUsers().addAll(userEntities);
        return this.repo.save(group);
    }

    public UserGroup deleteUsers(Long groupId, Set<UserDTO> users){
        Set<User> userEntities = users.stream().map(u -> this.modelMapper.getEntity(u, User.class)).collect(Collectors.toSet());
        UserGroup group = this.getUserGroup(groupId);
        group.getUsers().removeAll(userEntities);
        return this.repo.save(group);
    }

    public UserGroup addPermissions(Long groupId, Set<String> permissions){
        UserGroup userGroup = this.getUserGroup(groupId);
        userGroup.getGrantedPermissions().addAll(permissions);
        return this.repo.save(userGroup);
    }

    public UserGroup removePermissions(Long groupId, Set<String> permissions){
        UserGroup userGroup = this.getUserGroup(groupId);
        userGroup.getGrantedPermissions().removeAll(permissions);
        return this.repo.save(userGroup);
    }
}
