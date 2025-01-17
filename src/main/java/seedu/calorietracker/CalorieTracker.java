package seedu.calorietracker;

import seedu.exceptions.InvalidSyntaxException;
import seedu.parser.DateFormatter;
import seedu.storage.Storage;
import seedu.ui.Ui;

import java.util.Date;
import java.util.HashMap;

import static seedu.commands.caloriecommands.AddCalorieCommand.CALORIES_NOT_GIVEN;

//@@author calebcjl
/**
 * Represents a calorie tracker.
 */
public class CalorieTracker {
    private HashMap<Date, FoodList> dailyFoodConsumption;
    private FoodDictionary foodDictionary;

    public CalorieTracker() {
        dailyFoodConsumption = new HashMap<>();
        foodDictionary = new FoodDictionary();
    }

    public CalorieTracker(Storage storage, FoodDictionary foodDictionary) {
        dailyFoodConsumption = storage.readCalorieTrackerFile();
        this.foodDictionary = foodDictionary;
    }

    public FoodList getFoodList(Date date) {
        if (!dailyFoodConsumption.containsKey(date)) {
            FoodList foodList = new FoodList();
            dailyFoodConsumption.put(date, foodList);
        }
        return dailyFoodConsumption.get(date);
    }

    public HashMap<Date, FoodList> getDailyFoodConsumption() {
        return dailyFoodConsumption;
    }

    public String addCalories(Date date, String foodName, int foodCalories) throws InvalidSyntaxException {
        FoodList foodList = getFoodList(date);
        Food foodToAdd;
        if (foodCalories == CALORIES_NOT_GIVEN) {
            if (foodDictionary.contains(foodName)) {
                foodToAdd = new Food(foodName, foodDictionary.getFoodCalories().get(foodName));
            } else {
                throw new InvalidSyntaxException("food calories");
            }
        } else {
            foodToAdd = new Food(foodName, foodCalories);
            foodDictionary.addFood(foodName, foodCalories);
        }
        foodList.addFood(foodToAdd);

        return "Added " + foodName + " (" + foodToAdd.getCalories() + "kcal) to "
                + DateFormatter.dateToString(date) + "." + System.lineSeparator() + Ui.line();
    }
}
