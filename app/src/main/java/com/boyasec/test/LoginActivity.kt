package com.boyasec.test

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.ActivityNavigatorExtras
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.boyasec.test.data.Result
import com.boyasec.test.data.RouteConfig
import com.boyasec.test.extentions.isEmail
import com.boyasec.test.ui.theme.LoginTestTheme
import com.boyasec.test.ui.widget.StateButton
import com.boyasec.test.viewmodels.LoginViewModel
import com.boyasec.test.viewmodels.LoginViewModelFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

/**
 * 登录界面
 * @author Hey
 */
class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController, startDestination = RouteConfig.ROUTE_LOGIN
                    ) {
                        composable(RouteConfig.ROUTE_LOGIN) { LoginPage(navController = navController) }
                        composable(
                            "${RouteConfig.ROUTE_MAIN}/{account}/{password}", arguments = listOf(
                                navArgument("account") { type = NavType.StringType },
                                navArgument("password") { type = NavType.StringType })
                        ) {
                            val account = it.arguments?.getString("account")
                            val password = it.arguments?.getString("password")
                            Greeting(
                                name = account, password = password, navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(navController: NavController) {

    val composableScope = rememberCoroutineScope()

    val loginViewModel = viewModel(
        modelClass = LoginViewModel::class.java, factory = LoginViewModelFactory()
    )
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = stringResource(id = R.string.login))
        }, navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "back",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        })
    }) { padding ->
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
                    disabledContainerColor = MaterialTheme.colorScheme.errorContainer,
                    disabledContentColor = MaterialTheme.colorScheme.onErrorContainer
                )
                val context = LocalContext.current
                StateButton(text = stringResource(id = R.string.login),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    shape = RoundedCornerShape(8.dp),
                    enabled = accountIsValid.value && passwordText.value.isNotBlank(),
                    colors = buttonColors,
                    onClick = {
                        keyboard?.hide()
                        composableScope.launch {
                            loginViewModel.login(accountText.value, passwordText.value)
                                .collect { result ->
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val navController = rememberNavController()
    LoginTestTheme {
        LoginPage(navController)
    }
}


