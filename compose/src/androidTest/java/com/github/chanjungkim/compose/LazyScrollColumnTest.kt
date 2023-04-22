package com.github.chanjungkim.compose

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.isDebugInspectorInfoEnabled
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.unit.sp
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LazyScrollColumnTest {
    @get:Rule
    val rule = createComposeRule()

    private val scrollableAreaTag = "scrollableArea"
    private val focusableTag = "focusable"
    private val scrollState = ScrollState(initial = 0)
    private val lazyScrollState = LazyListState(0)
    private lateinit var focusManager: FocusManager

    @Before
    fun before() {
        isDebugInspectorInfoEnabled = true
    }

    @After
    fun after() {
        isDebugInspectorInfoEnabled = false
    }

    @Test
    fun scrollDown() {
        rule.setContent {
            LazyScrollColumn (
                state = lazyScrollState,
                modifier = Modifier.fillMaxSize().testTag(focusableTag)
            ){
                items(30) {
                    Text(
                        text = "Item $it",
                        fontSize = 100.sp
                    )
                }
            }
        }
        requestFocusAndScrollToTDown()
    }

    @Test
    fun scrollUp() {
        rule.setContent {
            LazyScrollColumn (
                modifier = Modifier.fillMaxSize()
            ){
                items(30) {
                    Text(
                        text = "Item $it",
                        fontSize = 100.sp
                    )
                }
            }
        }
        requestFocusAndScrollToTop()
    }

    private fun requestFocusAndScrollToTop() {
        requestFocus()
        scrollToTop()
    }

    private fun requestFocusAndScrollToTDown() {
        requestFocus()
        scrollToDown()
    }


    private fun requestFocus(tag: String = focusableTag) {
        rule.onNodeWithTag(tag).performSemanticsAction(SemanticsActions.RequestFocus)
    }

    private fun scrollToTop() {
        rule.waitForIdle()
        // Reset scroll to top since requesting focus will scroll it.
        runBlocking {
            lazyScrollState.scrollToItem(0)
        }
    }

    private fun scrollToDown() {
        rule.waitForIdle()
        // Reset scroll to top since requesting focus will scroll it.
        runBlocking {
            lazyScrollState.scrollToItem(30)
        }
    }
}