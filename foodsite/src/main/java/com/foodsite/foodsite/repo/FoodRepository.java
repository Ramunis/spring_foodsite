package com.foodsite.foodsite.repo;

import com.foodsite.foodsite.models.Client;
import com.foodsite.foodsite.models.Food;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FoodRepository extends CrudRepository<Food, Long> {

    @Query(value="SELECT * FROM food WHERE theme_id=:number", nativeQuery = true)
    public List<Food> getFoodByTheme(Integer number);

    @Query(value="SELECT * FROM food ORDER BY id DESC", nativeQuery = true)
    public List<Food> getBySortLast();

    @Query(value="SELECT * FROM food ORDER BY id ASC", nativeQuery = true)
    public List<Food> getBySortOld();

    @Query(value="SELECT * FROM food ORDER BY RAND() LIMIT 100", nativeQuery = true)
    public List<Food> getByTodayRec();

    @Query(value="SELECT * FROM food WHERE client_id=:number", nativeQuery = true)
    public List<Food> getFoodByChief(Long number);

    @Query(value="SELECT * FROM food WHERE title LIKE ':query%'", nativeQuery = true)
    public List<Food> getFoodByName(String query);
}

