package io.crypto.feature.settings.presentation.screens.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldDefaults
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import cafe.adriel.voyager.hilt.getViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import io.crypto.feature.registration.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import io.crypto.feature.settings.data.model.CategoryData
import io.crypto.feature.settings.data.model.MerchantData
import io.crypto.feature.settings.data.model.TransactionData
import io.crypto.feature.settings.navigation.AppScreen
import io.crypto.feature.settings.presentation.components.MerchantItem
import io.crypto.feature.settings.utils.hasLocationPermission
import io.crypto.feature.settings.utils.mainColor
import kotlin.coroutines.EmptyCoroutineContext


sealed class MyBottomSheetState() {
    object Searching : MyBottomSheetState()
    object Disable : MyBottomSheetState()

    data class Results(val list: List<TransactionData>) : MyBottomSheetState()

    data class Details(val item: MerchantData) : MyBottomSheetState()
}

class MapScreen : AppScreen() {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var placesClient: PlacesClient

    @Composable
    override fun Content() {
        MapScreenContent()
    }

    @OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterialApi::class)
    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable

    fun MapScreenContent() {
        var dialogState by remember { mutableStateOf(true) }
        var bottomSheetState: MyBottomSheetState by remember {
            mutableStateOf(MyBottomSheetState.Searching)
        }
        var radius by remember {
            mutableDoubleStateOf(200.0)
        }
        val permissionsToRequest = Manifest.permission.ACCESS_FINE_LOCATION

        val permissionState = rememberMultiplePermissionsState(
            permissions = listOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

        val viewModel = getViewModel<MapScreenViewModel>()
        val viewState by viewModel.viewState.collectAsState()


        LaunchedEffect(LocalContext.current.hasLocationPermission()) {
            permissionState.launchMultiplePermissionRequest()
        }
        val context = LocalContext.current

        when {
            permissionState.allPermissionsGranted -> {
                LaunchedEffect(Unit) {
                    viewModel.handle(PermissionEvent.Granted)
                }
            }

            permissionState.shouldShowRationale -> {
                RationaleAlert(onDismiss = { }) {
                    permissionState.launchMultiplePermissionRequest()
                }
            }

            !permissionState.allPermissionsGranted && !permissionState.shouldShowRationale -> {
                LaunchedEffect(Unit) {
                    viewModel.handle(PermissionEvent.Revoked)
                }
            }
        }
        val scope = rememberCoroutineScope() {
            EmptyCoroutineContext
        }
        scope.launch {
            delay(1000)
            radius += 100
            if (radius >= 1000) {
                radius = 200.0
            }
        }
        val sheetState = rememberBottomSheetState(
            initialValue = BottomSheetValue.Expanded
        )
        val scaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = sheetState
        )
        BottomSheetScaffold(
            modifier = Modifier,
            sheetPeekHeight = if (bottomSheetState is MyBottomSheetState.Results) 400.dp else BottomSheetScaffoldDefaults.SheetPeekHeight,
            sheetGesturesEnabled = bottomSheetState !is MyBottomSheetState.Disable,
            scaffoldState = scaffoldState,
            sheetShape = RoundedCornerShape(topEnd = 32.dp, topStart = 32.dp),
            sheetContent = {
                when (bottomSheetState) {
                    is MyBottomSheetState.Searching -> {
                        BottomSheetSearchingContent {
                            bottomSheetState = MyBottomSheetState.Disable
                        }
                    }

                    is MyBottomSheetState.Disable -> {
                        scope.launch {
                            sheetState.expand()
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceAround,
                            modifier = Modifier
                                .fillMaxWidth()

                                .padding(vertical = 16.dp)
                        ) {
                            IconButton(onClick = {
                                bottomSheetState = MyBottomSheetState.Results(
                                    emptyList<TransactionData>()
                                )
                            }) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_top),
                                    contentDescription = null
                                )
                            }
                            Text(
                                text = "Searching results",
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    lineHeight = 25.sp,
                                    fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF09282D),
                                    textAlign = TextAlign.Center,
                                )
                            )
                        }
                    }

                    is MyBottomSheetState.Results -> {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            IconButton(
                                onClick = {}, modifier = Modifier.align(
                                    Alignment.CenterHorizontally
                                )
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_line),
                                    contentDescription = null,
                                    modifier = Modifier.rotate(180f)
                                )
                            }

                            CategorySpinner()
                            LazyColumn {
                                items(30) {
                                    Box(modifier = Modifier.clickable {
                                        bottomSheetState = MyBottomSheetState.Details(
                                            MerchantData("Merchant Name", "Geo", "", "Food")
                                        )
                                    }) {

                                        MerchantItem(
                                            name = "Marchent Name",
                                            geoName = "Geo",
                                            categoryName = "Food",
                                            color = Color(0xFF71A600)
                                        )
                                    }
                                }
                            }

                        }
                    }

                    is MyBottomSheetState.Details -> {
                        scope.launch {
                            sheetState.expand()
                        }
                        val merchantData = (bottomSheetState as MyBottomSheetState.Details).item
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .padding(vertical = 8.dp, horizontal = 16.dp)
                        ) {
                            Image(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                painter = painterResource(id = R.drawable.ic_line),
                                contentDescription = null,
                                alignment = Alignment.TopCenter
                            )
                            Spacer(
                                modifier = Modifier
                                    .height(0.dp)
                                    .weight(1f)
                            )
                            MerchantItem(
                                name = merchantData.name,
                                geoName = merchantData.geoName,
                                categoryName = merchantData.categoryName,
                                color = Color(0xFF71A600)
                            )

                            Spacer(
                                modifier = Modifier
                                    .height(0.dp)
                                    .weight(1f)
                            )
                            Button(
                                onClick = { },
                                modifier = Modifier.padding(vertical = 8.dp),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = mainColor)
                            ) {
                                Row(
                                    modifier = Modifier,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_link),
                                        contentDescription = null,
                                        modifier = Modifier.padding(horizontal = 8.dp)
                                    )
                                    Text(
                                        text = "Go to site",
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                            fontFamily = FontFamily(Font(R.font.gramatika_bold)),
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFFFFFFFF),
                                            textAlign = TextAlign.Center,
                                        )
                                    )
                                }
                            }
                        }
                    }
                }

            }
        ) {
            Surface(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.fillMaxSize()) {

                    with(viewState) {
                        when (this) {
                            ViewState.Loading -> {

                            }

                            ViewState.RevokedPermissions -> {

                            }

                            is ViewState.Success -> {
                                val currentLoc =
                                    LatLng(
                                        this.location?.latitude ?: 0.0,
                                        this.location?.longitude ?: 0.0
                                    )
                                val cameraState = rememberCameraPositionState()

                                LaunchedEffect(key1 = currentLoc) {
                                    cameraState.centerOnLocation(currentLoc)
                                }

                                MainScreen(
                                    Modifier
                                        .fillMaxWidth(),
                                    currentPosition = LatLng(
                                        currentLoc.latitude,
                                        currentLoc.longitude,

                                        ),
                                    cameraState = cameraState,
                                    radius,
                                    listOf(
                                        LatLng(41.355305, 69.291905),
                                        LatLng(41.351323, 69.301023),
                                        LatLng(41.350693, 69.288733),
                                        LatLng(41.340874, 69.286212),
                                    )
                                ) {
                                    bottomSheetState = MyBottomSheetState.Details(
                                        MerchantData(
                                            "Merchant name",
                                            "Geo",
                                            "",
                                            "Food"
                                        )
                                    )
                                }
                            }
                        }
                    }

                    IconButton(onClick = { viewModel.back() }, modifier = Modifier.padding(8.dp)) {
                        Image(
                            modifier = Modifier.padding(),
                            painter = painterResource(id = R.drawable.ic_arrow_back_to_top),
                            contentDescription = null
                        )
                    }
                }
            }
        }

        if (dialogState) {
            Dialog(onDismissRequest = { dialogState = false }) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White
                ) {
                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(36.dp))
                        Image(
                            painter = painterResource(id = R.drawable.ic_error_map),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = "No merchants found near you",
                            style = TextStyle(
                                fontSize = 25.sp,
                                lineHeight = 30.sp,
                                fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFF09282D),
                                textAlign = TextAlign.Center,
                            )
                        )
                        Text(
                            text = "try to search in other locations",
                            style = TextStyle(
                                fontSize = 15.sp,
                                lineHeight = 20.sp,
                                fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFF09282D),
                                textAlign = TextAlign.Center,
                            )
                        )

                        Spacer(modifier = Modifier.height(32.dp))
                        Button(
                            onClick = { viewModel.back() },
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = mainColor)
                        ) {
                            Text(
                                text = "Go back to the main page",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.gramatika_bold)),
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFFFFFFFF),
                                    textAlign = TextAlign.Center,
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }
            }
        }

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


    @Preview
    @Composable
    fun MapScreenPreview() {
        MapScreenContent()
    }


    @Composable
    fun RationaleAlert(onDismiss: () -> Unit, onConfirm: () -> Unit) {

        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties()
        ) {
            Surface(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "We need location permissions to use this app",
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    TextButton(
                        onClick = {
                            onConfirm()
                            onDismiss()
                        },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("OK")
                    }
                }
            }
        }
    }

}


private suspend fun CameraPositionState.centerOnLocation(
    location: LatLng
) = animate(
    update = CameraUpdateFactory.newLatLngZoom(
        location,
        15f
    ),
    durationMs = 1000
)


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    currentPosition: LatLng,
    cameraState: CameraPositionState,
    radiusDefault: Double,
    locations: List<LatLng>,
    clickMarker: (LatLng) -> Unit
) {

    var radius by remember {
        mutableDoubleStateOf(radiusDefault)
    }
    val scope = rememberCoroutineScope {
        EmptyCoroutineContext
    }
    scope.launch {
        delay(1000)
        radius += 100
        if (radius >= 1000) {
            radius = 200.0
        }
    }
//    val bitmap = BitmapFactory.decodeResource(null, R.drawable.ic_circular_picture)
//    val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, false)
    val marker = LatLng(currentPosition.latitude, currentPosition.longitude)
    Column(modifier = modifier) {

        GoogleMap(
            modifier = Modifier
                .fillMaxWidth(),
            cameraPositionState = cameraState,
            properties = MapProperties(
                isMyLocationEnabled = true,
                isTrafficEnabled = false
            )
        ) {
            if (locations.isEmpty()) {
                Circle(
                    center = marker,
                    clickable = true,
                    fillColor = mainColor.copy(alpha = 0.3f),
                    radius = radius, // Specify the radius in meters
                    strokeColor = mainColor,
                    strokeWidth = 2f,
                    onClick = { circle ->
                        // Handle circle click event
//                selectedCircle = circle.tag as? CircleInfo
                    }
                )
            } else {
                locations.forEach {
                    val bitmapDescriptor = bitmapDescriptorFromVector(
                        context = LocalContext.current,
                        vectorDrawableResourceId = R.drawable.ic_circular_picture
                    )
                    Marker(
                        state = MarkerState(
                            position = LatLng(
                                it.latitude,
                                it.longitude + 0.00004
                            )
                        ), icon = bitmapDescriptor
                    )
                    Marker(
                        onClick = {
                            clickMarker(it.position)
                            true
                        },
                        state = MarkerState(position = it),
                        icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_locations_marker)
                    )
                }
            }
            Marker(

                state = MarkerState(position = marker),
                title = "MyPosition",
                icon = bitmapDescriptorFromVector(LocalContext.current, R.drawable.ic_marker),
                snippet = "This is a description of this Marker",
                draggable = true,

                )
        }

    }


}

@Composable
fun BottomSheetSearchingContent(clickDoNotStop: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
            .background(androidx.compose.ui.graphics.Color.White)

    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp),
            text = "We are looking for merchants within a radius of 4 km from you",
            style = TextStyle(
                fontSize = 20.sp,
                lineHeight = 30.sp,
                fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                fontWeight = FontWeight(400),
                color = Color(0xFF09282D),
                textAlign = TextAlign.Center,
            )
        )
        Text(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .align(Alignment.CenterHorizontally)
                .clickable() {
                    clickDoNotStop.invoke()
                },
            text = "Don't stop searching",
            style = TextStyle(
                fontSize = 15.sp,
                lineHeight = 20.sp,
                fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                fontWeight = FontWeight(400),
                color = Color(0xFF09282D),
                textAlign = TextAlign.Center,
            )
        )
    }
}

private fun bitmapDescriptorFromVector(
    context: Context,
    @DrawableRes vectorDrawableResourceId: Int
): BitmapDescriptor {


    val background = ContextCompat.getDrawable(context, R.drawable.ic_marker)
    background!!.setBounds(0, 0, background.intrinsicWidth, background.intrinsicHeight)
    val vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId)
    vectorDrawable!!.setBounds(
        40,
        20,
        vectorDrawable.intrinsicWidth + 40,
        vectorDrawable.intrinsicHeight + 20
    )
    val bitmap = Bitmap.createBitmap(
        background.intrinsicWidth,
        background.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    background.draw(canvas)
    vectorDrawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}


