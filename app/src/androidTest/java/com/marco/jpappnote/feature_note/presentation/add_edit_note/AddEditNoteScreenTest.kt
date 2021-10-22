package com.marco.jpappnote.feature_note.presentation.add_edit_note

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.marco.jpappnote.core.util.TestTags
import com.marco.jpappnote.di.AppModule
import com.marco.jpappnote.feature_note.presentation.MainActivity
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
class AddEditNoteScreenTest{


    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @OptIn(ExperimentalAnimationApi::class)
    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.setContent {
            val navController = rememberNavController()
            NoteAppTheme {
                NavHost(
                    navController = navController,
                    startDestination =  Screen.AddEditNoteScreen.route){
                    composable(route = Screen.AddEditNoteScreen.route){
                        AddEditNoteScreen(navController = navController, noteColor = -1)
                    }
                }
            }
        }
    }

    //This test will: Change Color, write in TextFields and Save content
    //Colors Position
    //0 - Salmon
    //1 - Yellow
    //2 - Violet
    //3 - BabyBlue
    //4 - RedPink




    @Test
    fun clickToggleOrderSection_isVisible(){
        composeRule
            .onNodeWithTag("Color0").performClick()

        composeRule
            .onNodeWithTag("Color3").performClick()

        composeRule
            .onNodeWithTag("Color1").performClick()

        composeRule
            .onNodeWithTag("Color4").performClick()

        composeRule
            .onNodeWithTag("Color2").performClick()

        // Enter texts in title and content text fields
        composeRule
            .onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
            .performTextInput("1")
        composeRule
            .onNodeWithTag(TestTags.CONTENT_TEXT_FIELD)
            .performTextInput("1")
        // Save the new
        composeRule.onNodeWithContentDescription("Save").performClick()
    }


}