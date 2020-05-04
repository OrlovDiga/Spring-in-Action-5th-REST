package tacos.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import tacos.Ingredient;

/**
 * @author Orlov Diga
 */
@CrossOrigin(origins="*")
@Repository
public interface IngredientRepo extends CrudRepository<Ingredient, String> {
}
