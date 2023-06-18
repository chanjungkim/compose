import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.chanjungkim.compose.ScrollColumn
import com.github.chanjungkim.compose.ScrollbarSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScrollColumnTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun scrollColumnTest() = runTest {
        val targetScrollPosition = 100
        val scrollState = mutableStateOf<ScrollState?>(null)
        val coroutineScope = mutableStateOf<CoroutineScope?>(null)

        composeTestRule.setContent {
            scrollState.value = rememberScrollState()
            coroutineScope.value = rememberCoroutineScope()

            ScrollColumn(
                scrollbarSource = ScrollbarSource(),
                scrollState = scrollState.value!!,
                modifier = Modifier.fillMaxSize()
            ) {
                repeat(100) {
                    Text(text = "Your Text $it")
                }
            }
        }

        // Await until UI thread has composed and drawn the screen
        composeTestRule.awaitIdle()

        // Add MonotonicFrameClock instance to the coroutine context
        scrollState.value!!.scrollTo(targetScrollPosition)

        // Await until scrolling is finished
        while (scrollState.value!!.isScrollInProgress) {
            delay(50) // This is to prevent the while loop from spinning too fast
        }

        checkScrollPosition(scrollState.value!!, targetScrollPosition)
    }

    private suspend fun checkScrollPosition(scrollState: ScrollState, target: Int) {
        delay(1000) // Give some time for the UI to complete the scrolling
        assertEquals(target, scrollState.value)
    }
}
