package org.learning.springrecipes.controller;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import org.learning.springrecipes.model.Recipe;
import org.learning.springrecipes.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        @GetMapping("/create")
        public String create (Model model){
            Recipe recipe = new Recipe();
            //passo tramite model attributo di tipo recipe vuoto
            model.addAttribute("recipe", recipe);
            return "recipes/create";
        }

        @PostMapping("/create")
        public String store (@Valid @ModelAttribute("recipe") Recipe formRecipe, BindingResult bindingResult, Model
        model){
            if (bindingResult.hasErrors()) {
                return "recipes/create";
        } else{
            //se sono validi li salvo su DB
            Recipe savedRecipe = recipeRepository.save(formRecipe);

            //faccio redirect a pagina di dettaglio della pizza appena creata
            return "redirect:/recipes/show/" + savedRecipe.getId();

            //se non sono validi ricarico la pagina col form e i messaggi di errore

        }
    }
    // metodo che restituisce la pagina di modifica del Book
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        // recupero il recipe da database
        Optional<Recipe> result = recipeRepository.findById(id);
        // verifico se il recipe è presente
        if (result.isPresent()) {
            // lo passo come attributo del Model
            model.addAttribute("recipe", result.get());
            // ritorno il template
            return "recipes/edit";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe with id " + id + " not found");
        }
    }

    // metodo che riceve il submit del form di edit
    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id, @Valid @ModelAttribute("recipe") Recipe formRecipe,
                         BindingResult bindingResult) {
        Optional<Recipe> result = recipeRepository.findById(id);
        if (result.isPresent()) {
            Recipe recipeToEdit = result.get();
            // valido i dati del recipe
            if (bindingResult.hasErrors()) {
                // se ci sono errori di validazione
                return "recipes/edit";
            }

            // se sono validi salvo il recipe su db
           Recipe savedRecipe = recipeRepository.save(formRecipe);
            // faccio la redirect alla pagina di dettaglio del recipe
            return "redirect:/recipes/show/" + id;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe with id " + id + " not found");
        }
    }

    // metodo che cancella un Recipe preso per id
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        // verifico se il Recipe è presente su db
        Optional<Recipe> result = recipeRepository.findById(id);
        if (result.isPresent()) {
            // se c'è lo cancello
            recipeRepository.deleteById(id);
            // mando un messaggio di successo con la redirect
            redirectAttributes.addFlashAttribute("redirectMessage",
                    "Recipe " + result.get().getTitle() + " deleted!");
            return "redirect:/recipes";
        } else {
            // se non c'è sollevo un'eccezione
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe with " + id + " not found");
        }
    }
}
