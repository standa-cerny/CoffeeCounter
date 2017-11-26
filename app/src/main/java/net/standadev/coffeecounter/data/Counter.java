package net.standadev.coffeecounter.data;

import android.content.Context;

import net.standadev.coffeecounter.data.db.CounterDataProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Standa on 05.11.2017.
 */

public class Counter {

    final static public String USER_ID = "USER_ID";
    final static public String INGREDIENT_ID = "INGREDIENT_ID";

    static private Counter instance;
    private ArrayList<IngredientCounter> ingredientCounters;
    private ArrayList<UserCounter> userCounters;
    private Map<Long, UserCounter> mapIdUserCounters;
    private Map<Long, IngredientCounter> mapIdIngredientCounters;

    private CounterDataProvider dataProvider;

    protected Counter(Context context) {
        ingredientCounters = new ArrayList<IngredientCounter>();
        userCounters = new ArrayList<UserCounter>();

        mapIdUserCounters = new HashMap<Long, UserCounter>();
        mapIdIngredientCounters = new HashMap<Long, IngredientCounter>();

        dataProvider = new CounterDataProvider(context);

    }

    static public Counter getInstance(Context context) {
        if (instance == null) {
            instance = new Counter(context);
            instance.reload();
        }

        return instance;
    }

    static public Counter getInstance() {
        return instance;
    }


    public void reload() {
        ingredientCounters.clear();
        userCounters.clear();

        loadTestData();
    }

    public void saveUser(User user) {
        if (user.getId() > 0) {
            dataProvider.update(user);

        } else {
            dataProvider.insert(user);
            loadUser(user);
        }

    }


    public void loadUser(User user) {
        ArrayList<IngredientCounter> uic;
        uic = new ArrayList<IngredientCounter>();

        for (IngredientCounter ic : ingredientCounters) {
            uic.add(new IngredientCounter(ic.getIngredient()));
        }
        UserCounter uc = new UserCounter(user, uic);
        userCounters.add(uc);
        mapIdUserCounters.put(user.getId(), uc);
    }

    public void saveIngredient(Ingredient ingredient) {
        // Save ingredient type
        IngredientType ingredientType = ingredient.getIngredientType();
        if (ingredientType.getId() > 0) {
            dataProvider.update(ingredientType);
        } else {
            dataProvider.insert(ingredient.getIngredientType());
        }

        // Save ingredient
        if (ingredient.getId() > 0) {
            dataProvider.update(ingredient);
        } else {
            dataProvider.insert(ingredient);
            reload();
        }

    }

    public void saveIngredientOrder(IngredientOrder order) {
        dataProvider.insert(order);
        loadIngredientOrder(order);
    }

    public void loadIngredientOrder(IngredientOrder order) {
        UserCounter uc = getUserCounterFromId(order.getUserId());
        IngredientCounter ic = getIngredientCounterFromId(order.getIngredientId());

        ic.addQuantity(order.getQuantity());
        addQuantity(uc.getIngredientCounters(), ic.getIngredient(), order.getQuantity());
    }

    public void loadUserDebt(IngredientUser ingredientUser) {
        UserCounter uc = getUserCounterFromId(ingredientUser.getUserId());
        uc.addDebt(ingredientUser.getPrice());
    }


    public void closeStatement(Ingredient ingredient) {
        // Get counter instance
        Counter counter = Counter.getInstance();

        // Clear total counter
        //ingredientCounters.get(ingredient.getCounterIndex()).clear();


        // Save summarized user orders of ingredients
        int index = ingredient.getCounterIndex();
        IngredientCounter totalCounter = ingredientCounters.get(ingredient.getCounterIndex());
        for (UserCounter uc : userCounters) {
            IngredientCounter userCounter = uc.getIngredientCounters().get(index);
            IngredientUser iu = new IngredientUser(totalCounter.getIngredient().getId(), uc.getUser().getId());

            float userQuantity = userCounter.getQuantity();
            iu.setQuantity( userQuantity );
            iu.setPrice( totalCounter.getUnitPrice() * userQuantity );

            dataProvider.insert(iu);
        }

        // Close current ingredient
        ingredient.setClosed(true);
        dataProvider.update(ingredient);

        // Prepare new ingredient
        // All data are reloaded since the new ingredient is added
        // No additional reload is needed
        Ingredient newIngredient = new Ingredient(0, "", ingredient.getIngredientType());
        counter.saveIngredient(newIngredient);
    }

    public void clearDebt(UserCounter userCounter){
        userCounter.clearDebt();
        dataProvider.clearDebt(userCounter.getUser());

    }

    public ArrayList<UserCounter> getListOfUserCounter() {
        return userCounters;
    }

    public ArrayList<IngredientCounter> getListOfIngredientCounter() {
        return ingredientCounters;
    }

    public UserCounter getUserCounterFromId(long id) {
        return mapIdUserCounters.get(id);
    }

    public IngredientCounter getIngredientCounterFromId(long id) {
        return mapIdIngredientCounters.get(id);
    }


    private void loadTestData() {
        if (0 == 1) {
            Ingredient ic = new Ingredient(1, "Brasilia Santos", new IngredientType(1, "coffee"));
            loadIngredient(ic);

            Ingredient im = new Ingredient(2, "Lidl milk", new IngredientType(2, "milk"));
            loadIngredient(im);
        }

        // Load ingredients from DB
        for (Ingredient ingredient : dataProvider.getListOfIngredients()) {
            loadIngredient(ingredient);
        }

        // Load users from DB
        for (User user : dataProvider.getListOfUsers()) {
            loadUser(user);
        }

        // Load ingredient orders from DB
        for (IngredientCounter ic : getListOfIngredientCounter()) {
            for (IngredientOrder io : dataProvider.getListOfIngredientOrders(ic.getIngredient())) {
                loadIngredientOrder(io);
            }
        }

        for (UserCounter uc : getListOfUserCounter()){
            for (IngredientUser iu : dataProvider.getListOfNotCleared(uc.getUser())){
                loadUserDebt(iu);
            }
        }

        if (0 == 1) {
            User us = new User(1, "Standa");
            loadUser(us);

            User uv = new User(2, "Vlaďa");
            loadUser(uv);

            User ur = new User(3, "Robert");
            loadUser(ur);

            User um = new User(4, "Michal");
            loadUser(um);

            //saveIngredientOrder(us, ic, 2.0f);
            //saveIngredientOrder(us, im, 1.0f);
            //saveIngredientOrder(uv, im, 1.0f);
        }
    }

    private void loadIngredient(Ingredient ingredient) {
        // Save index to ingredient counters
        ingredient.setCounterIndex(ingredientCounters.size());

        // Add ingredient to counters
        IngredientCounter ic = new IngredientCounter(ingredient, 0);
        ingredientCounters.add(ic);
        mapIdIngredientCounters.put(ingredient.getId(), ic);
    }

    private void addQuantity(ArrayList<IngredientCounter> ingredientCounters, Ingredient ingredient, float diff) {
        ingredientCounters.get(ingredient.getCounterIndex()).addQuantity(diff);
    }
}
