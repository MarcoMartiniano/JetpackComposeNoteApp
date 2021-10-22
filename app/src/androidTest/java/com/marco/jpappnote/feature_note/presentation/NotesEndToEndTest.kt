package com.marco.jpappnote.feature_note.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.marco.jpappnote.core.util.TestTags
import com.marco.jpappnote.di.AppModule
import com.marco.jpappnote.feature_note.presentation.add_edit_note.AddEditNoteScreen
import com.marco.jpappnote.feature_note.presentation.notes.NotesScreen
import com.marco.jpappnote.feature_note.presentation.util.Screen
import com.marco.jpappnote.ui.theme.NoteAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesEndToEndTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @OptIn(ExperimentalAnimationApi::class)
    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.setContent {
            NoteAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.NotesScreen.route
                ){
                    composable(route = Screen.NotesScreen.route){
                        NotesScreen(navController = navController)
                    }
                    composable(route = Screen.AddEditNoteScreen.route + "?noteId={noteId}&noteColor={noteColor}",
                        arguments = listOf(
                            navArgument(
                                name = "noteId"
                            ){
                                type = NavType.IntType
                                defaultValue = -1
                            },
                            navArgument(
                                name = "noteColor"
                            ){
                                type = NavType.IntType
                                defaultValue = -1
                            },
                        )
                    ){
                        val color = it.arguments?.getInt("noteColor") ?: -1
                        AddEditNoteScreen(navController = navController, noteColor = color)
                    }
                }
            }
        }
    }

    @Test
    fun saveNewNote_editAfterwards() {
        // Click on FAB to get to add note screen
        composeRule.onNodeWithContentDescription("Add").performClick()

        // Enter texts in title and content text fields
        composeRule
            .onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
            .performTextInput("test-title")
        composeRule
            .onNodeWithTag(TestTags.CONTENT_TEXT_FIELD)
            .performTextInput("test-content")
        // Save the new
        composeRule.onNodeWithContentDescription("Save").performClick()

        // Make sure there is a note in the list with our title and content
        composeRule.onNodeWithText("test-title").assertIsDisplayed()
        // Click on note to edit it
        composeRule.onNodeWithText("test-title").performClick()

        // Make sure title and content text fields contain note title and content
        composeRule
            .onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
            .assertTextEquals("test-title")
        composeRule
            .onNodeWithTag(TestTags.CONTENT_TEXT_FIELD)
            .assertTextEquals("test-content")
        // Add the text "2" to the title text field
        composeRule
            .onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
            .performTextInput("2")
        // Update the note
        composeRule.onNodeWithContentDescription("Save").performClick()

        // Make sure the update was applied to the list
        composeRule.onNodeWithText("test-title2").assertIsDisplayed()
    }

    //TITLE

    @Test
    fun saveNewNotes_orderByTitleDescending() {
        for(i in 1..3) {
            // Click on FAB to get to add note screen
            composeRule.onNodeWithContentDescription("Add").performClick()

            // Enter texts in title and content text fields
            composeRule
                .onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
                .performTextInput(i.toString())
            composeRule
                .onNodeWithTag(TestTags.CONTENT_TEXT_FIELD)
                .performTextInput(i.toString())
            // Save the new
            composeRule.onNodeWithContentDescription("Save").performClick()
        }

        composeRule.onNodeWithText("1").assertIsDisplayed()
        composeRule.onNodeWithText("2").assertIsDisplayed()
        composeRule.onNodeWithText("3").assertIsDisplayed()

        composeRule
            .onNodeWithContentDescription("Sort")
            .performClick()
        composeRule
            .onNodeWithContentDescription("Title")
            .performClick()
        composeRule
            .onNodeWithContentDescription("Descending")
            .performClick()

        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[0]
            .assertTextContains("3")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[1]
            .assertTextContains("2")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[2]
            .assertTextContains("1")
    }

    @Test
    fun saveNewNotes_orderByTitleAscending() {
        for(i in 1..3) {
            // Click on FAB to get to add note screen
            composeRule.onNodeWithContentDescription("Add").performClick()

            // Enter texts in title and content text fields
            composeRule
                .onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
                .performTextInput(i.toString())
            composeRule
                .onNodeWithTag(TestTags.CONTENT_TEXT_FIELD)
                .performTextInput(i.toString())
            // Save the new
            composeRule.onNodeWithContentDescription("Save").performClick()
        }

        composeRule.onNodeWithText("1").assertIsDisplayed()
        composeRule.onNodeWithText("2").assertIsDisplayed()
        composeRule.onNodeWithText("3").assertIsDisplayed()

        composeRule
            .onNodeWithContentDescription("Sort")
            .performClick()
        composeRule
            .onNodeWithContentDescription("Title")
            .performClick()
        composeRule
            .onNodeWithContentDescription("Ascending")
            .performClick()

        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[0]
            .assertTextContains("1")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[1]
            .assertTextContains("2")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[2]
            .assertTextContains("3")
    }

    //CONTENT

    @Test
    fun saveNewNotes_orderByContentDescending() {
        for(i in 1..3) {
            // Click on FAB to get to add note screen
            composeRule.onNodeWithContentDescription("Add").performClick()

            // Enter texts in title and content text fields
            composeRule
                .onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
                .performTextInput(i.toString())
            composeRule
                .onNodeWithTag(TestTags.CONTENT_TEXT_FIELD)
                .performTextInput("content$i")
            // Save the new
            composeRule.onNodeWithContentDescription("Save").performClick()
        }

        composeRule.onNodeWithText("1").assertIsDisplayed()
        composeRule.onNodeWithText("2").assertIsDisplayed()
        composeRule.onNodeWithText("3").assertIsDisplayed()

        composeRule
            .onNodeWithContentDescription("Sort")
            .performClick()
        composeRule
            .onNodeWithContentDescription("Title")
            .performClick()
        composeRule
            .onNodeWithContentDescription("Descending")
            .performClick()

        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[0]
            .assertTextContains("content3")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[1]
            .assertTextContains("content2")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[2]
            .assertTextContains("content1")
    }

    @Test
    fun saveNewNotes_orderByContentAscending() {
        for(i in 1..3) {
            // Click on FAB to get to add note screen
            composeRule.onNodeWithContentDescription("Add").performClick()

            // Enter texts in title and content text fields
            composeRule
                .onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
                .performTextInput(i.toString())
            composeRule
                .onNodeWithTag(TestTags.CONTENT_TEXT_FIELD)
                .performTextInput("content$i")
            // Save the new
            composeRule.onNodeWithContentDescription("Save").performClick()
        }

        composeRule.onNodeWithText("1").assertIsDisplayed()
        composeRule.onNodeWithText("2").assertIsDisplayed()
        composeRule.onNodeWithText("3").assertIsDisplayed()

        composeRule
            .onNodeWithContentDescription("Sort")
            .performClick()
        composeRule
            .onNodeWithContentDescription("Title")
            .performClick()
        composeRule
            .onNodeWithContentDescription("Ascending")
            .performClick()

        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[0]
            .assertTextContains("content1")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[1]
            .assertTextContains("content2")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[2]
            .assertTextContains("content3")
    }




    //Color
    //Colors Position Descending
    //0 - Salmon Color-21615
    //1 - RedPink Color-749647
    //2 - Yellow Color-1577573
    //2 - Violet Color-3173158
    //3 - BabyBlue Color-8266006


    @Test
    fun saveNewNotes_orderByColorDescending() {
        for(i in 4 downTo 0) {
            // Click on FAB to get to add note screen
            composeRule.onNodeWithContentDescription("Add").performClick()

            //Choose Color
            composeRule
                .onNodeWithTag("Color$i").performClick()

            // Enter texts in title and content text fields
            composeRule
                .onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
                .performTextInput(i.toString())
            composeRule
                .onNodeWithTag(TestTags.CONTENT_TEXT_FIELD)
                .performTextInput("content$i")

            // Save the new
            composeRule.onNodeWithContentDescription("Save").performClick()
        }

        composeRule.onNodeWithText("0").assertIsDisplayed()
        composeRule.onNodeWithText("1").assertIsDisplayed()
        composeRule.onNodeWithText("2").assertIsDisplayed()
        composeRule.onNodeWithText("3").assertIsDisplayed()
        composeRule.onNodeWithText("4").assertIsDisplayed()

        composeRule
            .onNodeWithContentDescription("Sort")
            .performClick()
        composeRule
            .onNodeWithContentDescription("Color")
            .performClick()
        composeRule
            .onNodeWithContentDescription("Descending")
            .performClick()

        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[0]
            .assertContentDescriptionContains("Color-21615")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[1]
            .assertContentDescriptionContains("Color-749647")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[2]
           .assertContentDescriptionContains("Color-1577573")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[3]
          .assertContentDescriptionContains("Color-3173158")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[4]
            .assertContentDescriptionContains("Color-8266006")
    }


    @Test
    fun saveNewNotes_orderByColorAscending() {
        for(i in 4 downTo 0) {
            // Click on FAB to get to add note screen
            composeRule.onNodeWithContentDescription("Add").performClick()

            //Choose Color
            composeRule
                .onNodeWithTag("Color$i").performClick()

            // Enter texts in title and content text fields
            composeRule
                .onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
                .performTextInput(i.toString())
            composeRule
                .onNodeWithTag(TestTags.CONTENT_TEXT_FIELD)
                .performTextInput("content$i")

            // Save the new
            composeRule.onNodeWithContentDescription("Save").performClick()
        }

        composeRule.onNodeWithText("0").assertIsDisplayed()
        composeRule.onNodeWithText("1").assertIsDisplayed()
        composeRule.onNodeWithText("2").assertIsDisplayed()
        composeRule.onNodeWithText("3").assertIsDisplayed()
        composeRule.onNodeWithText("4").assertIsDisplayed()

        composeRule
            .onNodeWithContentDescription("Sort")
            .performClick()
        composeRule
            .onNodeWithContentDescription("Color")
            .performClick()
        composeRule
            .onNodeWithContentDescription("Ascending")
            .performClick()

        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[4]
            .assertContentDescriptionContains("Color-21615")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[3]
            .assertContentDescriptionContains("Color-749647")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[2]
            .assertContentDescriptionContains("Color-1577573")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[1]
            .assertContentDescriptionContains("Color-3173158")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[0]
            .assertContentDescriptionContains("Color-8266006")
    }

}