package gaur.himanshu.searchrecipeapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import gaur.himanshu.media_player.navigation.MediaPlayerFeatureAPi
import gaur.himanshu.search.data.local.RecipeDao
import gaur.himanshu.search.navigation.SearchFeatureApi
import gaur.himanshu.searchrecipeapp.local.AppDatabase
import gaur.himanshu.searchrecipeapp.navigation.NavigationSubGraphs
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideNavigationSubGraphs(
        searchFeatureApi: SearchFeatureApi,
        mediaPlayerFeatureAPi: MediaPlayerFeatureAPi
    ): NavigationSubGraphs {
        return NavigationSubGraphs(searchFeatureApi, mediaPlayerFeatureAPi)
    }


    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) = AppDatabase.getInstance(context)

    @Provides
    fun provideRecipeDao(appDatabase: AppDatabase): RecipeDao {
        return appDatabase.getRecipeDao()
    }

}