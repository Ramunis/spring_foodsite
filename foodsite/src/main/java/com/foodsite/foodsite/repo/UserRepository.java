package com.foodsite.foodsite.repo;

import com.foodsite.foodsite.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query(value="SELECT id FROM usr WHERE username=:name", nativeQuery = true)
    public Integer getidByName(String name);
}
