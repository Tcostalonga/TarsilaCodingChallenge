package tarsila.codingchallenge.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import tarsila.codingchallenge.data.remote.api.RecipeApi
import tarsila.codingchallenge.data.remote.apimockservice.MockApiService
import tarsila.codingchallenge.data.remote.repository.RecipeRepositoryImpl
import tarsila.codingchallenge.domain.RecipeRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RecipeModule {

    @Binds
    @Singleton
    abstract fun bindRecipeRepository(
        recipeRepositoryImpl: RecipeRepositoryImpl,
    ): RecipeRepository

    companion object {
        @Provides
        @Singleton
        fun provideRecipeApi(
            @ApplicationContext context: Context,
        ): RecipeApi {
            return MockApiService(context)
        }
    }
}
