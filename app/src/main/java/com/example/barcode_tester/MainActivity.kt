package com.example.barcode_tester

import android.app.DatePickerDialog
import android.graphics.Camera
import android.os.Bundle
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.sharp.DateRange
import androidx.compose.material.icons.sharp.KeyboardArrowDown
import androidx.compose.material.icons.sharp.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.barcode_tester.ui.theme.BarcodetesterTheme
import java.util.Calendar
import java.util.Date

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BarcodetesterTheme {
                var showCamera by remember { mutableStateOf(false) }

                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column() {
                        if (showCamera) {
                            Camera(Modifier.weight(2f))
                        } else {
                            Surface(
                                color = MaterialTheme.colorScheme.inverseSurface,
                                modifier = Modifier.weight(2f)
                            ) {
                                CameraPrompt(onContinueClicked = {showCamera = false})
                            }
                        }
                        Fields(Modifier.weight(1f))
                    }
                }
            }
        }
    }
}


@Composable
fun CameraPrompt(onContinueClicked: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text("Access Camera")
        }
    }
}

@Composable
fun Camera(modifier: Modifier) {
    // holding off, will research MLKit's barcode scanner
    // https://developers.google.com/ml-kit/vision/barcode-scanning/android
}

@Composable
fun Fields(modifier: Modifier) {
    Surface (
        modifier = modifier.fillMaxSize()
    ) {
        var fieldList = listOf("Product Name", "Category", "Brand")
        Column(
            modifier = modifier.padding(vertical = 4.dp)
        ) {
            for (field in fieldList) {
                TextBox(field)
            }
            Row(horizontalArrangement = Arrangement.End) {
                ExpirationDate()
                Quantity()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextBox(label: String) {
    var text by remember { mutableStateOf(label) }
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = {text = it},
        label = { Text(label) }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpirationDate() {
    val calendar = Calendar.getInstance()

    val context = LocalContext.current
    val mYear = calendar.get(Calendar.YEAR)
    val mMonth = calendar.get(Calendar.MONTH)
    val mDay = calendar.get(Calendar.DAY_OF_MONTH)

    var date by remember { mutableStateOf("") }

    val mDatePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            date = "${mMonth+1}/$mDayOfMonth/$mYear"
        }, mYear, mMonth, mDay
    )

    Button(
        onClick = { mDatePickerDialog.show() },
        colors = ButtonDefaults.buttonColors(Color(0XFF0F9D58)),
    ) {
        Icon(Icons.Sharp.DateRange, contentDescription = "Localized description")
    }

    Surface(
        modifier = Modifier.defaultMinSize(100.dp).padding(0.dp,0.dp,5.dp,0.dp)
    ) {
        TextField(
            value = date,
            onValueChange = {date = it},
            label = {Text("Expiration Date")}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Quantity() {
    var quantity by remember { mutableStateOf("0") }

    TextField(
        value = quantity,
        onValueChange = {quantity = it.toString()},
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        label = { Text("Quantity")}
    )

    Column() {
        Button(
            onClick = {quantity = (quantity.toInt()+1).toString()}
        ) {
            Icon(Icons.Sharp.KeyboardArrowUp, contentDescription = "Increment")
        }

        Button(
            onClick = {quantity = (quantity.toInt()+1).toString()}
        ) {
            Icon(Icons.Sharp.KeyboardArrowDown, contentDescription = "Decrement")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FieldsPreview() {
    BarcodetesterTheme {
        var showCamera by remember { mutableStateOf(false) }

        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column() {
                if (showCamera) {
                    Camera(Modifier.weight(2f))
                } else {
                    Surface(
                        color = MaterialTheme.colorScheme.inverseSurface,
                        modifier = Modifier.weight(2f)
                    ) {
                        CameraPrompt(onContinueClicked = {showCamera = false})
                    }
                }
                Fields(Modifier.weight(1f))
            }
        }
    }
}