package io.crypto.feature.settings.presentation.screens.settings

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import cafe.adriel.voyager.hilt.getViewModel
import io.crypto.feature.registration.R
import io.crypto.feature.settings.navigation.AppScreen
import io.crypto.feature.settings.presentation.components.CustomSwitchButton

class SettingsScreen : AppScreen() {
    @Composable
    override fun Content() {
        SettingsScreenContent()
    }

    @Composable
    private fun SettingsScreenContent() {

        var autoExitDialogState by remember { mutableStateOf(false) }
        var fontSizeDialogState by remember { mutableStateOf(false) }
        var selectedAutoExitTime by remember { mutableIntStateOf(5) }
        var selectedFontSIze by remember { mutableIntStateOf(100) }

        var fingerPrintSwitchState by remember {
            mutableStateOf(true)
        }
        var faceIdSwitchState by remember {
            mutableStateOf(true)
        }
        var notificationSwitchState by remember {
            mutableStateOf(true)
        }

        var autoExitTime by remember {
            mutableIntStateOf(5)
        }

        val interaction = remember {
            MutableInteractionSource()
        }

        val viewModel = getViewModel<SettingsScreenViewModel>()

        var offset by remember { mutableFloatStateOf(0f) }


        Surface(modifier = Modifier.fillMaxSize()) {
            LazyColumn {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()

                            .background(Color(0xFFFAFBFC))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(bottomEnd = 32.dp, bottomStart = 32.dp))
                                .background(Color.White)
                                .padding(horizontal = 16.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp), verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(onClick = { viewModel.backToHome() }) {

                                    Image(
                                        painter = painterResource(id = R.drawable.ic_arrow_back_to_top),
                                        contentDescription = null
                                    )
                                }
                                Spacer(modifier = Modifier.width(22.dp))

                                Image(
                                    painter = painterResource(id = R.drawable.ic_settings),
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                    text = "Settings",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFF09282D),
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.height(32.dp))
                            Text(
                                text = "Safety",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF09282D),
                                )
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier.size(40.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_passord),
                                        contentDescription = null
                                    )
                                }
                                Text(
                                    text = "Change PIN",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFF09282D),
                                    )
                                )
                                Spacer(
                                    modifier = Modifier
                                        .width(0.dp)
                                        .weight(1f)
                                )
                                Box(
                                    modifier = Modifier.size(30.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    IconButton(onClick = { viewModel.openChangePinScreen() }) {

                                        Image(
                                            painter = painterResource(id = R.drawable.ic_next),
                                            contentDescription = null
                                        )
                                    }

                                }
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier.size(40.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_finger_print),
                                        contentDescription = null
                                    )
                                }
                                Column(
                                    modifier = Modifier,
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Login with biometrics",
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFF09282D),
                                        )
                                    )
                                    Text(
                                        text = "biometric input connected",
                                        style = TextStyle(
                                            fontSize = 12.sp,
                                            fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFFA4B4CB),
                                        )
                                    )
                                }

                                Spacer(
                                    modifier = Modifier
                                        .width(0.dp)
                                        .weight(1f)
                                )
                                Box(
                                    modifier = Modifier.size(30.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CustomSwitchButton(
                                        value = fingerPrintSwitchState,
                                        onValueChange = { fingerPrintSwitchState = it })
                                }
                                /*Switch(checked = fingerPrintSwitchState, onCheckedChange = {
                                    fingerPrintSwitchState = it
                                }, colors = SwitchDefaults.colors(checkedThumbColor = mainColor))*/
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier.size(40.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_frame),
                                        contentDescription = null
                                    )
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(50))
                                            .size(40.dp)

                                            .background(Color.White)
                                    )
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_person),
                                        contentDescription = null
                                    )
                                }
                                Column(
                                    modifier = Modifier,
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Extra. face photo protection",
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFF09282D),
                                        )
                                    )
                                    Text(
                                        text = "Included",
                                        style = TextStyle(
                                            fontSize = 12.sp,
                                            fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFFA4B4CB),
                                        )
                                    )
                                }

                                Spacer(
                                    modifier = Modifier
                                        .width(0.dp)
                                        .weight(1f)
                                )
                                Box(
                                    modifier = Modifier.size(30.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CustomSwitchButton(
                                        value = faceIdSwitchState,
                                        onValueChange = { faceIdSwitchState = it })
                                }
                                /*Switch(checked = fingerPrintSwitchState, onCheckedChange = {
                                    fingerPrintSwitchState = it
                                }, colors = SwitchDefaults.colors(checkedThumbColor = mainColor))*/
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier.size(40.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_phrace),
                                        contentDescription = null
                                    )
                                }
                                Text(
                                    text = "Recovery Phrase",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFF09282D),
                                    )
                                )
                                Spacer(
                                    modifier = Modifier
                                        .width(0.dp)
                                        .weight(1f)
                                )
                                Box(
                                    modifier = Modifier.size(30.dp),
                                    contentAlignment = Alignment.Center
                                ) {

                                    IconButton(onClick = { viewModel.openEnvironmentScreen() }) {

                                        Image(
                                            painter = painterResource(id = R.drawable.ic_next),
                                            contentDescription = null
                                        )
                                    }
                                }
                            }


                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp)
                                    .clickable(interactionSource = interaction, indication = null) {
                                        autoExitDialogState = true
                                    },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier.size(40.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_exit),
                                        contentDescription = null
                                    )
                                }
                                Column(
                                    modifier = Modifier,
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "automatic exit",
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFF09282D),
                                        )
                                    )
                                    Text(
                                        text = "$selectedAutoExitTime minutes",
                                        style = TextStyle(
                                            fontSize = 12.sp,
                                            fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFFA4B4CB),
                                        )
                                    )
                                }

                                Spacer(
                                    modifier = Modifier
                                        .width(0.dp)
                                        .weight(1f)
                                )

                                /*Switch(checked = fingerPrintSwitchState, onCheckedChange = {
                                    fingerPrintSwitchState = it
                                }, colors = SwitchDefaults.colors(checkedThumbColor = mainColor))*/
                            }
                            Spacer(modifier = Modifier.height(22.dp))
                        }
                        Spacer(modifier = Modifier.height(22.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(32.dp))
                                .background(Color.White)
                                .padding(vertical = 24.dp, horizontal = 16.dp)
                        ) {
                            Text(
                                text = "Basic",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF09282D),
                                )
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier.size(40.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_question_sign),
                                        contentDescription = null
                                    )
                                }
                                Text(
                                    text = "FAQ",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFF09282D),
                                    )
                                )
                                Spacer(
                                    modifier = Modifier
                                        .width(0.dp)
                                        .weight(1f)
                                )
                                Box(
                                    modifier = Modifier.size(30.dp),
                                    contentAlignment = Alignment.Center
                                ) {

                                    Image(
                                        painter = painterResource(id = R.drawable.ic_next),
                                        contentDescription = null
                                    )
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier.size(40.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_upload),
                                        contentDescription = null
                                    )
                                }
                                Text(
                                    text = "Transaction options",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFF09282D),
                                    )
                                )
                                Spacer(
                                    modifier = Modifier
                                        .width(0.dp)
                                        .weight(1f)
                                )
                                Box(
                                    modifier = Modifier.size(30.dp),
                                    contentAlignment = Alignment.Center
                                ) {

                                    Image(
                                        painter = painterResource(id = R.drawable.ic_next),
                                        contentDescription = null
                                    )
                                }
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier.size(40.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_notification),
                                        contentDescription = null
                                    )
                                }
                                Column(
                                    modifier = Modifier,
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {

                                    Text(
                                        text = "Notifications",
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            lineHeight = 50.sp,
                                            fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFF09282D),
                                        )
                                    )
                                    Text(
                                        text = "Included",
                                        style = TextStyle(
                                            fontSize = 12.sp,
                                            lineHeight = 50.sp,
                                            fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFFA4B4CB),
                                        )
                                    )
                                }

                                Spacer(
                                    modifier = Modifier
                                        .width(0.dp)
                                        .weight(1f)
                                )
                                Box(
                                    modifier = Modifier.size(30.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CustomSwitchButton(
                                        value = notificationSwitchState,
                                        onValueChange = { notificationSwitchState = it })
                                }
                                /*Switch(checked = fingerPrintSwitchState, onCheckedChange = {
                                    fingerPrintSwitchState = it
                                }, colors = SwitchDefaults.colors(checkedThumbColor = mainColor))*/
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier.size(40.dp),
                                    contentAlignment = Alignment.Center
                                ) {

                                    Image(
                                        painter = painterResource(id = R.drawable.ic_aa),
                                        contentDescription = null
                                    )
                                }
                                Column(
                                    modifier = Modifier,
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Font size",
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFF09282D),
                                        )
                                    )
                                    Text(
                                        text = "$selectedFontSIze %",
                                        style = TextStyle(
                                            fontSize = 12.sp,
                                            lineHeight = 50.sp,
                                            fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFFA4B4CB),
                                        )
                                    )

                                }

                                Spacer(
                                    modifier = Modifier
                                        .width(0.dp)
                                        .weight(1f)
                                )
                                Box(
                                    modifier = Modifier.size(30.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    IconButton(onClick = { fontSizeDialogState = true }) {
                                        Image(
                                            painter = painterResource(id = R.drawable.ic_next),
                                            contentDescription = null
                                        )
                                    }
                                }
                                /*Switch(checked = fingerPrintSwitchState, onCheckedChange = {
                                    fingerPrintSwitchState = it
                                }, colors = SwitchDefaults.colors(checkedThumbColor = mainColor))*/
                            }


                        }


                        Spacer(modifier = Modifier.height(16.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(32.dp))
                                .background(Color.White)
                                .padding(horizontal = 16.dp, vertical = 22.dp)
                        ) {
                            Text(
                                text = "language",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    lineHeight = 50.sp,
                                    fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF09282D),
                                )
                            )
                            Spacer(modifier = Modifier.height(40.dp))
                            LanguageSpinner(
                                selectedLanguage = "English",
                                arrayListOf<String>("English", "ภาษาไทย"),
                                onChangeItem = {

                                })
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(topEnd = 32.dp, topStart = 32.dp))
                                .background(Color.White)
                                .padding(horizontal = 16.dp, vertical = 30.dp)
                        ) {
                            Text(
                                text = "Support",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    lineHeight = 50.sp,
                                    fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF09282D),
                                )
                            )
                            Text(
                                text = "Have a question? . We will answer you within 15 minutes",
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    lineHeight = 17.sp,
                                    fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF09282D),
                                )
                            )
                            Text(
                                text = "Go to chat",
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    lineHeight = 17.sp,
                                    fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF1ABACF),
                                )
                            )

                        }

                    }
                }
            }
        }
        if (autoExitDialogState) {
            AutoExitDialog(
                onDismissRequestAndClickCancelBtn = { autoExitDialogState = false },
                selectedItem = selectedAutoExitTime,
                changeSelectedItem = { selectedAutoExitTime = it }
            )
        }
        if (fontSizeDialogState) {
            FontSizeDialog(
                onDismissRequestAndClickCancelBtn = { fontSizeDialogState = false },
                selectedItem = selectedFontSIze,
                changeSelectedItem = { selectedFontSIze = it }
            )
        }
    }

    @Composable
    fun LanguageSpinner(
        selectedLanguage: String,
        suggestions: List<String>,
        onChangeItem: (String) -> Unit
    ) {
        var expanded by remember { mutableStateOf(false) }
        var selectedItem by remember { mutableStateOf(selectedLanguage) }


        Box(
            modifier = Modifier.clip(RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {

            Row(modifier = Modifier
                .fillMaxWidth(0.8f)
                .clip(RoundedCornerShape(8.dp))
                .border(
                    border = BorderStroke(width = 2.dp, color = Color.Gray),
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable { expanded = !expanded }
                .padding(horizontal = 12.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                androidx.compose.material3.Text(
                    text = selectedItem,
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 50.sp,
                        fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFFA4B4CB),
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
                        selectedItem = label
                        onChangeItem(label)
                        expanded = false
                    }, text = {
                        androidx.compose.material3.Text(text = label)
                    })
                }
            }
        }
    }

    @Composable
    fun AutoExitDialog(
        onDismissRequestAndClickCancelBtn: () -> Unit,
        selectedItem: Int,
        changeSelectedItem: (Int) -> Unit
    ) {
        Dialog(onDismissRequest = onDismissRequestAndClickCancelBtn) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 42.dp, bottom = 16.dp)
                        .padding(horizontal = 20.dp)
                ) {
                    Text(
                        text = "Automatic exit",
                        style = TextStyle(
                            fontSize = 25.sp,
                            fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF09282D),
                        ),
                        modifier = Modifier.padding(start = 12.dp)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = selectedItem == 1,
                            onClick = { changeSelectedItem(1) })
                        Text(
                            text = "1 minute",
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFF09282D),
                            )
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = selectedItem == 2,
                            onClick = { changeSelectedItem(2) })
                        Text(
                            text = "2 minute",
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFF09282D),
                            )
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = selectedItem == 5,
                            onClick = { changeSelectedItem(5) })
                        Text(
                            text = "5 minute",
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFF09282D),
                            )
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = selectedItem == 10,
                            onClick = { changeSelectedItem(10) })
                        Text(
                            text = "10 minute",
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFF09282D),
                            )
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = selectedItem == 50,
                            onClick = { changeSelectedItem(50) })
                        Text(
                            text = "50 minute",
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFF09282D),
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    val interaction = remember {
                        MutableInteractionSource()
                    }

                    Text(
                        text = "Cancel",
                        style = TextStyle(
                            fontSize = 15.sp,
                            lineHeight = 30.sp,
                            fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF1ABACF),
                            textAlign = TextAlign.Right,
                        ),
                        modifier = Modifier
                            .align(Alignment.End)
                            .clickable(
                                interactionSource = interaction,
                                indication = null
                            ) { onDismissRequestAndClickCancelBtn() }
                    )
                }
            }
        }

    }

    @Composable
    fun FontSizeDialog(
        onDismissRequestAndClickCancelBtn: () -> Unit,
        selectedItem: Int,
        changeSelectedItem: (Int) -> Unit
    ) {
        Dialog(onDismissRequest = onDismissRequestAndClickCancelBtn) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 42.dp, bottom = 16.dp)
                        .padding(horizontal = 20.dp)
                ) {
                    Text(
                        text = "Font size",
                        style = TextStyle(
                            fontSize = 25.sp,
                            fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF09282D),
                        ),
                        modifier = Modifier.padding(start = 12.dp)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = selectedItem == 100,
                            onClick = { changeSelectedItem(100) })
                        Text(
                            text = "100 %",
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFF09282D),
                            )
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = selectedItem == 125,
                            onClick = { changeSelectedItem(125) })
                        Text(
                            text = "125 %",
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFF09282D),
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    val interaction = remember {
                        MutableInteractionSource()
                    }

                    Text(
                        text = "Cancel",
                        style = TextStyle(
                            fontSize = 15.sp,
                            lineHeight = 30.sp,
                            fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF1ABACF),
                            textAlign = TextAlign.Right,
                        ),
                        modifier = Modifier
                            .align(Alignment.End)
                            .clickable(
                                interactionSource = interaction,
                                indication = null
                            ) { onDismissRequestAndClickCancelBtn() }
                    )
                }
            }
        }

    }


}