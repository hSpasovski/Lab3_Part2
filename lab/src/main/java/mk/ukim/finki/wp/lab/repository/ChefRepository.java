package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.model.Chef;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ChefRepository extends JpaRepository<Chef, Long> {
}
