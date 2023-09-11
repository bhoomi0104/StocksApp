package com.example.stocksapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stocksapp.model.Stock
import com.example.stocksapp.ui.theme.blue
import com.example.stocksapp.model.News
import com.example.stocksapp.ui.theme.SheetBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val stockList = mutableListOf<Stock>()

    val newsList = mutableListOf<News>()
    val scaffoldState = rememberBottomSheetScaffoldState()

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


    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 100.dp, //initial sheet height
        sheetContainerColor = SheetBackground,
        sheetContent = {
            BottomSheetContent(newsList)
        },
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        mainContent(stockList, newsList)
    }

}

@Composable
fun mainContent(stockList: MutableList<Stock>, newsList: MutableList<News>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = "Stocks", style = MaterialTheme.typography.titleLarge)

                Text(
                    text = "September 12",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.DarkGray
                )
            }


            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(Color.LightGray, shape = CircleShape)
                    .padding(4.dp), // Adjust the padding as needed
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.MoreVert,
                    contentDescription = "Up Down",
                    tint = blue,
                    modifier = Modifier.rotate(90f)
                )
            }

        }


        Spacer(modifier = Modifier.height(4.dp))

        CustomSearchBar(searchQuery = "", onSearchQueryChange = {})

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


        stockList.add(Stock("Abc", "ABC CDH", 15263f, 5f))
        stockList.add(Stock("Abc", "ABC CDH", 15263f, 5f))
        stockList.add(Stock("Abc", "ABC CDH", 15263f, 5f))


        LazyColumn {
            items(stockList.size) {
                ItemStock(stock = stockList[it])
            }
        }


    }

}

@Composable
fun BottomSheetContent(newsList: List<News>) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
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

@Composable
fun CustomSearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {

    OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        leadingIcon = {
            Icon(
                Icons.Filled.Search,
                contentDescription = null,
            )
        },
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_voice),
                contentDescription = ""
            )
        },
        placeholder = {
            Text(text = "Search")
        },
//        textStyle = LocalTextStyle.current.copy(color = Color.Gray),
        colors = TextFieldDefaults.colors(
            disabledContainerColor = Color.Transparent,
            focusedTextColor = Color.Black,
            cursorColor = Color.Black, // Change the cursor color as needed
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                // Perform the search action here
            }
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp), // Adjust padding as needed
        shape = RoundedCornerShape(25.dp)
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewMainScreen() {
    HomeScreen()
}