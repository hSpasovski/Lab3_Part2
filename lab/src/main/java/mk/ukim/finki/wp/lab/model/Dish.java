package mk.ukim.finki.wp.lab.model;

import jakarta.persistence.*;
import lombok.*;
import mk.ukim.finki.wp.lab.model.enums.Cuisine;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Dish {

    @Id
    @GeneratedValue
    private Long id;

    private String dishId;
    private String name;
    @Enumerated(EnumType.STRING)
    private Cuisine cuisine;
    private int preparationTime;

    @ManyToMany
    private Set<Chef> chefs;

    public Dish(String dishId, String name, Cuisine cuisine, int preparationTime) {
        this.dishId = dishId;
        this.name = name;
        this.cuisine = cuisine;
        this.preparationTime = preparationTime;
    }
}

