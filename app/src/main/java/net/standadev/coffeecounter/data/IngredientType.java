package net.standadev.coffeecounter.data;

/**
 * Created by Standa on 05.11.2017.
 */

public class IngredientType extends BaseId{

    private IngredientType ingredientType;

    public IngredientType(long id, String name) {
        super(id, name);
    }

    public IngredientType getIngredientType() {
        return ingredientType;
    }

    public void setIngredientType(IngredientType ingredientType) {
        this.ingredientType = ingredientType;
    }
}
