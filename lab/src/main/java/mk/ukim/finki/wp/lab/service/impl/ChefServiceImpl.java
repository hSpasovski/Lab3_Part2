package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Chef;
import mk.ukim.finki.wp.lab.model.Dish;
import mk.ukim.finki.wp.lab.repository.ChefRepository;
import mk.ukim.finki.wp.lab.repository.DishRepository;
import mk.ukim.finki.wp.lab.service.ChefService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ChefServiceImpl implements ChefService {

    private final ChefRepository chefRepository;
    private final DishRepository dishRepository;

    public ChefServiceImpl(ChefRepository chefRepository, DishRepository dishRepository) {
        this.chefRepository = chefRepository;
        this.dishRepository = dishRepository;
    }

    @Override
    public List<Chef> listChefs() {
        return chefRepository.findAll();
    }

    @Override
    public Chef findById(Long id) {
        return chefRepository.findById(id).orElse(null);
    }

    @Override
    public Chef addDishToChef(Long chefId, String dishId) {
        Chef chef = findById(chefId);
        Dish dish = dishRepository.findByDishId(dishId).orElse(null);
        if (chef != null && dish != null) {
            Set<Chef> chefs = dish.getChefs() != null ? dish.getChefs() : new HashSet<>();
            chefs.add(chef);
            dish.setChefs(chefs);
            dishRepository.save(dish);
            return chef;
        }
        return null;
    }


    @Override
    public Chef save(Chef chef) {
        return null;
    }

    @Override
    public Chef removeDishFromChef(Long chefId, String dishId) {
        Chef chef = findById(chefId);
        Dish dish = dishRepository.findByDishId(dishId).orElse(null);
        if (chef != null && dish != null && dish.getChefs() != null) {
            // Отстрани chef од dish.chefs
            dish.getChefs().removeIf(c -> c.getId().equals(chefId)); // <-- getId(), не getID()

            // Отстрани dish од chef.dishes
            if (chef.getDishes() != null) {
                chef.getDishes().remove(dish);
            }

            dishRepository.save(dish);
            return chef;
        }
        return null;
    }



    @Override
    public void removeDishFromAllChefsByDishId(String dishId) {
        Dish dish = dishRepository.findByDishId(dishId).orElse(null);
        if (dish != null) {
            dish.getChefs().clear();
            dishRepository.save(dish);
        }
    }


    @Override
    public Chef create(String firstName, String lastName, String bio) {
        Chef chef = new Chef();
        chef.setFirstName(firstName);
        chef.setLastName(lastName);
        chef.setBio(bio);
        return chefRepository.save(chef);
    }

    @Override
    public Chef update(Long id, String firstName, String lastName, String bio) {
        Chef chef = findById(id);
        if (chef != null) {
            chef.setFirstName(firstName);
            chef.setLastName(lastName);
            chef.setBio(bio);
            return chefRepository.save(chef);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        chefRepository.deleteById(id);
    }
}
