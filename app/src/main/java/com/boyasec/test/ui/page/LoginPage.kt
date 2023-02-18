package com.boyasec.test.ui.page

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.boyasec.test.R
import com.boyasec.test.data.Result
import com.boyasec.test.data.RouteConfig
import com.boyasec.test.extentions.isEmail
import com.boyasec.test.ui.theme.Grey
import com.boyasec.test.ui.widget.AppBar
import com.boyasec.test.ui.widget.ButtonState
import com.boyasec.test.ui.widget.StateButton
import com.boyasec.test.viewmodels.LoginViewModel
import com.boyasec.test.viewmodels.LoginViewModelFactory
import kotlinx.coroutines.launch

/**
 * 登录页
 *
 */
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(navController: NavController) {
    // compose协程作用域
    val composableScope = rememberCoroutineScope()

    val loginViewModel = viewModel(
        modelClass = LoginViewModel::class.java, factory = LoginViewModelFactory()
    )
    Column {
        AppBar(title = stringResource(id = R.string.login))
        Box(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Column {
                val keyboard = LocalSoftwareKeyboardController.current
                val accountText = rememberSaveable {
                    mutableStateOf("")
                }
                val passwordText = rememberSaveable {
                    mutableStateOf("")
                }
                val accountIsValid = remember {
                    mutableStateOf(true)
                }
                val showPassword = remember {
                    mutableStateOf(false)
                }
                // 账号输入
                OutlinedTextField(value = accountText.value,
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        if (!accountIsValid.value) {
                            Text(text = stringResource(id = R.string.email_invalid))
                        } else {
                            Text(text = stringResource(id = R.string.account_label))
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
                    ),
                    isError = !accountIsValid.value,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Email,
                            contentDescription = "email",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    onValueChange = {
                        accountText.value = it
                        accountIsValid.value = it.isEmail()
                    })
                Spacer(modifier = Modifier.height(20.dp))
                // 密码输入
                OutlinedTextField(value = passwordText.value,
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (!showPassword.value) PasswordVisualTransformation() else VisualTransformation.None,
                    label = {
                        Text(
                            text = stringResource(id = R.string.password_label)
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    trailingIcon = {
                        IconButton(onClick = { showPassword.value = !showPassword.value }) {
                            if (showPassword.value) {
                                Icon(
                                    painter = painterResource(id = R.drawable.unlock),
                                    contentDescription = "unlock",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            } else {
                                Icon(
                                    painter = painterResource(id = R.drawable.lock),
                                    contentDescription = "lock",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    },
                    onValueChange = {
                        passwordText.value = it
                    })
                Spacer(modifier = Modifier.height(25.dp))
                // 登录
                val buttonColors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContainerColor = Grey,
                    disabledContentColor = MaterialTheme.colorScheme.onErrorContainer
                )
                val context = LocalContext.current
                val buttonState = remember {
                    mutableStateOf(ButtonState.Normal)
                }
                StateButton(text = stringResource(id = R.string.login),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp),
                    shape = RoundedCornerShape(8.dp),
                    enabled = accountIsValid.value && passwordText.value.isNotBlank(),
                    colors = buttonColors,
                    state = buttonState,
                    onClick = {
                        keyboard?.hide()
                        composableScope.launch {
                            loginViewModel.login(accountText.value, passwordText.value)
                                .collect { result ->
                                    buttonState.value = ButtonState.Normal
                                    when (result) {
                                        is Result.Success -> {
                                            navController.navigate(
                                                "${RouteConfig.ROUTE_MAIN}/${result.data.account}/${result.data.password}"
                                            )
                                        }
                                        is Result.Error -> {
                                            Toast.makeText(
                                                context,
                                                "Login failed error ${result.exception.localizedMessage}",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                }
                        }
                    })
            }
        }

    }
}