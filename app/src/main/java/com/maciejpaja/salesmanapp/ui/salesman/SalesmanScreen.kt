package com.maciejpaja.salesmanapp.ui.salesman

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maciejpaja.salesmanapp.R
import com.maciejpaja.salesmanapp.models.Salesman
import com.maciejpaja.salesmanapp.ui.theme.LocalColor


@Composable
fun SalesmanSearchView(
    onSearchQueryChange: (String) -> Unit
) {
    Column {
        var text by remember { mutableStateOf("") }

        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                onSearchQueryChange(it)
            },
            maxLines = 1,
            textStyle = TextStyle(fontSize = 16.sp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 16.dp, end = 16.dp)
                .shadow(elevation = 2.dp),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp, horizontal = 4.dp),
                    content = {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.icon_search),
                            tint = LocalColor.TextGrey,
                            contentDescription = null,
                            modifier = Modifier.padding(end = 2.dp)
                        )
                        Box(
                            modifier = Modifier.weight(19f),
                            contentAlignment = Alignment.CenterStart,
                            content = {
                                if (text.isEmpty()) {
                                    Text(
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 2.dp),
                                        text = "Suche",
                                        color = Color.Gray
                                    )
                                }
                                innerTextField()
                            }
                        )
                        Spacer(Modifier.weight(1f))
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.icon_microphone),
                            tint = LocalColor.TextGrey,
                            contentDescription = null,
                            modifier = Modifier.padding(end = 2.dp)
                        )
                    }
                )

            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalesmanAppBar(
    onBackArrowClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Adressen",
                color = LocalColor.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = LocalColor.DarkBlue),
        navigationIcon = {
            IconButton(onClick = onBackArrowClick) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.icon_arrow_back),
                    contentDescription = "Localized description",
                    tint = LocalColor.White
                )
            }
        }
    )
}

@Composable
fun SalesmanList(
    salesmanList: List<Salesman>,
    expandedList: List<String>,
    onArrowClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        itemsIndexed(
            salesmanList
        ) { index, salesMan ->
            ExpendableItem(salesman = salesMan,
                onArrowClick = { onArrowClick.invoke(salesMan.name) },
                expanded = expandedList.any { it == salesMan.name })
            if (index < salesmanList.lastIndex) HorizontalDivider(color = LocalColor.DividerGray)
        }
    }
}

@Composable
fun ExpendableItem(salesman: Salesman, onArrowClick: () -> Unit, expanded: Boolean) {
    if (expanded) {
        SalesmanItemExpanded(salesman = salesman, onArrowClick = onArrowClick)
    } else {
        SalesmanItem(salesman = salesman, onArrowClick = onArrowClick)
    }
}

@Composable
fun RoundFirstLetter(salesman: Salesman) {
    Box(
        modifier = Modifier
            .padding(top = 10.dp, bottom = 10.dp, end = 6.dp)
            .height(42.dp)
            .width(42.dp)
            .aspectRatio(1f)
            .background(LocalColor.LightGray, shape = CircleShape)
            .border(0.5.dp, Color.LightGray, CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = salesman.name.first().titlecase(),
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
    }
}

@Composable
fun SalesmanItem(salesman: Salesman, onArrowClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RoundFirstLetter(salesman = salesman)
        Text(
            text = salesman.name,
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )
        Spacer(Modifier.weight(1f))
        IconButton(onClick = onArrowClick) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.icon_arrow_right),
                tint = LocalColor.TextGrey,
                contentDescription = null,
                modifier = Modifier
                    .height(24.dp)
                    .width(24.dp)
            )
        }
    }
}

@Composable
fun SalesmanItemExpanded(salesman: Salesman, onArrowClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RoundFirstLetter(salesman = salesman)
        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
        ) {
            Text(text = salesman.name)
            Text(
                text = salesman.areas.joinToString(separator = ", ") { it },
                color = LocalColor.TextGrey
            )
        }
        Spacer(Modifier.weight(1f))
        Column {

        }
        IconButton(onClick = onArrowClick) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.icon_arrow_bottom),
                tint = LocalColor.TextGrey,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .height(24.dp)
                    .width(24.dp)
            )
        }

    }
}