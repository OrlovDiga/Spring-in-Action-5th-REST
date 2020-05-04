package tacos.web.api;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import tacos.Ingredient;

/**
 * @author Orlov Diga
 */
public class IngredientResourceAssembler extends ResourceAssemblerSupport<Ingredient, IngredientResource> {

    public IngredientResourceAssembler() {
        super(IngredientController.class, IngredientResource.class);
    }

    @Override
    public IngredientResource toResource(Ingredient entity) {
        return createResourceWithId(entity.getId(), entity);
    }

    @Override
    protected IngredientResource instantiateResource(
            Ingredient ingredient) {
        return new IngredientResource(ingredient);
    }
}
