package com.foodsite.foodsite.repo;

import com.foodsite.foodsite.models.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client, Long> {

    @Query(value="SELECT * FROM client ORDER BY RAND() LIMIT 100", nativeQuery = true)
    public List<Client> getByTodayChief();

    @Query(value="SELECT * FROM client WHERE country=:id", nativeQuery = true)
    public List<Client> getFoodByRegion(String id);

}
