package mk.ukim.finki.wp.lab.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.util.*;

@Entity
public class Chef {
    @Getter
    @Id
    @GeneratedValue
    private Long id;

    @Getter @Setter
    private String firstName;

    @Getter @Setter
    private String lastName;

    @Getter @Setter
    private String bio;

    // setter за dishes (по потреба)
    // getter за dishes
    // Сега ManyToMany
    @Setter
    @Getter
    @ManyToMany(mappedBy = "chefs")

    private Set<Dish> dishes = new HashSet<>();

    public Chef() {}

    public Chef(Long id, String firstName, String lastName, String bio) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
        this.dishes = new HashSet<>();
    }

    // метод за додавање јадење
    public void addDish(Dish dish) {
        dishes.add(dish);
        dish.getChefs().add(this);
    }

    // метод за отстранување јадење
    public void removeDish(Dish dish) {
        dishes.remove(dish);
        dish.getChefs().remove(this);
    }

}
