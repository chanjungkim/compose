package com.github.chanjungkim.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PuppyItem(
    data: PuppyData,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        modifier = modifier
            .clip(RoundedCornerShape(15.dp))
            .background(Color(0xB938BCF8))
            .padding(15.dp)
    ) {
        Image(
            painter = painterResource(id = data.resId),
            contentDescription = "it's ${data.name}",
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(30.dp))
        )
        Column {
            Text(
                text = "Name: ${data.name}",
                fontSize = 16.sp
            )
            Text(
                text = "Age: ${data.age}",
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "${data.description}",
                fontSize = 14.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PuppyItemPreview() {
    PuppyItem(
        data = PuppyData(
            resId = R.drawable.puppy1,
            name = "Puppy 1",
            age = 1,
            description = "This is puppy 1"
        )
    )
}