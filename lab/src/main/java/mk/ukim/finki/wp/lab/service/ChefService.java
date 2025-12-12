package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Chef;
import org.springframework.stereotype.Service;

import java.util.List;
public interface ChefService {
    List<Chef> listChefs();
    Chef findById(Long id);
    Chef addDishToChef(Long chefId, String dishId);
    Chef save(Chef chef);
    Chef removeDishFromChef(Long chefId, String dishId);
    void removeDishFromAllChefsByDishId(String dishId);
    Chef create(String firstName, String lastName, String bio);
    Chef update(Long id, String firstName, String lastName, String bio);
    void delete(Long id);

}
