package com.example.stocksapp.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stocksapp.R
import com.example.stocksapp.model.Stock
import com.example.stocksapp.ui.theme.blue
import com.example.stocksapp.model.News
import com.example.stocksapp.ui.theme.SheetBackground
import com.example.stocksapp.ui.theme.newsBackground
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {

    val stockList = remember { mutableStateListOf<Stock>() }
    var isPlusLayoutVisible by remember { mutableStateOf(false) }
    val scaffoldState = rememberBottomSheetScaffoldState()

    val newsList = mutableListOf<News>()

    newsList.add(
        News(
            "Times Of India",
            "Indian G-20 ",
            R.drawable.test,
            "3 days ago",
            "Author"
        )
    )
    newsList.add(
        News(
            "Times Of India",
            "Indian G-20 Summit held successfully in Delhi",
            R.drawable.test,
            "3 days ago",
            "Author"
        )
    )

    newsList.add(
        News(
            "Times Of India",
            "Indian G-20 Summit held successfully in Delhi",
            R.drawable.test,
            "3 days ago",
            "Author"
        )
    )

    newsList.add(
        News(
            "Times Of India",
            "Indian G-20 Summit held successfully in Delhi",
            R.drawable.test,
            "3 days ago",
            "Author"
        )
    )




    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContainerColor = SheetBackground,
        sheetContent = {
            BottomSheetContent(newsList)

        },
        sheetPeekHeight = 100.dp, //initial sheet height
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .fillMaxWidth()
    ) {

        Scaffold(topBar = {
            TopAppBar(title = {
                Column {
                    Text(text = "Stocks", style = MaterialTheme.typography.titleLarge)
                    Text(
                        text = "September 12",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.DarkGray
                    )
                }
            }, actions =
            {
                Row {
                    IconButton(onClick = {
                        isPlusLayoutVisible = true
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add",
                            Modifier
                                .background(color = newsBackground, shape = CircleShape)
                                .padding(8.dp)
                        )
                    }
                }
            }
            )

        }, content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {

                Spacer(modifier = Modifier.height(4.dp))

                if (isPlusLayoutVisible)
                    AddStockScreen(onStockAdded = {stock->
                        stockList.add(stock)
                        isPlusLayoutVisible=!isPlusLayoutVisible
                    })

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "My Symbols", color = blue, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_up_down),
                        contentDescription = "Up Down",
                        tint = blue

                    )

                }

                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(state = rememberLazyListState()) {

                    itemsIndexed(
                        items = stockList,
                        key = { index: Int, item: Stock -> item.id }) { index: Int, item: Stock ->

                        val dismissState =
                            rememberDismissState(confirmValueChange = {
                                if (it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd)
                                    stockList.remove(item)
                                true
                            })
                        SwipeToDismiss(
                            state = dismissState,
                            background = {
                                Box(
                                    Modifier
                                        .fillMaxSize()
                                        .background(Color.Transparent)
                                )
                            },
                            dismissContent = {
                                ItemStock(stock = item)
                            },
                            directions = setOf(
                                DismissDirection.StartToEnd,
                                DismissDirection.EndToStart
                            )
                        )
                    }
                }
            }
        })
    }
}

@Composable
fun BottomSheetContent(newsList: List<News>) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .fillMaxHeight(0.9f)
    ) {
        Text(text = "Top Stories", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Text(text = "From News", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)

        Spacer(Modifier.height(16.dp))

        LazyColumn {
            items(newsList.size) {
                ItemNews(news = newsList[it])
            }
        }
    }
}

