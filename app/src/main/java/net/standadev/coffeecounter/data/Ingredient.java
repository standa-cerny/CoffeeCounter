package net.standadev.coffeecounter.data;

/**
 * Created by Standa on 05.11.2017.
 */

public class Ingredient extends BaseId{

    private IngredientType ingredientType;
    private String description;

    public Ingredient(long id, String name, IngredientType ingredientType) {
        super(id, name);
        this.ingredientType = ingredientType;
    }

    public IngredientType getIngredientType() {
        return ingredientType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
