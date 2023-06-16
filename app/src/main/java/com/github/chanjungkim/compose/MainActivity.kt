package com.github.chanjungkim.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.chanjungkim.compose.ui.theme.MyApplicationTheme

val puppies = listOf<PuppyData>(
    PuppyData(resId = R.drawable.puppy1, name = "Sam", age = 3, description = "Sam is happy."),
    PuppyData(resId = R.drawable.puppy2, name = "Lucy", age = 2, description = "Lucy is happy."),
    PuppyData(resId = R.drawable.puppy3, name = "Carl", age = 5, description = "Carl is happy."),
    PuppyData(resId = R.drawable.puppy4, name = "Max", age = 1, description = "Max is happy."),
    PuppyData(resId = R.drawable.puppy5, name = "Sean", age = 2, description = "Sean is happy."),
    PuppyData(resId = R.drawable.puppy6, name = "Chris", age = 6, description = "Chris is happy."),
    PuppyData(
        resId = R.drawable.puppy7,
        name = "Austin",
        age = 4,
        description = "Austin is happy."
    ),
    PuppyData(resId = R.drawable.puppy8, name = "Gabby", age = 3, description = "Gabby is happy."),
    PuppyData(resId = R.drawable.puppy9, name = "Alan", age = 2, description = "Alan is happy."),
    PuppyData(
        resId = R.drawable.puppy10,
        name = "Chacha",
        age = 5,
        description = "Chacha is happy."
    )
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Sample()
                }
            }
        }
    }
}

@Composable
fun Sample() {
    LazyScrollColumn(
        scrollbarSource = ScrollbarSource(
            fadeDuration = 500,
            thumbColor = Color(0xFF38BCF8),
            thickness = 5.dp,
            trackColor = Color.LightGray.copy(alpha = 0.5f),
            radius = 8.dp
        ),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier
    ) {
        val modifier = Modifier
            .padding(horizontal = 15.dp)
            .fillMaxWidth()
        itemsIndexed(items = puppies) { index, item ->
            PuppyItem(
                data = item,
                modifier = when (index) {
                    0 -> {
                        Modifier
                            .padding(top = 15.dp)
                            .then(modifier)
                    }
                    puppies.size - 1 -> {
                        Modifier
                            .padding(bottom = 15.dp)
                            .then(modifier)
                    }
                    else -> modifier
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        ScrollColumn {
            Sample()
        }
    }
}