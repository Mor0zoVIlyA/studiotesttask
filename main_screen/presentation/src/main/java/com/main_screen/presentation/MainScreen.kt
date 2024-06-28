package com.main_screen.presentation

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.compose.rememberAsyncImagePainter
import com.main_screen.domain.model.NetworkRequestItem
import com.main_screen.domain.model.SubCategory


@Composable
fun shimmerBrush(showShimmer: Boolean = true, targetValue: Float = 1000f): Brush {
    return if (showShimmer) {
        val shimmerColors = listOf(
            Color.LightGray.copy(alpha = 0.6f),
            Color.LightGray.copy(alpha = 0.2f),
            Color.LightGray.copy(alpha = 0.6f),
        )

        val transition = rememberInfiniteTransition(label = "")
        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = targetValue,
            animationSpec = infiniteRepeatable(
                animation = tween(800), repeatMode = RepeatMode.Reverse
            ), label = ""
        )
        Brush.linearGradient(
            colors = shimmerColors,
            start = Offset.Zero,
            end = Offset(x = translateAnimation.value, y = translateAnimation.value)
        )
    } else {
        Brush.linearGradient(
            colors = listOf(Color.Transparent, Color.Transparent),
            start = Offset.Zero,
            end = Offset.Zero
        )
    }
}

@Composable
fun NetworkRequestTreeView(
    items: List<NetworkRequestItem>,
    onLastItemClick: (SubCategory) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(items) { item ->
            TreeViewItem(item = item, onLastItemClick = onLastItemClick)
        }
    }
}

@Composable
fun TreeViewItem(item: NetworkRequestItem, depth: Int = 0, onLastItemClick: (SubCategory) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = (depth * 16).dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(8.dp)
        ) {
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowRight,
                contentDescription = if (expanded) "Collapse" else "Expand"
            )
            Image(
                painter = rememberAsyncImagePainter(item.icon),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 16.dp)
            )
            Text(
                text = item.title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
        }
        if (item.subCategories.isNotEmpty()) {
            AnimatedVisibility(visible = expanded) {
                Column {
                    item.subCategories.forEach { subCategory ->
                        SubCategoryItem(subCategory, depth + 1, onLastItemClick)
                    }
                }
            }
        }
    }
}

@Composable
fun SubCategoryItem(item: SubCategory, depth: Int, onLastItemClick: (SubCategory) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val showShimmer = remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = (depth * 16).dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    if (item.subCategories.isEmpty()) {
                        onLastItemClick(item)
                    } else {
                        expanded = !expanded
                    }
                }
                .padding(8.dp)
        ) {
            if (item.subCategories.isNotEmpty()) {
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowRight,
                    contentDescription = if (expanded) "Collapse" else "Expand"
                )
            }
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(shimmerBrush(targetValue = 1300f, showShimmer = showShimmer.value))
            ) {
                SubcomposeAsyncImage(
                    model = item.icon,
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Inside,
                    modifier = Modifier.matchParentSize()
                ) {
                    val state = painter.state
                    if (state is AsyncImagePainter.State.Success) {
                        showShimmer.value = false
                        SubcomposeAsyncImageContent()
                    }
                }
            }
            Text(
                text = item.title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )

        }
        if (item.subCategories.isNotEmpty()) {
            AnimatedVisibility(visible = expanded) {
                Column {
                    item.subCategories.forEach { subCategory ->
                        SubCategoryItem(subCategory, depth + 1, onLastItemClick)
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    sampleData: List<NetworkRequestItem>,
    navController: NavController
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.catalog)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
            )
        },
        content = { padding ->
            Column(modifier = Modifier.padding(padding)) {
                NetworkRequestTreeView(items = sampleData){ item ->
                    Log.d("TAG", "MainScreen: $item")
                    navController.navigate("second_layer/${item.slug}/${item.title}")
                }
            }
        }
    )
}