package org.learning.springrecipes.controller;

import org.learning.springrecipes.model.Recipe;
import org.learning.springrecipes.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

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

}
