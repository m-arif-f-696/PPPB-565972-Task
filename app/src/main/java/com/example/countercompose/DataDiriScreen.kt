package com.example.countercompose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.countercompose.ui.theme.CounterComposeTheme

@Composable
fun DataDiriApp(
    modifier: Modifier = Modifier,
    viewModel: CounterViewModel = viewModel()
) {
    val counter by viewModel.counter
    val namaInput by viewModel.namaInput
    val savedName by viewModel.savedName

    DataDiriScreen(
        counter = counter,
        namaInput = namaInput,
        savedName = savedName,
        onIncrement = { viewModel.increment() },
        onDecrement = { viewModel.decrement() },
        onReset = { viewModel.reset() },
        onNamaChange = { viewModel.onNamaChange(it) },
        onSaveNama = { viewModel.saveNama() },
        modifier = modifier
    )
}

@Composable
fun DataDiriScreen(
    counter: Int,
    namaInput: String,
    savedName: String,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onReset: () -> Unit,
    onNamaChange: (String) -> Unit,
    onSaveNama: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Counter App",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Area Counter + Tampilan Nama di bawah angka counter
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(
                    color = Color(0xFFF5F5F5),
                    shape = RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Nilai Counter",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 16.dp)
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = counter.toString(),
                    fontSize = 100.sp,
                    fontWeight = FontWeight.Bold
                )
                if (savedName.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Nama: $savedName",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF333333)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tombol KURANG, TAMBAH, dan RESET (re-use komponen dari CounterScreen.kt)
        CounterButtons(
            onDecrement = onDecrement,
            onIncrement = onIncrement,
            onReset = onReset
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 1. Form input "Nama" di bawah button Reset
        OutlinedTextField(
            value = namaInput,
            onValueChange = onNamaChange,
            label = { Text("Nama") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 2. Button "Simpan" di bawah form input "Nama"
        Button(
            onClick = onSaveNama,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Simpan")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DataDiriScreenPreview() {
    CounterComposeTheme {
        DataDiriScreen(
            counter = 5,
            namaInput = "Muhamad Arif Farhan",
            savedName = "Muhamad Arif Farhan",
            onIncrement = {},
            onDecrement = {},
            onReset = {},
            onNamaChange = {},
            onSaveNama = {}
        )
    }
}