package dev.dwikiy.ace_combat_gallery

import androidx.compose.animation.core.copy
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.style.lerp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import kotlin.math.absoluteValue

// In HomeScreen.kt (or a new SampleData.kt file)
val aircraftList = listOf(
    Aircraft(
        name = "F-22A Raptor",
        nickname = "The Apex Predator",
        imageRes = R.drawable.f22_raptor,
        description = "An American single-seat, twin-engine, all-weather stealth tactical fighter aircraft developed for the United States Air Force."
    ),
    Aircraft(
        name = "Su-57 Felon",
        nickname = "The Ghost of Razgriz",
        imageRes = R.drawable.su57_felon,
        description = "A Russian single-seat, twin-engine stealth multirole fighter aircraft developed for air superiority and attack operations."
    ),
    Aircraft(
        name = "X-02S Strike Wyvern",
        nickname = "The Unsung War Hero",
        imageRes = R.drawable.x02s_strikewyvern,
        description = "An experimental stealth fighter aircraft known for its unique variable-geometry wings and exceptional performance in all flight regimes."
    )
)

//@Composable
//fun HomeScreen () {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.Black)
//            .padding(16.dp),
//        contentAlignment = Alignment.Center
//
//    ) {
//        SleekAudioPlayer()
//    }
//}

@Composable
fun AircraftCard(aircraft: Aircraft, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16f / 10f),
        shape = RoundedCornerShape(24.dp)
    ) {
        Box {
            Image(
                painter = painterResource(id = aircraft.imageRes),
                contentDescription = aircraft.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                            startY = 400f
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = aircraft.name,
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )
                Text(
                    text = aircraft.nickname,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White.copy(alpha = 0.8f)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = aircraft.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.7f),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AircraftPager(modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(pageCount = { aircraftList.size })

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 32.dp),
            pageSpacing = 16.dp
        ) { page ->
            val aircraft = aircraftList[page]
            AircraftCard(
                aircraft = aircraft,
                modifier = Modifier.graphicsLayer {
                    val pageOffset = ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
                    alpha = lerp(start = 0.5f, stop = 1f, fraction = 1f - pageOffset.coerceIn(0f, 1f))
                    scaleY =
                        lerp(start = 0.8f, stop = 1f, fraction = 1f - pageOffset.coerceIn(0f, 1f))
                }
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color = if (pagerState.currentPage == iteration) Color.White else Color.Gray
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(10.dp)
                )
            }
        }
    }
}

@Composable
fun HomeScreen() {
    LaunchedEffect(Unit) {
        ExoPlayerManager.stop()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // The pager will take up most of the space
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            AircraftPager()
        }

        // The audio player will be at the bottom
        SleekAudioPlayer()
    }
}