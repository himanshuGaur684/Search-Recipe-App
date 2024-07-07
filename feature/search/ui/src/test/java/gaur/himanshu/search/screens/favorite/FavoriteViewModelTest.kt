package gaur.himanshu.search.screens.favorite

import gaur.himanshu.search.domain.model.Recipe
import gaur.himanshu.search.domain.model.RecipeDetails
import gaur.himanshu.search.domain.use_cases.DeleteRecipeUseCase
import gaur.himanshu.search.domain.use_cases.GetAllRecipesFromLocalDbUseCase
import gaur.himanshu.search.screens.details.MainDispatcherRule
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class FavoriteViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getAllRecipesFromLocalDbUseCase: GetAllRecipesFromLocalDbUseCase = mock()
    private val deleteRecipeUseCase: DeleteRecipeUseCase = mock()

    @Before
    fun setUp() {
        `when`(getAllRecipesFromLocalDbUseCase.invoke())
            .thenReturn(
                flowOf(getRecipeResponse())
            )
    }

    @Test
    fun test_alphabeticalSort() = runTest {
        val viewModel = FavoriteViewModel(getAllRecipesFromLocalDbUseCase, deleteRecipeUseCase)

        viewModel.onEvent(FavoriteScreen.Event.AlphabeticalSort)

        assertEquals(
            getRecipeResponse().sortedBy { it.strMeal },
            viewModel.uiState.value.data
        )
    }

    @Test
    fun test_less_ingredient_sort() = runTest {
        val viewModel = FavoriteViewModel(getAllRecipesFromLocalDbUseCase, deleteRecipeUseCase)
        viewModel.onEvent(FavoriteScreen.Event.LessIngredientsSort)
        assertEquals(getRecipeResponse()
            .sortedBy { it.strInstructions.length }, viewModel.uiState.value.data
        )
    }
    @Test
    fun test_reset_sort() = runTest {
        val viewModel = FavoriteViewModel(getAllRecipesFromLocalDbUseCase, deleteRecipeUseCase)
        viewModel.onEvent(FavoriteScreen.Event.AlphabeticalSort)
        assertEquals(
            getRecipeResponse().sortedBy { it.strMeal },
            viewModel.uiState.value.data
        )
        viewModel.onEvent(FavoriteScreen.Event.ResetSort)
        assertEquals(
            getRecipeResponse() , viewModel.uiState.value.data
        )
    }


    @Test
    fun test_delete() = runTest {
        val recipeList = getRecipeResponse().toMutableList()

        `when`(deleteRecipeUseCase.invoke(recipeList.first()))
            .then {
                recipeList.remove(recipeList.first())
                flowOf(Unit)
            }

        val viewModel = FavoriteViewModel(getAllRecipesFromLocalDbUseCase, deleteRecipeUseCase)

        viewModel.onEvent(FavoriteScreen.Event.DeleteRecipe(recipeList.first()))

        assert(recipeList.size == 1)

    }

    @Test
    fun test_navigation_details() = runTest {
        val viewModel = FavoriteViewModel(getAllRecipesFromLocalDbUseCase, deleteRecipeUseCase)
        viewModel.onEvent(FavoriteScreen.Event.ShowDetails("id"))
        val list = mutableListOf<FavoriteScreen.Navigation>()
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.navigation.collectLatest {
                list.add(it)
            }
        }
        assert(list.first() is FavoriteScreen.Navigation.GoToRecipeDetailsScreen)


    }



}


private fun getRecipeResponse(): List<Recipe> {
    return listOf(
        Recipe(
            idMeal = "idMeal",
            strArea = "India",
            strCategory = "category",
            strYoutube = "strYoutube",
            strTags = "tag1,tag2",
            strMeal = "Chicken",
            strMealThumb = "strMealThumb",
            strInstructions = "12",
        ),
        Recipe(
            idMeal = "idMeal",
            strArea = "India",
            strCategory = "category",
            strYoutube = "strYoutube",
            strTags = "tag1,tag2",
            strMeal = "Chicken",
            strMealThumb = "strMealThumb",
            strInstructions = "123",
        )
    )

}

private fun getRecipeDetails(): RecipeDetails {
    return RecipeDetails(
        idMeal = "idMeal",
        strArea = "India",
        strCategory = "category",
        strYoutube = "strYoutube",
        strTags = "tag1,tag2",
        strMeal = "Chicken",
        strMealThumb = "strMealThumb",
        strInstructions = "strInstructions",
        ingredientsPair = listOf(Pair("Ingredients", "Measure"))
    )
}


