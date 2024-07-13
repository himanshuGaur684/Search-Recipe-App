package gaur.himanshu.search.repository

import gaur.himanshu.search.domain.model.Recipe
import gaur.himanshu.search.domain.model.RecipeDetails
import gaur.himanshu.search.domain.repository.SearchRepository
import gaur.himanshu.search.utils.getRecipeDetailsList
import gaur.himanshu.search.utils.getRecipeResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow


class FakeSuccessRepoImpl : SearchRepository {

    private val dbFlow = MutableStateFlow(emptyList<Recipe>())
    private val list = mutableListOf<Recipe>()

    fun reset() {
        list.clear()
    }

    override suspend fun getRecipes(s: String): Result<List<Recipe>> {
        return Result.success(getRecipeResponse())
    }

    override suspend fun getRecipeDetails(id: String): Result<RecipeDetails> {
        return getRecipeDetailsList().find { it.idMeal == id }?.let { recipeDetails ->
            Result.success(recipeDetails)
        } ?: run { Result.success(gaur.himanshu.search.utils.getRecipeDetails()) }
    }

    override suspend fun insertRecipe(recipe: Recipe) {
        list.add(recipe)
        dbFlow.value = list.toList()
    }

    override suspend fun deleteRecipe(recipe: Recipe) {
        list.remove(recipe)
        dbFlow.value = list.toList()
    }

    override fun getAllRecipes(): Flow<List<Recipe>> {
        return dbFlow
    }
}


class FakeFailureRepoIMpl : SearchRepository {

    private val dbFlow = MutableStateFlow(emptyList<Recipe>())
    private val list = mutableListOf<Recipe>()

    companion object {
        val errorMessage = "error message"
    }

    fun reset() {
        list.clear()
    }

    override suspend fun getRecipes(s: String): Result<List<Recipe>> {
        return Result.failure(RuntimeException(errorMessage))
    }

    override suspend fun getRecipeDetails(id: String): Result<RecipeDetails> {
        return Result.failure(RuntimeException(errorMessage))
    }

    override suspend fun insertRecipe(recipe: Recipe) {
        list.add(recipe)
        dbFlow.value = list.toList()
    }

    override suspend fun deleteRecipe(recipe: Recipe) {
        list.remove(recipe)
        dbFlow.value = list.toList()
    }

    override fun getAllRecipes(): Flow<List<Recipe>> {
        return dbFlow
    }
}

