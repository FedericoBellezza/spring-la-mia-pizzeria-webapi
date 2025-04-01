package org.lessons.java.spring_la_mia_pizzeria_crud.controller;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.SpecialOffer;
import org.lessons.java.spring_la_mia_pizzeria_crud.repository.SpecialOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/special-offers")
public class SpecialOfferController {

    // repository
    @Autowired
    private SpecialOfferRepository repository;

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("specialOffer") SpecialOffer newOffer, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "special-offers/create";
        } else {
            repository.save(newOffer);
            return "redirect:/pizzas";
        }
    }

    @PostMapping("/delete/{offerId}")
    public String delete(@PathVariable Integer offerId) {
        repository.deleteById(offerId);
        return "redirect:/pizzas";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        SpecialOffer specialOffer = repository.findById(id).get();
        model.addAttribute(specialOffer);

        return "special-offers/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("specialOffer") SpecialOffer newOffer, BindingResult bindingResult,
            Model model, @PathVariable Integer id) {
        if (bindingResult.hasErrors()) {
            return "special-offers/edit";
        } else {
            repository.save(newOffer);
            return "redirect:/pizzas";
        }
    }

}
