package gaur.himanshu.searchrecipeapp.repository

import gaur.himanshu.search.data.local.RecipeDao
import gaur.himanshu.search.domain.model.Recipe
import gaur.himanshu.search.domain.model.RecipeDetails
import gaur.himanshu.search.domain.repository.SearchRepository
import gaur.himanshu.searchrecipeapp.utils.getRecipeDetailsList
import gaur.himanshu.searchrecipeapp.utils.getRecipeResponse
import kotlinx.coroutines.flow.Flow

class FakeSearchRepository(private val recipeDao: RecipeDao) : SearchRepository {

    override suspend fun getRecipes(s: String): Result<List<Recipe>> {
        return Result.success(getRecipeResponse())
    }

    override suspend fun getRecipeDetails(id: String): Result<RecipeDetails> {
        return getRecipeDetailsList().find { it.idMeal == id }?.let { recipeDetails ->
            Result.success(recipeDetails)
        } ?: run { Result.success(gaur.himanshu.searchrecipeapp.utils.getRecipeDetails()) }
    }

    override suspend fun insertRecipe(recipe: Recipe) {
       recipeDao.insertRecipe(recipe)
    }

    override suspend fun deleteRecipe(recipe: Recipe) {
        recipeDao.deleteRecipe(recipe)
    }

    override fun getAllRecipes(): Flow<List<Recipe>> {
        return recipeDao.getAllRecipes()
    }
}