package com.owerp.fmsprovider.system.repository;

import com.owerp.fmsprovider.system.model.data.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {


}
