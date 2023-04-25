# compose
[![](https://jitpack.io/v/chanjungkim/compose.svg)](https://jitpack.io/#chanjungkim/compose)

This project will keep updated some features that compose doesn't provide.

1. `scroll bar` is the first thing. compose doesn't provide scrollbar.
2. What else?

# Setup

To get a Git project into your build:

Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:
```
allprojects {
    repositories {
        ...
		maven { url 'https://jitpack.io' }
   	}
}
```
Step 2. Add the dependency
```
dependencies {
    implementation 'com.github.chanjungkim:compose:Tag'
}
```

# Usage

## Scroll Scolumns

They are the extension of Column and LazyColumn.
You can pass `ScrollbarSource` to change the scrollbar's height, thickness, radius, thumbColor, trackColor and also fadeDuration.

### ScrollColumn

```
@Composable
fun Greeting(name: String) {
    ScrollColumn(
        scrollbarSource = ScrollbarSource(
            fadeDuration = 500,
            thumbColor = Color(0xFF38BCF8),
            thickness = 5.dp,
            trackColor = Color.LightGray.copy(alpha = 0.5f),
            radius = 8.dp
        )
    ){
        repeat(100) {
            Text("Hello $name!")
        }
    }
}
```

### LazyScrollColumn

```
@Composable
fun Greeting(name: String) {
    LazyScrollColumn(
        scrollbarSource = ScrollbarSource(
            fadeDuration = 500,
            thumbColor = Color(0xFF38BCF8),
            thickness = 5.dp,
            trackColor = Color.LightGray.copy(alpha = 0.5f),
            radius = 8.dp
        ),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier.padding(15.dp)
    ) {
        items(puppies){
            PuppyItem(
                data = it,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}
```

![compose-scrollabar](https://user-images.githubusercontent.com/19689773/233661167-5e42a274-6296-487c-9f8f-67ec915b3622.gif)
