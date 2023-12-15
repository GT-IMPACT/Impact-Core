package com.impact.util.multis;

import com.impact.addon.gt.api.multis.IMachineRecipe;
import com.impact.models.RecipeMachineModel;
import com.impact.models.RecipeOverclockItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OverclockCalculateTest {

    private static List<RecipeOverclockItem> ITEMS;

    static {
        ITEMS = RecipeOverclockItem.Companion.readParams();
    }

    IMachineRecipe model = new RecipeMachineModel();

    @BeforeEach
    void setUp() {
        model.setMaxProgressTime(0);
        model.setEUt(0);
    }

    @Test
    void calculateOverclockedNessMultiTest() {

        assertFalse(ITEMS.isEmpty());

        for (RecipeOverclockItem item : ITEMS) {
            int eU = item.getRecipeVoltage();
            int duration = item.getRecipeDuration();
            long maxVoltage = (long) item.getHatchVoltage() * item.getHatchAmperes();

            OverclockCalculate.calculateOverclockedNessMulti(eU, duration, 1, maxVoltage, model);

            assertEquals(model.getEUt(), item.getResultVoltage());
            assertEquals(model.getMaxProgressTime(), item.getResultProgress());
        }
    }
}
