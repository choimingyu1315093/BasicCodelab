package com.example.basiccodelab

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.coerceAtMost
import androidx.compose.ui.unit.dp
import com.example.basiccodelab.ui.theme.BasicCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BasicCodelabTheme {
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier){
    // "=" 대신 "by" 키워드를 사용하면, 매번 .value를 입력할 필요 없다.
    // rememberSaveable 화면 회전 시에도 유지된다.
//    var shouldShowOnboarding by remember { mutableStateOf(true) }
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    Surface(modifier) {
        if(shouldShowOnboarding){
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        }else {
            Greetings()
        }
    }
}

@Composable
fun OnboardingScreen(modifier: Modifier = Modifier, onContinueClicked: () -> Unit){
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Welcome to the Basics Codelab!")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked,
        ) {
            Text("Continue")
        }
    }
}

@Composable
fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = List(1000) {"$it"}
){
    //LazyColumn은 RecyclerView와 동일하다.
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ){
        CardContent(name = name)
    }
}

@Composable
fun CardContent(modifier: Modifier = Modifier, name: String){
    var expanded by rememberSaveable { mutableStateOf(false) }
    //애니메이션 효과 없음
//    val extraPadding = if(expanded) 48.dp else 0.dp
    //애니메이션
    val extraPadding by animateDpAsState(
        if(expanded) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Row(modifier = modifier
        .fillMaxWidth()
        .padding(24.dp)){
        Column(modifier = Modifier
            .weight(1f)
            .padding(bottom = extraPadding.coerceAtLeast(0.dp))){
            Text(
                text = "Hello",
            )
            Text(
                text = name,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            if(expanded){
                Text(
                    text = ("Compose ipsum color sit lazy, padding theme elit, sed do bouncy.").repeat(4)
                )
            }
        }
        IconButton(
            onClick = {expanded = !expanded}
        ) {
            Icon(
                imageVector = if(expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if(expanded){
                    stringResource(R.string.show_less)
                }else {
                    stringResource(R.string.show_more)
                }
            )
        }
//        ElevatedButton(
//            onClick = {
//                expanded = !expanded
//            }
//        ) {
//            Text(if(expanded) stringResource(R.string.show_less) else stringResource(R.string.show_more))
//        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun GreetingPreview() {
    BasicCodelabTheme {
        MyApp(Modifier.fillMaxSize())
    }
}