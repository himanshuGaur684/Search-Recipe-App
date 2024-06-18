package gaur.himanshu.search.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gaur.himanshu.common.utils.UiText
import gaur.himanshu.search.domain.model.Recipe
import gaur.himanshu.search.domain.use_cases.DeleteRecipeUseCase
import gaur.himanshu.search.domain.use_cases.GetAllRecipesFromLocalDbUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getAllRecipesFromLocalDbUseCase: GetAllRecipesFromLocalDbUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase
) :
    ViewModel() {

    private var originalList = mutableListOf<Recipe>()

    private val _uiState = MutableStateFlow(FavoriteScreen.UiState())
    val uiState: StateFlow<FavoriteScreen.UiState> get() = _uiState.asStateFlow()

    private val _navigation = Channel<FavoriteScreen.Navigation>()
    val navigation: Flow<FavoriteScreen.Navigation> = _navigation.receiveAsFlow()

    init {
        getRecipeList()
    }

    fun onEvent(event: FavoriteScreen.Event) {
        when (event) {
            FavoriteScreen.Event.AlphabeticalSort -> alphabeticalSort()
            FavoriteScreen.Event.LessIngredientsSort -> lessIngredientsSort()
            FavoriteScreen.Event.ResetSort -> resetSort()
            is FavoriteScreen.Event.ShowDetails -> viewModelScope.launch {
                _navigation.send(FavoriteScreen.Navigation.GoToRecipeDetailsScreen(event.id))
            }

            is FavoriteScreen.Event.DeleteRecipe -> deleteRecipe(event.recipe)
            is FavoriteScreen.Event.GoToDetails -> viewModelScope.launch {
                _navigation.send(FavoriteScreen.Navigation.GoToRecipeDetailsScreen(event.id))
            }
        }
    }

    private fun deleteRecipe(recipe: Recipe)= deleteRecipeUseCase.invoke(recipe)
        .launchIn(viewModelScope)

    private fun getRecipeList() =
        viewModelScope.launch {
            getAllRecipesFromLocalDbUseCase.invoke().collectLatest { list ->
                originalList = list.toMutableList()
                _uiState.update { FavoriteScreen.UiState(data = list) }
            }
        }


    fun alphabeticalSort() =
        _uiState.update { FavoriteScreen.UiState(data = originalList.sortedBy { it.strMeal }) }

    fun lessIngredientsSort() =
        _uiState.update { FavoriteScreen.UiState(data = originalList.sortedBy { it.strInstructions.length }) }

    fun resetSort() {
        _uiState.update { FavoriteScreen.UiState(data = originalList) }
    }

}

object FavoriteScreen {
    data class UiState(
        val isLoading: Boolean = false,
        val error: UiText = UiText.Idle,
        val data: List<Recipe>? = null
    )

    sealed interface Navigation {
        data class GoToRecipeDetailsScreen(val id: String) : Navigation
    }

    sealed interface Event {
        data object AlphabeticalSort : Event
        data object LessIngredientsSort : Event
        data object ResetSort : Event
        data class ShowDetails(val id: String) : Event
        data class DeleteRecipe(val recipe: Recipe) : Event
        data class GoToDetails(val id:String):Event
    }

}