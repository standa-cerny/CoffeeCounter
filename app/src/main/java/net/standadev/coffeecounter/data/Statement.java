package net.standadev.coffeecounter.data;

import net.standadev.coffeecounter.data.Ingredient;

import java.util.ArrayList;

/**
 * Created by Standa on 29.11.2017.
 */

public class Statement {
    private IngredientCounter ingredientCounter;
    private ArrayList<StatementItem> items;

    private boolean isClosed;

    public Statement(IngredientCounter ingredientCounter, boolean isClosed) {
        this.ingredientCounter = ingredientCounter;
        items = new ArrayList<StatementItem>();
        this.isClosed = isClosed;
    }

    public IngredientCounter getIngredientCounter() {
        return ingredientCounter;
    }

    public ArrayList<StatementItem> getItems() {
        return items;
    }

    public void addItem(StatementItem item){
        items.add(item);
    }

    public boolean isClosed() {
        return isClosed;
    }
}
