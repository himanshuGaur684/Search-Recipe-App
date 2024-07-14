package gaur.himanshu.searchrecipeapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import gaur.himanshu.search.data.di.SearchDataModule
import gaur.himanshu.search.data.local.RecipeDao
import gaur.himanshu.search.domain.repository.SearchRepository
import gaur.himanshu.searchrecipeapp.local.AppDatabase
import gaur.himanshu.searchrecipeapp.repository.FakeSearchRepository
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [SearchDataModule::class,DataBaseModule::class]
)
@Module
object TestDi {


    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
    }

    @Provides
    fun provideRecipeDao(appDatabase: AppDatabase): RecipeDao {
        return appDatabase.getRecipeDao()
    }

    @Provides
    fun provideRepoImpl(recipeDao: RecipeDao): SearchRepository {
        return FakeSearchRepository(recipeDao)
    }


}