package tacos;

import lombok.Data;

/**
 * @author Orlov Diga
 */
@Data
public class Ingredient {

    private final String name;
    private final Type type;

    public static enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
