package com.example.stocksapp.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stocksapp.R
import com.example.stocksapp.model.Stock
import com.example.stocksapp.ui.theme.Blue
import com.example.stocksapp.model.News
import com.example.stocksapp.ui.theme.SheetBackgroundDark
import com.example.stocksapp.ui.theme.SheetBackgroundLight


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {

    // State for maintaining the Stock List
    val stockList = remember { mutableStateListOf<Stock>() }

    // State for visibility of "Add Stock" layout
    var isPlusLayoutVisible by remember { mutableStateOf(false) }

    // News List
    val newsList = mutableListOf<News>()
    createNewsList(newsList)


    // Screen Configuration for bottom sheet
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp

    // State for handling the sheet expansion
    var expandedType by remember {
        mutableStateOf(ExpandedType.HALF)
    }

    val scaffoldState = rememberBottomSheetScaffoldState()

    // Calculate the bottom sheet height based on the expanded type
    val height by animateIntAsState(
        when (expandedType) {
            ExpandedType.HALF -> screenHeight / 2       // Half of the screen height
            ExpandedType.FULL -> screenHeight * 9 / 10  // 90% of the screen height
            ExpandedType.COLLAPSED -> 100               // Fixed size : 100.dp
        }
    )

    //Bottom Sheet
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContainerColor = if (!isSystemInDarkTheme())
            SheetBackgroundLight
        else
            SheetBackgroundDark,
        sheetDragHandle = null,
        sheetContent = {
            var isUpdated = false

            //BottomSheet Drag Handle
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(height.dp)
                    .pointerInput(Unit) {
                        detectVerticalDragGestures(
                            onVerticalDrag = { change, dragAmount ->
                                change.consume()

                                if (!isUpdated) {
                                    expandedType = when {
                                        //Swipe Up
                                        dragAmount < 0 && expandedType == ExpandedType.COLLAPSED -> {
                                            ExpandedType.HALF
                                        }

                                        //Swipe Up
                                        dragAmount < 0 && expandedType == ExpandedType.HALF -> {
                                            ExpandedType.FULL
                                        }

                                        //Swipe Down
                                        dragAmount > 0 && expandedType == ExpandedType.FULL -> {
                                            ExpandedType.HALF
                                        }

                                        //Swipe Down
                                        dragAmount > 0 && expandedType == ExpandedType.HALF -> {
                                            ExpandedType.COLLAPSED
                                        }

                                        //Swipe Down
                                        dragAmount > 0 && expandedType == ExpandedType.COLLAPSED -> {
                                            ExpandedType.COLLAPSED
                                        }

                                        else -> {
                                            ExpandedType.FULL
                                        }
                                    }
                                    isUpdated = true
                                }
                            },
                            onDragEnd = {
                                isUpdated = false

                            }
                        )
                    }
            )
            {
                BottomSheetContent(newsList)
            }

        },
        sheetPeekHeight = height.dp, //initial sheet height
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
    ) {

        Scaffold(topBar = {
            TopAppBar(title = {
                Column {
                    Text(text = "Stocks", style = MaterialTheme.typography.titleLarge)
                    Text(
                        text = "September 12",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }, actions =
            {
                Row {
                    //Add Icon
                    IconButton(onClick = {
                        isPlusLayoutVisible = true
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add",
                            Modifier
                                .background(
                                    color = if (!isSystemInDarkTheme()) SheetBackgroundLight else SheetBackgroundDark,
                                    shape = CircleShape
                                )
                                .padding(8.dp),
                            tint = if (!isSystemInDarkTheme()) Color.Black else Color.White
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

                //Animate the plus layout visibility
                AnimatedVisibility(isPlusLayoutVisible) {

                    AddStockScreen(onStockAdded = { stock ->
                        stockList.add(stock)
                        isPlusLayoutVisible = !isPlusLayoutVisible
                    })

                }
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(text = "My Symbols", color = Blue, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_up_down),
                        contentDescription = "Up Down",
                        tint = Blue

                    )

                }

                Spacer(modifier = Modifier.height(8.dp))


                // List of stock
                LazyColumn(state = rememberLazyListState()) {
                    itemsIndexed(
                        items = stockList,
                        key = { _: Int, item: Stock -> item.id }) { _: Int, item: Stock ->

                        val dismissState =
                            rememberDismissState(confirmValueChange = {
                                if (it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd)
                                    stockList.remove(item)
                                true
                            })

                        //Adding swipe to dismiss feature
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
            .padding(8.dp)

    ) {

        Box(
            modifier = Modifier
                .width(20.dp)
                .height(4.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(2.dp))
                .padding(8.dp)
                .align(Alignment.CenterHorizontally),

            )

        Spacer(Modifier.height(16.dp))

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

fun createNewsList(newsList: MutableList<News>) {
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


}

//Bottom sheet with 3 states
enum class ExpandedType {
    HALF, FULL, COLLAPSED
}

@Preview
@Composable
fun XYZPreview() {
    HomeScreen()
}

