package com.example.harry_potter_app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.ui.res.vectorResource
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.harry_potter_app.R
import com.example.harry_potter_app.common.TabBarBadgeView

@Composable
fun BottomBar(
    onNavigate: (String) -> Unit,
) {

    val charactersTab = TabBarItem(title = HarryPotterScreen.Characters.name, selectedIcon = ImageVector.vectorResource(R.drawable.filledwizard), unselectedIcon = ImageVector.vectorResource(R.drawable.outlinewizard))
    val housesTab = TabBarItem(title = HarryPotterScreen.Houses.name, selectedIcon = ImageVector.vectorResource(R.drawable.filledhouse), unselectedIcon = ImageVector.vectorResource(R.drawable.outlinedhouse))
    val booksTab = TabBarItem(title = HarryPotterScreen.Books.name, selectedIcon = ImageVector.vectorResource(R.drawable.filledbook), unselectedIcon = ImageVector.vectorResource(R.drawable.outlinedbook))
    val favoritesTab = TabBarItem(title = HarryPotterScreen.Favorites.name, selectedIcon = ImageVector.vectorResource(R.drawable.filledheart), unselectedIcon = ImageVector.vectorResource(R.drawable.outlinedheart))
    val tabBarItems = listOf(charactersTab, housesTab, booksTab, favoritesTab)

    TabView(tabBarItems, onNavigate)
}

data class TabBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeAmount: Int? = null
)

@Composable
fun TabView(tabBarItems: List<TabBarItem>, onNavigate: (String) -> Unit) {
    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary
    ) {

        tabBarItems.forEachIndexed { index, tabBarItem ->
            NavigationBarItem(
                selected = selectedTabIndex == index,
                onClick = {
                    selectedTabIndex = index
                    onNavigate(tabBarItem.title)
                },
                icon = {
                    TabBarIconView(
                        isSelected = selectedTabIndex == index,
                        selectedIcon = tabBarItem.selectedIcon,
                        unselectedIcon = tabBarItem.unselectedIcon,
                        title = tabBarItem.title,
                        badgeAmount = tabBarItem.badgeAmount
                    )
                },
                label = { Text(tabBarItem.title) })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabBarIconView(
    isSelected: Boolean,
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    title: String,
    badgeAmount: Int? = null
) {
    BadgedBox(badge = { TabBarBadgeView(badgeAmount) }) {
        Icon(
            imageVector = if (isSelected) {selectedIcon} else {unselectedIcon},
            contentDescription = title,
        )
    }
}