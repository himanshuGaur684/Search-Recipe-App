package gaur.himanshu.common.navigation

sealed class NavigationRoute(val route: String) {

    data object RecipeList : NavigationRoute("/recipe_list")
    data object RecipeDetails : NavigationRoute("/recipe_details/{id}") {
        fun sendId(id: String) = "/recipe_details/${id}"
    }
    data object FavoriteScreen:NavigationRoute("/favorite")

    data object MediaPlayer : NavigationRoute("/player/{video_id}"){
        fun sendUrl(videoId:String) = "/player/$videoId"
    }

}

sealed class NavigationSubGraphRoute(val route:String){
    data object Search: NavigationSubGraphRoute(route = "/search")
    data object MediaPlayer: NavigationSubGraphRoute(route = "/media_player")
}
