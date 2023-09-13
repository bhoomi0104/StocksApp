package com.example.stocksapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stocksapp.R
import com.example.stocksapp.model.News
import com.example.stocksapp.ui.theme.CardBackgroundDark
import com.example.stocksapp.ui.theme.CardBackgroundLight


@Composable
fun ItemNews(news: News) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (!isSystemInDarkTheme()) CardBackgroundLight else CardBackgroundDark
        )
    ) {

        Column(modifier = Modifier.padding(vertical = 8.dp)) {


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier.weight(1f)
                ) {

                    Column {
                        Text(text = news.newsPaper, fontWeight = FontWeight.Bold, maxLines = 1)
                        Text(text = news.title, fontWeight = FontWeight.Normal, maxLines = 3)
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.test),
                    contentDescription = "",
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(8.dp)
                        )
                        .size(120.dp)
                )
            }

            Divider(
                thickness = 0.5.dp,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = news.time, fontSize = 14.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = news.author, fontSize = 14.sp)

                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    Icons.Filled.MoreVert,
                    contentDescription = "Up Down",
                    tint = if (!isSystemInDarkTheme()) Color.Black else Color.White,
                    modifier = Modifier.rotate(90f)
                )
            }
        }
    }

}

