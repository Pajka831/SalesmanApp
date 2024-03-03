package com.maciejpaja.salesmanapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.maciejpaja.salesmanapp.R
import com.maciejpaja.salesmanapp.models.Salesman
import com.maciejpaja.salesmanapp.ui.salesman.SalesmanAppBar
import com.maciejpaja.salesmanapp.ui.salesman.SalesmanList
import com.maciejpaja.salesmanapp.ui.salesman.SalesmanSearchView
import com.maciejpaja.salesmanapp.ui.theme.LocalColor
import com.maciejpaja.salesmanapp.ui.theme.SalesmanAppTheme
import org.koin.android.ext.android.inject


class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SalesmanAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        SearchScreen(viewModel = viewModel) {
                            onBackPressedDispatcher.onBackPressed()
                        }
                    }

                }
            }
        }
    }

    @Composable
    fun SearchScreen(
        viewModel: MainViewModel,
        onBackArrowClick: () -> Unit
    ) {
        val salesmenState by viewModel.salesmenState.collectAsStateWithLifecycle()
        val expandedNamesList by viewModel.expandedCardIdsList.collectAsStateWithLifecycle()

        SalesmanAppBar(onBackArrowClick)
        SalesmanSearchView {
            viewModel.onSearchInputChange(it)
        }
        SalesmanList(salesmanList = salesmenState, expandedList = expandedNamesList) {
            viewModel.onExpandItemClick(it)
        }
    }
}
