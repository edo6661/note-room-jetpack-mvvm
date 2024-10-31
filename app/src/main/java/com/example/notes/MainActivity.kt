package com.example.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.notes.presentation.bookmark.BookmarkViewModel
import com.example.notes.presentation.detail.DetailAssistedFactory
import com.example.notes.presentation.home.HomeViewModel
import com.example.notes.presentation.navigation.NoteNavigation
import com.example.notes.presentation.navigation.Screens
import com.example.notes.presentation.navigation.navigateToSingleTop
import com.example.notes.ui.theme.NotesTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  @Inject
  lateinit var assistedFactory : DetailAssistedFactory

  override fun onCreate(savedInstanceState : Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      NotesTheme {
        NoteApp()


      }
    }
  }

  @Composable
  private fun NoteApp() {
    val homeViewModel : HomeViewModel = viewModel()
    val bookmarkViewModel : BookmarkViewModel = viewModel()
    val navController = rememberNavController(
    )
    BottomTabs(
      content = {
        NoteNavigation(
          navHostController = navController,
          homeViewModel = homeViewModel,
          bookmarkViewModel = bookmarkViewModel,
          assistedFactory = assistedFactory
        )
      },
      navController = navController
    )

  }

  @Composable
  private fun BottomTabs(
    content : @Composable (modifier : Modifier) -> Unit,
    navController : NavHostController
  ) {
    var currentScreen by remember { mutableStateOf(Screens.Home.route) }
    val hiddenScreens = setOf(Screens.Detail.ROUTE)
    var showBottomBarAndFab by remember { mutableStateOf(true) }

    // Listen to navigation changes to hide/show bottom bar
    LaunchedEffect(navController) {
      navController.addOnDestinationChangedListener { _, destination, _ ->
        showBottomBarAndFab = ! hiddenScreens.contains(destination.route)
      }
    }

    Scaffold(
      modifier = Modifier.systemBarsPadding(),
      bottomBar = {
        BottomAppBar(

          actions = {
            Row(
              horizontalArrangement = Arrangement.SpaceBetween,
              modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .padding(16.dp)
            ) {
              InputChip(
                selected = currentScreen == Screens.Home.route,
                onClick = {
                  currentScreen = Screens.Home.route
                  navController.navigateToSingleTop(Screens.Home.route)
                },
                label = { Text("Home") },
                trailingIcon = {
                  Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home"
                  )
                }
              )
              Spacer(modifier = Modifier.size(16.dp))
              InputChip(
                selected = currentScreen == Screens.Bookmark.route,
                onClick = {
                  currentScreen = Screens.Bookmark.route
                  navController.navigateToSingleTop(Screens.Bookmark.route)
                },
                label = { Text("Bookmark") },
                trailingIcon = {
                  Icon(
                    imageVector = Icons.Default.Book,
                    contentDescription = "Bookmark"
                  )
                }
              )
            }
          },

          )

      },
      floatingActionButton = {
        FloatingActionButton(
          onClick = {
            navController.navigateToSingleTop(Screens.Detail.createRoute(- 1))
          }
        ) {
          Icon(imageVector = Icons.Default.Add, contentDescription = "Add Note")
        }

      }
    ) { innerPadding ->
      content(Modifier.padding(innerPadding))
    }
  }


}

