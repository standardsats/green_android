package io.crypto.feature.settings.presentation.screens.store

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import io.crypto.feature.registration.R
import io.crypto.feature.settings.navigation.AppScreen

class StoreScreen : AppScreen() {
    @Composable
    override fun Content() {
        StoreScreenContent()
    }

    @Composable
    private fun StoreScreenContent() {

        val viewModel = getViewModel<StoreScreenViewModel>()
        val phraseList: List<PhraseStoreData> = arrayListOf(
            PhraseStoreData("racall"),
            PhraseStoreData("fit"),
            PhraseStoreData("stuff"),
            PhraseStoreData("flavor"),
            PhraseStoreData("august"),
            PhraseStoreData("loyal"),
            PhraseStoreData("racall"),
            PhraseStoreData("fit"),
            PhraseStoreData("stuff"),
            PhraseStoreData("flavor"),
            PhraseStoreData("august"),
            PhraseStoreData("loyal"),
        )
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFAFBFC))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFAFBFC)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = { viewModel.back() },
                    modifier = Modifier.align(Alignment.Start)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.height(64.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "Store the phrase in a safe place and keep it secret",
                        style = TextStyle(
                            fontSize = 25.sp,
                            lineHeight = 30.sp,
                            fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF09282D),
                            textAlign = TextAlign.Center,
                        )
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        text = "If you lose your recovery immediately, you will have to reissue your wallet at the nearest post office",
                        style = TextStyle(
                            fontSize = 18.sp,
                            lineHeight = 30.sp,
                            fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF09282D),
                            textAlign = TextAlign.Center,
                        )
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
                /* LazyVerticalGrid(
                     columns = GridCells.Fixed(2), modifier = Modifier
                         .fillMaxWidth()
                         .clip(RoundedCornerShape(topEnd = 32.dp, topStart = 32.dp))
                         .background(Color.White)
                         .padding(horizontal = 16.dp)
                         .padding(start = 16.dp, end = 16.dp)
                 ) {
                     items(10) {
                         Row() {

                             Text(
                                 text = "${it + 1}",
                                 style = TextStyle(
                                     fontSize = 25.sp,
                                     lineHeight = 30.sp,
                                     fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                                     fontWeight = FontWeight(400),
                                     color = Color(0xFF1ABACF),
                                 )
                             )
                             Text(
                                 text = " recall",
                                 style = TextStyle(
                                     fontSize = 25.sp,
                                     lineHeight = 30.sp,
                                     fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                                     fontWeight = FontWeight(400),
                                     color = Color(0xFF09282D),
                                 )
                             )
                         }
                     }
                 }*/


                var i = 0
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                        .background(Color.White)
                        .padding(horizontal = 52.dp, vertical = 32.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .width(0.dp)
                            .weight(1f)
                    ) {
                        items(phraseList.subList(0, phraseList.size / 2)) {
                            Row(modifier = Modifier.padding(vertical = 12.dp)) {
                                Text(
                                    text = "${++i}.",
                                    style = TextStyle(
                                        fontSize = 25.sp,
                                        lineHeight = 30.sp,
                                        fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFF1ABACF),
                                    )
                                )
                                Text(
                                    text = " ${it.name}",
                                    style = TextStyle(
                                        fontSize = 25.sp,
                                        lineHeight = 30.sp,
                                        fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFF09282D),
                                    )
                                )
                            }
                        }
                    }
                    LazyColumn(
                        modifier = Modifier
                            .width(0.dp)
                            .weight(1f)
                    ) {
                        items(phraseList.subList(phraseList.size / 2, phraseList.size)) {
                            Row(modifier = Modifier.padding(vertical = 12.dp)) {
                                Text(
                                    text = "${++i}.",
                                    style = TextStyle(
                                        fontSize = 25.sp,
                                        lineHeight = 30.sp,
                                        fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFF1ABACF),
                                    )
                                )
                                Text(
                                    text = " ${it.name}",
                                    style = TextStyle(
                                        fontSize = 25.sp,
                                        lineHeight = 30.sp,
                                        fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFF09282D),
                                    )
                                )

                            }
                        }
                    }

                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(0.dp)
                        .weight(1f)
                        .background(Color.White)
                )
            }
        }
    }
}

data class PhraseStoreData(val name: String)