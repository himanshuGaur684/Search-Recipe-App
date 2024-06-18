package gaur.himanshu.common.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface FeatureApi {
    fun registerGraph(
        navGraphBuilder:NavGraphBuilder,
        navHostController: NavHostController
    )
}