package gaur.himanshu.searchrecipeapp.navigation

import gaur.himanshu.media_player.navigation.MediaPlayerFeatureAPi
import gaur.himanshu.search.navigation.SearchFeatureApi

data class NavigationSubGraphs(
    val searchFeatureApi: SearchFeatureApi,
    val mediaPlayerApi:MediaPlayerFeatureAPi
)
