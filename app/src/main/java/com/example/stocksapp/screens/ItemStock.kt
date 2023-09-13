package com.example.stocksapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stocksapp.model.Stock

@Composable
fun ItemStock(stock: Stock) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = stock.name,
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
            )
            Text(text = stock.fullName, style = TextStyle(fontSize = 14.sp), color = Color.Gray)
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = stock.value.toString(),
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                textAlign = TextAlign.End
            )

            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        Color.Green
                    )

            )
            {
                Text(
                    text = stock.change.toString(),
                    style = TextStyle(fontSize = 14.sp),
                    color = Color.White,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(horizontal = 4.dp)
                )
            }
        }


    }
}


//@Preview(showBackground = true)
@Composable
fun PreviewH() {
    ItemStock(stock = Stock(0, "", "", 45f, 55552f))
}


