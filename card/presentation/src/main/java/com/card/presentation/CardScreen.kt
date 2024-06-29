package com.card.presentation

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.card.domain.DomainGoodInfo
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProductScreen(screenState: DomainGoodInfo, share: (String, String) -> Unit, navController: NavController) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Top icons (back and share)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            IconButton(onClick = { share(screenState.title, screenState.sku) }) {
                Icon(Icons.Default.Share, contentDescription = "Share")
            }
        }

        // Image pager
        HorizontalPager(
            count = screenState.imageUrlList.size, // Replace with actual image count
            state = pagerState,
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()
        ) { page ->
            AsyncImage(
                model = screenState.imageUrlList[page], // Replace with actual image URL
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }

        // Pager indicator
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(screenState.imageUrlList.size) { page ->
                val color = if (pagerState.currentPage == page) Color.Black else Color.Gray
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .padding(2.dp)
                        .background(color, shape = CircleShape)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (screenState.salePercentage>0)
            Text(
                text = "-${screenState.salePercentage}%",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(4.dp)
            )
        Text(
            text = "Арт. ${screenState.sku}",
            color = Color.Gray,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(4.dp)
        )
        Text(
            text = screenState.title,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(4.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))


        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "${screenState.price} ₽/${screenState.units}.",
                color = Color.Black,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(4.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            if (screenState.salePercentage>0){
                Box{

                    Canvas(modifier = Modifier.matchParentSize()) {
                        val width = size.width
                        val height = size.height

                        drawLine(
                            color = Color.Black,
                            start = androidx.compose.ui.geometry.Offset(0f, height),
                            end = androidx.compose.ui.geometry.Offset(width, 0f),
                            strokeWidth = 4f
                        )
                    }
                    Text(
                        text = "${screenState.priceOld} ₽/${screenState.units}.",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(4.dp)
                    )
                }

            }
        }
    }
}
