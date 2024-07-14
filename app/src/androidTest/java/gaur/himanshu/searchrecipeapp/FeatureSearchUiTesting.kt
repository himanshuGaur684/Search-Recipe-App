package gaur.himanshu.searchrecipeapp

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import gaur.himanshu.search.data.di.SearchDataModule
import gaur.himanshu.search.screens.recipe_list.RecipeListScreenTestTag
import gaur.himanshu.searchrecipeapp.di.DataBaseModule
import gaur.himanshu.searchrecipeapp.utils.getRecipeResponse
import org.junit.Before
import org.junit.Rule
import org.junit.Test

// Second Approach of writing UI Testing

@HiltAndroidTest
@UninstallModules(SearchDataModule::class, DataBaseModule::class)
class FeatureSearchUiTesting {

    @get:Rule(order = 1)
    val hiltRule = HiltAndroidRule(this)


    @get:Rule(order = 2)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }


    @Test
    fun test_recipeListSuccess() {
        with(composeRule) {
            onNodeWithTag(RecipeListScreenTestTag.SEARCH).performClick()
            onNodeWithTag(RecipeListScreenTestTag.SEARCH).performTextInput("chicken")

            onNodeWithTag(RecipeListScreenTestTag.LAZY_COL).onChildAt(0)
                .assert(hasTestTag(getRecipeResponse().first().strMeal.plus(0)))

        }
    }

    @Test
    fun test_recipeListSucccess_recipeDetailsSuccess(){
        with(composeRule){
            onNodeWithTag(RecipeListScreenTestTag.SEARCH).performClick()
            onNodeWithTag(RecipeListScreenTestTag.SEARCH).performTextInput("chicken")
            onNodeWithTag(RecipeListScreenTestTag.LAZY_COL).onChildAt(0)
                .assert(hasTestTag(getRecipeResponse().first().strMeal.plus(0)))
                .performClick()

            onNodeWithText(getRecipeResponse().first().strMeal).assertIsDisplayed()
        }
    }





}