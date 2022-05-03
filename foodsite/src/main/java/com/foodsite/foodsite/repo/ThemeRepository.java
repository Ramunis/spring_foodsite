package com.foodsite.foodsite.repo;

import com.foodsite.foodsite.models.Client;
import com.foodsite.foodsite.models.Theme;
import org.springframework.data.repository.CrudRepository;

public interface ThemeRepository extends CrudRepository<Theme, Long> {
}

