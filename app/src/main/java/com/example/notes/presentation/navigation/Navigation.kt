package com.example.notes.presentation.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.notes.presentation.bookmark.BookmarkScreen
import com.example.notes.presentation.bookmark.BookmarkViewModel
import com.example.notes.presentation.detail.DetailAssistedFactory
import com.example.notes.presentation.detail.DetailScreen
import com.example.notes.presentation.home.HomeScreen
import com.example.notes.presentation.home.HomeViewModel

sealed class Screens(val route : String) {
  data object Home : Screens("home")
  data object Bookmark : Screens("bookmark")
  data object Detail : Screens("detail/{id}") {

    const val ROUTE = "detail/{id}"
    fun createRoute(id : Long) = "detail/$id"
  }
}

// inti dari function ini tuh, misal dari home screen kita pindah ke detail screen, terus kita kembali ke home screen, kita kembali ke home screen yang sama, bukan bikin home screen baru
fun NavHostController.navigateToSingleTop(route : String) {
  navigate(route) {
    popUpTo(graph.findStartDestination().id) {
      saveState = true
    }
    launchSingleTop = true
    restoreState = true
  }
}


@Composable
fun NoteNavigation(
  navHostController : NavHostController,
  homeViewModel : HomeViewModel,
  bookmarkViewModel : BookmarkViewModel,
  assistedFactory : DetailAssistedFactory,
) {
  NavHost(
    navController = navHostController,
    startDestination = Screens.Home.route
  ) {
    composable(Screens.Home.route) {
      val state by homeViewModel.state.collectAsState()
      HomeScreen(
        state = state,
        onBookmarkChange = homeViewModel::onBookMarkChange,
        onDeleteNote = homeViewModel::onDeleteNote,
        onNoteClick = {
          navHostController.navigateToSingleTop(Screens.Detail.createRoute(it))
        }
      )
    }

    composable(Screens.Bookmark.route) {
      val state by bookmarkViewModel.state.collectAsState()
      Log.d("NoteNavigation", "BookmarkScreen: $state")
      BookmarkScreen(
        state = state,
        onBookmarkChange = bookmarkViewModel::onBookmarkChange,
        onDeleteNote = bookmarkViewModel::onDeleteNote,
        onNoteClicked = {
          navHostController.navigateToSingleTop(Screens.Detail.createRoute(it))
        }
      )
    }

    composable(
      route = Screens.Detail.ROUTE,  // gunakan ROUTE constant
      arguments = listOf(
        navArgument("id") {
          type = NavType.LongType
          defaultValue = - 1L
        }
      )
    ) { backstackEntry ->
      val id = backstackEntry.arguments?.getLong("id") ?: - 1L
      DetailScreen(
        id = id,
        assistedFactory = assistedFactory,
        navigateUp = { navHostController.popBackStack() }
      )
    }
  }
}