package org.lessons.java.spring_la_mia_pizzeria_crud.controller;

// imports
import java.util.List;
import org.lessons.java.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.spring_la_mia_pizzeria_crud.model.SpecialOffer;
import org.lessons.java.spring_la_mia_pizzeria_crud.repository.IngredientRepository;
import org.lessons.java.spring_la_mia_pizzeria_crud.repository.SpecialOfferRepository;
import org.lessons.java.spring_la_mia_pizzeria_crud.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {

    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private SpecialOfferRepository specialOfferRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @GetMapping
    public String index(Model model) {
        List<Pizza> pizzas = pizzaService.findAll();
        model.addAttribute("pizzas", pizzas);
        return "pizzas/index";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable Integer id) {
        Pizza pizza = pizzaService.getById(id);
        model.addAttribute("pizza", pizza);
        return "pizzas/show";
    }

    @GetMapping("/search-by-name")
    public String searchByName(@RequestParam(name = "name") String name, Model model) {
        List<Pizza> pizzas = pizzaService.findByName(name);
        model.addAttribute("pizzas", pizzas);
        return "pizzas/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());
        model.addAttribute("ingredients", ingredientRepository.findAll());
        return "pizzas/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredients", ingredientRepository.findAll());
            return "pizzas/create";
        } else {
            pizzaService.create(formPizza);
            return "redirect:/pizzas";
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Integer id) {
        model.addAttribute("pizza", pizzaService.getById(id));
        model.addAttribute("ingredients", ingredientRepository.findAll());

        return "pizzas/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(
            @Valid @ModelAttribute("pizza") Pizza formPizza,
            BindingResult bindingResult,
            Model model,
            @PathVariable Integer id) {

        if (bindingResult.hasErrors()) {
            return "/pizzas/edit";
        } else {
            pizzaService.update(formPizza);
            return "redirect:/pizzas/{id}";
        }
    }

    // pizza delete
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        Pizza pizzaToDelete = pizzaService.getById(id);
        for (SpecialOffer specialOffer : pizzaToDelete.getSpecialOffers()) {
            specialOfferRepository.delete(specialOffer);
        }
        pizzaService.delete(pizzaToDelete);
        return "redirect:/pizzas";
    }

    @GetMapping("/{id}/special-offers/create")
    public String createOffer(@PathVariable Integer id, Model model) {
        SpecialOffer specialOffer = new SpecialOffer();
        specialOffer.setPizza(pizzaService.getById(id));
        model.addAttribute("specialOffer", specialOffer);
        model.addAttribute("pizza", pizzaService.getById(id));

        return "special-offers/create";
    }

}
