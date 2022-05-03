package com.foodsite.foodsite.repo;

import com.foodsite.foodsite.models.Like;
import com.foodsite.foodsite.models.Theme;
import org.springframework.data.repository.CrudRepository;

public interface LikeRepository extends CrudRepository<Like, Long> {
}
