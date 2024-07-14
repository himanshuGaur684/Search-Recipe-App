package gaur.himanshu.searchrecipeapp.utils


import gaur.himanshu.search.domain.model.Recipe


fun getRecipeResponse(): List<Recipe> {
    return listOf(
        Recipe(
            idMeal = "idMeal 1",
            strArea = "India",
            strCategory = "category",
            strYoutube = "strYoutube",
            strTags = "tag1,tag2",
            strMeal = "Chicken",
            strMealThumb = "strMealThumb",
            strInstructions = "12345",
        ),
        Recipe(
            idMeal = "idMeal 2",
            strArea = "India",
            strCategory = "category",
            strYoutube = "strYoutube",
            strTags = "tag3,tag4",
            strMeal = "Kadai Paneer",
            strMealThumb = "strMealThumb",
            strInstructions = "123",
        )
    )

}

fun getRecipeDetailsList(): List<gaur.himanshu.search.domain.model.RecipeDetails> {
    return listOf(
        gaur.himanshu.search.domain.model.RecipeDetails(
            idMeal = "idMeal 1",
            strArea = "India",
            strCategory = "category",
            strYoutube = "strYoutube",
            strTags = "tag1,tag2",
            strMeal = "Chicken",
            strMealThumb = "strMealThumb",
            strInstructions = "strInstructions",
            ingredientsPair = listOf(Pair("Ingredients", "Measure"))
        ),
        gaur.himanshu.search.domain.model.RecipeDetails(
            idMeal = "idMeal 2",
            strArea = "India",
            strCategory = "category",
            strYoutube = "strYoutube",
            strTags = "tag1,tag2",
            strMeal = "Kadai Paneer",
            strMealThumb = "strMealThumb",
            strInstructions = "123",
            ingredientsPair = listOf(Pair("Ingredients 2", "Measure 2"))
        )
    )
}


fun getRecipeDetails(): gaur.himanshu.search.domain.model.RecipeDetails {
    return gaur.himanshu.search.domain.model.RecipeDetails(
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


