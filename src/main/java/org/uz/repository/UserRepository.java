package org.uz.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.uz.model.User;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
     User findByEmail(String email);

}
