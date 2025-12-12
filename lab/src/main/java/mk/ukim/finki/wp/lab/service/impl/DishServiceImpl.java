package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Chef;
import mk.ukim.finki.wp.lab.model.Dish;
import mk.ukim.finki.wp.lab.model.enums.Cuisine;
import mk.ukim.finki.wp.lab.repository.DishRepository;
import mk.ukim.finki.wp.lab.service.ChefService;
import mk.ukim.finki.wp.lab.service.DishService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final ChefService chefService;

    public DishServiceImpl(DishRepository dishRepository, ChefService chefService) {
        this.dishRepository = dishRepository;
        this.chefService = chefService;
    }

    @Override
    public List<Dish> listDishes() {
        return dishRepository.findAll();
    }

    @Override
    public Dish findByDishId(String dishId) {
        return dishRepository.findByDishId(dishId).orElse(null);
    }

    @Override
    public Dish findById(Long id) {
        return dishRepository.findById(id).orElse(null);
    }

    @Override
    public Dish create(String dishId, String name, Cuisine cuisine, int preparationTime, List<Long> chefIds) {
        Set<Chef> chefs = chefIds.stream()
                .map(chefService::findById)
                .collect(Collectors.toSet());
        Dish dish = new Dish();
        dish.setDishId(dishId);
        dish.setName(name);
        dish.setCuisine(cuisine);
        dish.setPreparationTime(preparationTime);
        dish.setChefs(chefs);
        return dishRepository.save(dish);
    }

    public Dish update(Long id, String dishId, String name, Cuisine cuisine, int preparationTime, List<Long> chefIds) {
        Dish dish = dishRepository.findById(id).orElseThrow();
        dish.setDishId(dishId);
        dish.setName(name);
        dish.setCuisine(cuisine);
        dish.setPreparationTime(preparationTime);
        Set<Chef> chefs = chefIds.stream()
                .map(chefService::findById)
                .collect(Collectors.toSet());
        dish.setChefs(chefs);
        return dishRepository.save(dish);
    }



    @Override
    public void delete(Long id) {
        dishRepository.deleteById(id);
    }
}
