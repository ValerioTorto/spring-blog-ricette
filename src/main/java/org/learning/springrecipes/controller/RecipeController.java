package org.learning.springrecipes.controller;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.learning.springrecipes.model.Recipe;
import org.learning.springrecipes.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/recipes")
public class RecipeController {
    @Autowired
    private RecipeRepository recipeRepository;

    //metodo index che mostra la lista delle ricette
    @GetMapping
    public String index(Model model) {
        List<Recipe> recipeList = recipeRepository.findAll();
        model.addAttribute("recipeList", recipeList);
        return "recipes/list";
    }
    @GetMapping("show/{id}")
    public String show(@PathVariable Integer id, Model model) {
        Optional<Recipe> result = recipeRepository.findById(id);
        if (result.isPresent()) {
            //estraggo la recipe dall'optional
            Recipe recipe = result.get();
            //aggiungo al model l'attributo con la recipe
            model.addAttribute("recipe", recipe);
            //restituisco il template
            return "recipes/show";
        } else {
            //gestisco il caso in cui la recipe nel database non c'è
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza with id " + id + " not found");
        }
    }
}
