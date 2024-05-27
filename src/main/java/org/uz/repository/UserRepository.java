package org.uz.repository;


import org.springframework.stereotype.Repository;
import org.uz.model.User;

@Repository
public interface UserRepository {
    public User findUserByUserName(String username);

    public User save(User user);

}
