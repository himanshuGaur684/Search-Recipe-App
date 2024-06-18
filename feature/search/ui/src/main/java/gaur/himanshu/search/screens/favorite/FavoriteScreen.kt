package gaur.himanshu.search.screens.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import gaur.himanshu.common.navigation.NavigationRoute
import gaur.himanshu.common.utils.UiText
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: FavoriteViewModel,
    onClick: (String) -> Unit
) {
    val showDropDown = rememberSaveable {
        mutableStateOf(false)
    }
    val selectedIndex = rememberSaveable {
        mutableStateOf(-1)
    }
    val uiState = viewModel.uiState.collectAsState()

    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(key1 = viewModel.navigation) {
        viewModel.navigation.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest { navigation ->
                when (navigation) {
                    is FavoriteScreen.Navigation.GoToRecipeDetailsScreen -> {
                        navHostController.navigate(NavigationRoute.RecipeDetails.sendId(navigation.id))
                    }
                }

            }
    }

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = "Favorite Recipes",
                style = MaterialTheme.typography.headlineSmall
            )
        }, actions = {
            IconButton(onClick = {
                showDropDown.value = showDropDown.value.not()
            }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
            }
            if (showDropDown.value) {
                DropdownMenu(expanded = showDropDown.value, onDismissRequest = {
                    showDropDown.value = !showDropDown.value
                }) {
                    DropdownMenuItem(text = { Text(text = "Alphabetical") }, onClick = {
                        selectedIndex.value = 0
                        showDropDown.value = showDropDown.value.not()
                        viewModel.onEvent(FavoriteScreen.Event.AlphabeticalSort)
                    }, leadingIcon = {
                        RadioButton(selected = selectedIndex.value == 0, onClick = {
                            selectedIndex.value = 0
                            showDropDown.value = showDropDown.value.not()
                            viewModel.onEvent(FavoriteScreen.Event.AlphabeticalSort)
                        })
                    })

                    DropdownMenuItem(text = { Text(text = "Less Ingredients") }, onClick = {
                        selectedIndex.value = 1
                        showDropDown.value = showDropDown.value.not()
                        viewModel.onEvent(FavoriteScreen.Event.LessIngredientsSort)
                    }, leadingIcon = {
                        RadioButton(selected = selectedIndex.value == 1, onClick = {
                            selectedIndex.value = 1
                            showDropDown.value = showDropDown.value.not()
                            viewModel.onEvent(FavoriteScreen.Event.LessIngredientsSort)
                        })
                    })

                    DropdownMenuItem(text = { Text(text = "Reset") }, onClick = {
                        selectedIndex.value = 2
                        showDropDown.value = showDropDown.value.not()
                        viewModel.onEvent(FavoriteScreen.Event.ResetSort)
                    }, leadingIcon = {
                        RadioButton(selected = selectedIndex.value == 2, onClick = {
                            selectedIndex.value = 2
                            showDropDown.value = showDropDown.value.not()
                            viewModel.onEvent(FavoriteScreen.Event.ResetSort)
                        })
                    })

                }
            }


        })
    }) {
        if (uiState.value.isLoading) {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        if (uiState.value.error !is UiText.Idle) {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Text(text = uiState.value.error.getString())
            }
        }

        uiState.value.data?.let { list ->

            if (list.isEmpty()) {
                Box(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text(text = "Nothing Found")
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                ) {
                    items(list) {
                        Card(
                            modifier = Modifier
                                .padding(horizontal = 12.dp, vertical = 4.dp)
                                .clickable { onClick.invoke(it.idMeal) },
                            shape = RoundedCornerShape(12.dp)
                        ) {

                            Box(modifier = Modifier.fillMaxWidth()) {
                                AsyncImage(
                                    model = it.strMealThumb,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(250.dp),
                                    contentScale = ContentScale.Crop
                                )

                                IconButton(
                                    onClick = {
                                        viewModel.onEvent(
                                            FavoriteScreen.Event.DeleteRecipe(
                                                it
                                            )
                                        )
                                    },
                                    modifier = Modifier
                                        .align(Alignment.TopEnd).padding(12.dp)
                                        .background(
                                        color = Color.White,
                                        shape = CircleShape
                                    )
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = null,
                                        tint = Color.Red
                                    )

                                }
                            }


                            Spacer(modifier = Modifier.height(12.dp))
                            Column(
                                modifier = Modifier
                                    .padding(vertical = 12.dp)
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                            ) {
                                Text(text = it.strMeal, style = MaterialTheme.typography.bodyLarge)
                                Spacer(modifier = Modifier.height(12.dp))

                                Text(
                                    text = it.strInstructions,
                                    style = MaterialTheme.typography.bodyMedium,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 4
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                if (it.strTags.isNotEmpty()) {

                                    FlowRow {

                                        it.strTags.split(",")
                                            .forEach {
                                                Box(
                                                    modifier = Modifier
                                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                                        .wrapContentSize()
                                                        .background(
                                                            color = Color.White,
                                                            shape = RoundedCornerShape(24.dp)
                                                        )
                                                        .clip(RoundedCornerShape(24.dp))
                                                        .border(
                                                            width = 1.dp,
                                                            color = Color.Red,
                                                            shape = RoundedCornerShape(24.dp)
                                                        ),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Text(
                                                        text = it,
                                                        style = MaterialTheme.typography.bodySmall,
                                                        modifier = Modifier.padding(
                                                            horizontal = 12.dp,
                                                            vertical = 6.dp
                                                        )
                                                    )
                                                }
                                            }

                                    }

                                    Spacer(modifier = Modifier.height(12.dp))

                                }
                            }


                        }
                    }
                }
            }

        }


    }

}