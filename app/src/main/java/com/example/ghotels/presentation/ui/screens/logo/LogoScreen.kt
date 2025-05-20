package com.example.ghotels.presentation.ui.screens.logo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ghotels.R

@Composable
fun LogoScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F203E)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo5),
            contentDescription = "GHotels Logo",
            modifier = Modifier.size(250.dp),
            contentScale = ContentScale.Fit
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenPreview() {
    LogoScreen()
}