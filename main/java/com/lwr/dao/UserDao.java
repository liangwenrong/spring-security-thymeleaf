package com.lwr.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.lwr.entity.User;

public interface UserDao  extends CrudRepository<User, String>{//, JpaSpecificationExecutor<User>
    public List<User> findByName(String name);
    

}
