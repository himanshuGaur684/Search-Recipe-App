# Unit Testing

### Fake Data That i will use in Unit Testing

### Data Layer 


```kotlin
private fun getRecipeResponse(): RecipeResponse {
    return RecipeResponse(
        meals = listOf(
            RecipeDTO(
                dateModified = null,
                idMeal = "idMeal",
                strArea = "India",
                strCategory = "category",
                strYoutube = "strYoutube",
                strTags = "tag1,tag2",
                strMeal = "Chicken",
                strSource = "strSource",
                strMealThumb = "strMealThumb",
                strInstructions = "strInstructions",
                strCreativeCommonsConfirmed = null,
                strIngredient1 = null,
                strIngredient2 = null,
                strIngredient3 = null,
                strIngredient4 = null,
                strIngredient5 = null,
                strIngredient6 = null,
                strIngredient7 = null,
                strIngredient8 = null,
                strIngredient9 = null,
                strIngredient10 = null,
                strIngredient11 = null,
                strIngredient12 = null,
                strIngredient13 = null,
                strIngredient14 = null,
                strIngredient15 = null,
                strIngredient16 = null,
                strIngredient17 = null,
                strIngredient18 = null,
                strIngredient19 = null,
                strIngredient20 = null,
                strMeasure1 = null,
                strMeasure2 = null,
                strMeasure3 = null,
                strMeasure4 = null,
                strMeasure5 = null,
                strMeasure6 = null,
                strMeasure7 = null,
                strMeasure8 = null,
                strMeasure9 = null,
                strMeasure10 = null,
                strMeasure11 = null,
                strMeasure12 = null,
                strMeasure13 = null,
                strMeasure14 = null,
                strMeasure15 = null,
                strMeasure16 = null,
                strMeasure17 = null,
                strMeasure18 = null,
                strMeasure19 = null,
                strMeasure20 = null,
                strDrinkAlternate = null,
                strImageSource = "empty"
            )
        )
    )
}

private fun getRecipeDetails(): RecipeDetailsResponse {
    return RecipeDetailsResponse(
        meals = listOf(
            getRecipeResponse().meals?.first()!!
        )
    )
}
```

### Domain Layer

```kotlin

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
```

### UI Layer


```kotlin
private fun getRecipeResponse(): RecipeResponse {
    return RecipeResponse(
        meals = listOf(
            RecipeDTO(
                dateModified = null,
                idMeal = "idMeal",
                strArea = "India",
                strCategory = "category",
                strYoutube = "strYoutube",
                strTags = "tag1,tag2",
                strMeal = "Chicken",
                strSource = "strSource",
                strMealThumb = "strMealThumb",
                strInstructions = "strInstructions",
                strCreativeCommonsConfirmed = null,
                strIngredient1 = null,
                strIngredient2 = null,
                strIngredient3 = null,
                strIngredient4 = null,
                strIngredient5 = null,
                strIngredient6 = null,
                strIngredient7 = null,
                strIngredient8 = null,
                strIngredient9 = null,
                strIngredient10 = null,
                strIngredient11 = null,
                strIngredient12 = null,
                strIngredient13 = null,
                strIngredient14 = null,
                strIngredient15 = null,
                strIngredient16 = null,
                strIngredient17 = null,
                strIngredient18 = null,
                strIngredient19 = null,
                strIngredient20 = null,
                strMeasure1 = null,
                strMeasure2 = null,
                strMeasure3 = null,
                strMeasure4 = null,
                strMeasure5 = null,
                strMeasure6 = null,
                strMeasure7 = null,
                strMeasure8 = null,
                strMeasure9 = null,
                strMeasure10 = null,
                strMeasure11 = null,
                strMeasure12 = null,
                strMeasure13 = null,
                strMeasure14 = null,
                strMeasure15 = null,
                strMeasure16 = null,
                strMeasure17 = null,
                strMeasure18 = null,
                strMeasure19 = null,
                strMeasure20 = null,
                strDrinkAlternate = null,
                strImageSource = "empty"
            )
        )
    )
}

private fun getRecipeDetails(): RecipeDetailsResponse {
    return RecipeDetailsResponse(
        meals = listOf(
            getRecipeResponse().meals?.first()!!
        )
    )
}
```

### Domain Layer

```kotlin

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
```



