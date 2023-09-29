package io.crypto.feature.settings.presentation.screens.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import io.crypto.feature.registration.R
import io.crypto.feature.settings.data.model.CategoryData
import io.crypto.feature.settings.navigation.AppScreen
import io.crypto.feature.settings.presentation.components.TransactionItem
import io.crypto.feature.settings.utils.mainColor

class MainScreen : AppScreen() {
    @Composable
    override fun Content() {

        MainScreenContent()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun MainScreenContent() {

        val viewModel = getViewModel<MainScreenViewModel>()
        var isVisible by remember {
            mutableStateOf(true)
        }

        val interaction = remember {
            MutableInteractionSource()
        }


        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFAFBFC))
        ) {
            Scaffold(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.padding(it)) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.TopCenter)
                            .height(0.dp)
                    ) {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                                .background(Color.White)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.White)
                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(onClick = {
                                    //todo
                                }) {

                                    Image(
                                        painter = painterResource(id = R.drawable.ic_person),
                                        contentDescription = null
                                    )
                                }

                                Text(
                                    text = "Thai digital wallet",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        lineHeight = 50.sp,
                                        fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFFA4B4CB),
                                        textAlign = TextAlign.Center,
                                    )
                                )

                                Row(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0xFFFAFBFC))
                                        .padding(horizontal = 14.dp, vertical = 5.dp)
                                        .clickable(
                                            interactionSource = interaction,
                                            indication = null
                                        ) {
                                            //todo
                                        },
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_question_mark),
                                        contentDescription = null
                                    )
                                    Text(
                                        text = "FAQ",
                                        style = TextStyle(
                                            fontSize = 15.sp,
                                            lineHeight = 50.sp,
                                            fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFFA4B4CB),
                                            textAlign = TextAlign.Center,
                                        )
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(32.dp))
                            IconButton(
                                onClick = { isVisible = !isVisible },
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            ) {
                                Image(
                                    painter = painterResource(id = if (!isVisible) R.drawable.ic_eye else R.drawable.ic_hide_eye),
                                    contentDescription = null
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = if (isVisible) "10,000" else "******",
                                    style = TextStyle(
                                        fontSize = 40.sp,
                                        fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFF09282D),
                                        textAlign = TextAlign.Center,
                                    )
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "ETHB",
                                    style = TextStyle(
                                        fontSize = 32.sp,
                                        fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                                        fontWeight = FontWeight(400),
                                        color = mainColor,
                                        textAlign = TextAlign.Center,
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.height(30.dp))
                        }


                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            text = "Latest transactions",
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFF09282D),
                            )
                        )
                        Row(
                            modifier = Modifier.padding(
                                top = 12.dp,
                                bottom = 4.dp,
                                start = 16.dp,
                                end = 16.dp
                            )
                        ) {
                            CategorySpinner()
                            Spacer(modifier = Modifier.width(16.dp))
                            StatusSpinner()
                            Spacer(modifier = Modifier.width(16.dp))
                            Row(
                                modifier = Modifier
                                    .width(0.dp)
                                    .weight(1f)

                                    .clip(RoundedCornerShape(8.dp))
                                    .border(
                                        2.dp,
                                        color = Color.Gray,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    modifier = Modifier.padding(start = 16.dp),
                                    painter = painterResource(id = R.drawable.ic_calendar),
                                    contentDescription = null
                                )
                                Text(
                                    modifier = Modifier.padding(start = 16.dp),
                                    text = "Date",
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        lineHeight = 50.sp,
                                        fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFFA4B4CB),
                                        textAlign = TextAlign.Center,
                                    )
                                )
                            }
                        }

                        LazyColumn {
                            item {
                                Spacer(modifier = Modifier.height(16.dp))
                            }

                            item {
                                Text(
                                    modifier = Modifier.padding(start = 16.dp),
                                    text = "Today",
                                    style = TextStyle(
                                        fontSize = 15.sp,
                                        lineHeight = 50.sp,
                                        fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFF09282D),
                                    )
                                )
                            }
                            items(10) {
                                Box(
                                    modifier = Modifier.padding(
                                        horizontal = 24.dp,
                                        vertical = 8.dp
                                    )
                                ) {

                                    TransactionItem(
                                        "Merchant name",
                                        "category name",
                                        "-100 eTHB",
                                        ""
                                    )
                                }
                            }
                            item {
                                Spacer(modifier = Modifier.height(68.dp))
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .height(66.dp)
                            .padding(horizontal = 4.dp)
                            .clip(RoundedCornerShape(topEnd = 32.dp, topStart = 32.dp))
                            .background(Color.White)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.Center),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Spacer(
                                modifier = Modifier
                                    .width(0.dp)
                                    .weight(3f)
                            )
                            Image(
                                modifier = Modifier.clickable(
                                    interactionSource = interaction,
                                    indication = null
                                ) { viewModel.openSettingsScreen() },
                                painter = painterResource(id = R.drawable.ic_settings),
                                contentDescription = null
                            )
                            Spacer(
                                modifier = Modifier
                                    .width(0.dp)
                                    .weight(2f)
                            )
                            Box(
                                modifier = Modifier
                                    .border(width = 1.dp, color = Color(0xFFDDE3F3))
                                    .padding(1.dp)
                                    .width(0.dp)
                                    .height(30.5.dp)
                            )
                            Spacer(
                                modifier = Modifier
                                    .width(0.dp)
                                    .weight(2f)
                            )
                            Row(
                                modifier = Modifier.clickable(
                                    interactionSource = interaction,
                                    indication = null
                                ) { viewModel.openSendScreen() },
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Image(
                                    painter = painterResource(id = R.drawable.ic_send),
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "send",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        lineHeight = 50.sp,
                                        fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFF09282D),
                                    )
                                )
                            }
                            Spacer(
                                modifier = Modifier
                                    .width(0.dp)
                                    .weight(2f)
                            )
                            Box(
                                modifier = Modifier
                                    .border(width = 1.dp, color = Color(0xFFDDE3F3))
                                    .padding(1.dp)
                                    .width(0.dp)
                                    .height(30.5.dp)
                            )
                            Spacer(
                                modifier = Modifier
                                    .width(0.dp)
                                    .weight(2f)
                            )

                            Image(
                                painter = painterResource(id = R.drawable.ic_map),
                                contentDescription = null,
                                modifier = Modifier.clickable(
                                    interactionSource = interaction,
                                    indication = null
                                ) { viewModel.openMapScreen() }
                            )

                            Spacer(
                                modifier = Modifier
                                    .width(0.dp)
                                    .weight(3f)
                            )

                        }
                    }
                }
            }
        }
    }

    @Preview
    @Composable
    fun MainScreenPreview() {
        MainScreenContent()
    }


    @Composable
    fun CategorySpinner() {
        var expanded by remember { mutableStateOf(false) }
        var selectedItem by remember { mutableStateOf("Food") }
        val suggestions = listOf(
            CategoryData("Food", Color(0xFF71A600)),
            CategoryData("Cloth", color = mainColor),
            CategoryData("Pharmacy", Color(0xFFCF1A66)),
            CategoryData("Entertainment", Color(0xFFD07000))
        )


        Box(
            modifier = Modifier.clip(RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {

            Row(modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .border(
                    border = BorderStroke(width = 2.dp, color = Color.Gray),
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable { expanded = !expanded }
                .padding(horizontal = 12.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Category",
                    style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 50.sp,
                        fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFFA4B4CB),
                        textAlign = TextAlign.Center,
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(id = if (!expanded) R.drawable.ic_arrow_bottom else R.drawable.ic_arrow_top),
                    contentDescription = null,
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                suggestions.forEach { label ->
                    DropdownMenuItem(modifier = Modifier.background(Color.White), onClick = {
                        selectedItem = label.label
                        expanded = false
                    }, text = {
                        Text(text = label.label, color = label.color)
                    })
                }
            }
        }
    }

    @Composable
    fun StatusSpinner() {
        var expanded by remember { mutableStateOf(false) }
        var selectedItem by remember { mutableStateOf("Ultra") }
        val suggestions = listOf(
            CategoryData("Ultra", Color(0xFF71A600)),
            CategoryData("Pro Max", color = mainColor),
            CategoryData("Pro", Color(0xFFCF1A66))
        )


        Box(modifier = Modifier.clip(RoundedCornerShape(8.dp))) {

            Row(modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .border(
                    border = BorderStroke(width = 2.dp, color = Color.Gray),
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable { expanded = !expanded }
                .padding(horizontal = 12.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Status",
                    style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 50.sp,
                        fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFFA4B4CB),
                        textAlign = TextAlign.Center,
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(id = if (!expanded) R.drawable.ic_arrow_bottom else R.drawable.ic_arrow_top),
                    contentDescription = null,
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                suggestions.forEach { label ->
                    DropdownMenuItem(modifier = Modifier.background(Color.White), onClick = {
                        selectedItem = label.label
                        expanded = false
                    }, text = {
                        Text(text = label.label, color = label.color)
                    })
                }
            }
        }
    }

}

