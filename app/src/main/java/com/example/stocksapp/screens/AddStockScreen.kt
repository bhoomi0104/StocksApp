package com.example.stocksapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stocksapp.model.Stock
import kotlin.random.Random

@Composable
fun AddStockScreen(onStockAdded: (Stock) -> Unit) {
    var shortName by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var value by remember { mutableStateOf("") }
    var change by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        TextField(
            value = shortName,
            onValueChange = { shortName = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            placeholder = { Text("Short Name") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),

            )

        TextField(
            value = fullName,
            onValueChange = { fullName = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            placeholder = { Text("Full Name") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
        )

        TextField(
            value = value,
            onValueChange = { value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            ),
            placeholder = { Text("Value") }
        )

        TextField(
            value = change,
            onValueChange = { change = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            placeholder = { Text("Change") },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            ),

            )

        Button(
            onClick = {
                // Convert value and change to Float
                val floatValue = value.toFloatOrNull()
                val changeValue = change.toFloatOrNull()

                // Check if conversion was successful
                if (floatValue != null && changeValue != null) {
                    // Create a Stock object with a generated ID
                    val newStock = Stock(
                        id = Random.nextInt(), // You can generate a unique ID as needed
                        name = shortName,
                        fullName = fullName,
                        value = floatValue,
                        change = changeValue
                    )

                    // Pass the new Stock object to the callback function
                    onStockAdded(newStock)

                    // Clear input fields
                    shortName = ""
                    fullName = ""
                    value = ""
                    change = ""
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            Text("Add Stock")
        }
    }

}

@Preview
@Composable
fun PreviewCom() {
    AddStockScreen(onStockAdded = {})
}