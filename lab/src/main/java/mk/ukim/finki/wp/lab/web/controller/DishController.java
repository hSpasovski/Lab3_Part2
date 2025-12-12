package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Dish;
import mk.ukim.finki.wp.lab.model.enums.Cuisine;
import mk.ukim.finki.wp.lab.service.ChefService;
import mk.ukim.finki.wp.lab.service.DishService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dishes")
public class DishController {

    private final DishService dishService;
    private final ChefService chefService;

    public DishController(DishService dishService, ChefService chefService) {
        this.dishService = dishService;
        this.chefService = chefService;
    }

    // LIST ALL DISHES
    @GetMapping
    public String listDishes(Model model) {
        model.addAttribute("dishes", dishService.listDishes());
        return "dishes/listDishes";
    }

    // ADD FORM
    @GetMapping("/dish-form")
    public String addDish(Model model) {
        model.addAttribute("dish", new Dish());
        model.addAttribute("chefs", chefService.listChefs());
        return "dishes/dish-form";
    }

    // EDIT FORM
    @GetMapping("/dish-form/{id}")
    public String editDish(@PathVariable Long id, Model model) {
        Dish dish = dishService.findById(id);
        model.addAttribute("dish", dish);
        model.addAttribute("chefs", chefService.listChefs());
        return "dishes/dish-form";
    }

    // CREATE DISH
    @PostMapping("/add")
    public String createDish(@ModelAttribute Dish dish,
                             @RequestParam List<Long> chefIds) { // ← List<Long>
        dishService.create(dish.getDishId(), dish.getName(), dish.getCuisine(), dish.getPreparationTime(), chefIds);
        return "redirect:/dishes";
    }

    @PostMapping("/edit/{id}")
    public String updateDish(@PathVariable Long id,
                             @ModelAttribute Dish dish,
                             @RequestParam List<Long> chefIds) { // ← List<Long>
        dishService.update(id, dish.getDishId(), dish.getName(), dish.getCuisine(), dish.getPreparationTime(), chefIds);
        return "redirect:/dishes";
    }



    // DELETE DISH
    @PostMapping("/delete/{id}")
    public String deleteDish(@PathVariable Long id) {
        dishService.delete(id);
        return "redirect:/dishes";
    }
}
