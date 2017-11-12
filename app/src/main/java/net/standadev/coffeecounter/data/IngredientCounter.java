package net.standadev.coffeecounter.data;


/**
 * Created by Standa on 05.11.2017.
 */

public class IngredientCounter {
    Ingredient ingredient;
    float quantity;
    float price = 552.0f;

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

    public void clear(){
        setPrice(0.0f);
        setQuantity(0.0f);
    }

    public float getQuantity() {
        return quantity;
    }
    void setQuantity(float quantity) {
        this.quantity = quantity;
    }
    void addQuantity(float diff){
        this.quantity += diff;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getUnitPrice(){
        if (getQuantity() > 0.0f){
            return getPrice() / getQuantity();
        }
        return 0.0f;
    }
}
