package gaur.himanshu.search.screens.recipe_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gaur.himanshu.common.utils.NetworkResult
import gaur.himanshu.common.utils.UiText
import gaur.himanshu.search.domain.model.Recipe
import gaur.himanshu.search.domain.use_cases.GetAllRecipeUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val getAllRecipeUseCase: GetAllRecipeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecipeList.UiState())
    val uiState: StateFlow<RecipeList.UiState> get() = _uiState.asStateFlow()

    private val _navigation = Channel<RecipeList.Navigation>()
    val navigation: Flow<RecipeList.Navigation> = _navigation.receiveAsFlow()

    fun onEvent(event: RecipeList.Event) {
        when (event) {
            is RecipeList.Event.SearchRecipe -> {
                search(event.q)
            }

            is RecipeList.Event.GoToRecipeDetails -> {
                viewModelScope.launch {
                    _navigation.send(RecipeList.Navigation.GoToRecipeDetails(event.id))
                }
            }

            RecipeList.Event.FavoriteScreen -> viewModelScope.launch {
                _navigation.send(RecipeList.Navigation.GoToFavoriteScreen)
            }
        }
    }


    private fun search(q: String) = getAllRecipeUseCase.invoke(q)
        .onEach { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    _uiState.update { RecipeList.UiState(isLoading = true) }
                }

                is NetworkResult.Error -> {
                    _uiState.update { RecipeList.UiState(error = UiText.RemoteString(result.message.toString())) }

                }

                is NetworkResult.Success -> {
                    _uiState.update { RecipeList.UiState(data = result.data) }

                }
            }

        }.launchIn(viewModelScope)


}

object RecipeList {

    data class UiState(
        val isLoading: Boolean = false,
        val error: UiText = UiText.Idle,
        val data: List<Recipe>? = null
    )

    sealed interface Navigation {

        data class GoToRecipeDetails(val id: String) : Navigation

        data object GoToFavoriteScreen:Navigation

    }

    sealed interface Event {
        data class SearchRecipe(val q: String) : Event

        data class GoToRecipeDetails(val id: String) : Event

        data object FavoriteScreen:Event
    }

}
