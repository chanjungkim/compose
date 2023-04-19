# compose
[![](https://jitpack.io/v/chanjungkim/compose.svg)](https://jitpack.io/#chanjungkim/compose)

This project will keep updated some features that compose doesn't provide. `scroll bar` is the first thing.

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

```
@Composable
fun Greeting(name: String) {
    ScrollColumn{
        repeat(100) {
            Text("Hello $name!")
        }
    }
}
```

```
@Composable
fun Greeting(name: String) {
    LazyScrollColumn{
        repeat(100) {
            Text("Hello $name!")
        }
    }
}
```
