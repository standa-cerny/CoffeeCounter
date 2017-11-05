package net.standadev.coffeecounter.data;


/**
 * Created by Standa on 05.11.2017.
 */

public class IngredientCounter {
    Ingredient ingredient;
    float quantity;

    public IngredientCounter(Ingredient ingredient, float quantity) {
        this.ingredient = ingredient;
        this.quantity = quantity;
    }

    public IngredientCounter(Ingredient ingredient) {
        this(ingredient, 0.0f);
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public float getQuantity() {
        return quantity;
    }

}
