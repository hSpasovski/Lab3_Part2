package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Chef;
import mk.ukim.finki.wp.lab.service.ChefService;
import mk.ukim.finki.wp.lab.service.DishService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/chefs")
public class ChefController {

    private final ChefService chefService;
    private final DishService dishService;

    public ChefController(ChefService chefService, DishService dishService) {
        this.chefService = chefService;
        this.dishService = dishService;
    }

    // Листање на сите готвачи
    @GetMapping
    public String listChefs(Model model) {
        model.addAttribute("chefs", chefService.listChefs());

        return "listChefs";
    }

    // Приказ на детали за еден готвач
    @GetMapping("/details")
    public String getChefDetails(@RequestParam Long chefId, Model model) {
        Chef chef = chefService.findById(chefId);
        model.addAttribute("chef", chef);
        model.addAttribute("dishes", dishService.listDishes());
        return "chefDetails";
    }

    // Додавање јадење кај готвач
    @PostMapping("/add-dish")
    public String addDishToChef(@RequestParam Long chefId, @RequestParam String dishId) {
        chefService.addDishToChef(chefId, dishId);
        return "redirect:/chefs/details?chefId=" + chefId;
    }

    // Бришење јадење од готвач
    @PostMapping("/delete-dish")
    public String deleteDishFromChef(@RequestParam Long chefId, @RequestParam String dishId) {
        chefService.removeDishFromChef(chefId, dishId); // мора да се додаде овој метод во ChefService
        return "redirect:/chefs/details?chefId=" + chefId;
    }
    @GetMapping("/form")
    public String getChefForm(Model model) {
        model.addAttribute("chef", new Chef(0L, "", "", ""));
        return "chef-form";
    }

    @PostMapping("/add")
    public String addChef(@RequestParam String firstName,
                          @RequestParam String lastName,
                          @RequestParam String bio) {
        chefService.create(firstName, lastName, bio);
        return "redirect:/chefs";
    }
    @GetMapping("/form/{id}")
    public String editChefForm(@PathVariable Long id, Model model) {
        Chef chef = chefService.findById(id);
        if (chef == null) {
            return "redirect:/chefs?error=ChefNotFound";
        }
        model.addAttribute("chef", chef);
        return "chef-form";
    }

    @PostMapping("/edit/{id}")
    public String editChef(@PathVariable Long id,
                           @RequestParam String firstName,
                           @RequestParam String lastName,
                           @RequestParam String bio) {
        chefService.update(id, firstName, lastName, bio);
        return "redirect:/chefs";
    }
    @PostMapping("/delete/{id}")
    public String deleteChef(@PathVariable Long id) {
        chefService.delete(id);
        return "redirect:/chefs";
    }

}
