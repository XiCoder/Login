package com.boyasec.test.ui.page

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.boyasec.test.ui.widget.AppBar
import com.boyasec.test.utils.PasswordLevel
import com.boyasec.test.utils.PasswordUtils
import com.boyasec.test.R

/**
 * 欢迎页面，提示账号和密码等级
 */
@Composable
fun MainPage(name: String?, password: String?, navController: NavController) {
    Column {
        AppBar(title = stringResource(id = R.string.welcome), navigationIcon = {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "back",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }, navigationClick = { navController.popBackStack() })

        Box(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            val level = password?.let {
                when (PasswordUtils.checkPasswordLevel(it)) {
                    PasswordLevel.Easy -> stringResource(id = R.string.easy)
                    PasswordLevel.Middle -> stringResource(id = R.string.middle)
                    PasswordLevel.Strong -> stringResource(id = R.string.strong)
                    PasswordLevel.VeryStrong -> stringResource(id = R.string.very_strong)
                }
            } ?: stringResource(id = R.string.password_is_empty)
            Text(text = stringResource(id = R.string.welcome_password_tip).format(name, level))
        }
    }
}