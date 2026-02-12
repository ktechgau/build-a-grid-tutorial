package com.example.buildagrid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.buildagrid.data.Datasource
import com.example.buildagrid.model.Topic
import com.example.buildagrid.ui.theme.BuildAGridTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BuildAGridTheme {
               Surface(
                   modifier = Modifier.fillMaxSize(),
                   color = MaterialTheme.colorScheme.background
               ) {
                   TopicsGridApp()
               }
            }
        }
    }
}

@Composable
fun TopicsGridApp(modifier: Modifier = Modifier){
    val layoutDirection = LocalLayoutDirection.current
    
    Surface(
        modifier = Modifier
            /* Explanations:
            Surface creates a mainbackground area (surface) that fills the entire screen.
            statusBarsPadding() prevents UI from overlapping with status bar icons
            WindowInsets.safeDrawing.asPaddingValues() prevents UI from overlapping with system bars
*/
            .fillMaxSize()
            .statusBarsPadding()
            .padding(
                start = WindowInsets.safeDrawing.asPaddingValues()
                    .calculateStartPadding(layoutDirection),
                end = WindowInsets.safeDrawing.asPaddingValues()
                    .calculateEndPadding(layoutDirection)
            )
    ){
        TopicList(
            topicList = Datasource().loadTopics(),
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopicCard(
    topic: Topic,
    modifier: Modifier = Modifier
){
        Card(modifier = modifier) {
            Row( modifier = Modifier
                .height(68.dp),
            ) {
            Image(
                painter = painterResource(id = topic.subjectImageId),
                contentDescription = stringResource(id = topic.subjectId),
                modifier = Modifier
                    .width(68.dp)
                    .fillMaxHeight(),
                contentScale = ContentScale.Crop
            )

                Column{
                        Text(
                            text = stringResource(id = topic.subjectId),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(
                                start = 16.dp,
                                top = 16.dp,
                                end = 16.dp,
                                bottom = 8.dp),
                        )
                    Row (
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                    ){
                        Icon(
                            painter = painterResource(id = R.drawable.dots),
                            contentDescription = null,
                            modifier = Modifier.padding(
                                start = 16.dp,
                                end = 8.dp)
                        )
                        Text(
                            text = topic.count.toString(),
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
        }
    }
}

@Composable
fun TopicList(topicList: List<Topic>, modifier: Modifier = Modifier){
    /*
    Similar to LazyColumn (Lazy columns can only display one item per row.
    LazyVerticalGrid can display multiple items per row. Requires columns parameter
     */
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp),
        modifier = modifier
    ){
        items(topicList){ topic ->
            TopicCard(topic = topic, modifier = Modifier.padding(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopicCardPreview() {
    BuildAGridTheme {
        val topic = Topic(
            R.string.architecture, 58, R.drawable.architecture
        )
        TopicCard(topic = topic,
            modifier = Modifier.padding(8.dp)
        )
    }
}