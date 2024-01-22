package org.learning.springrecipes.repository;

import org.learning.springrecipes.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

}
