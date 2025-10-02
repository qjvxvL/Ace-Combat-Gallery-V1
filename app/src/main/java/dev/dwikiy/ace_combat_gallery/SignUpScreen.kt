package dev.dwikiy.ace_combat_gallery

import android.media.AudioManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.NavController
import androidx.navigation.NavHostController




@Composable
fun SignUpScreen(navController : NavController) {
    //here this page is incomplete you can create according to your use like login Screen.
    var isPlaying by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.ace_combat),
            contentDescription = "Login",
            modifier = Modifier
                .fillMaxSize()
                .blur(8.dp),
            contentScale = ContentScale.Crop
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier
                .padding(28.dp)
                .alpha(0.7f)
                .clip(
                    CutCornerShape(
                        topStart = 10.dp,
                        topEnd = 10.dp,
                        bottomStart = 10.dp,
                        bottomEnd = 10.dp
                    )
                )
                .background(MaterialTheme.colorScheme.background)
                .wrapContentHeight()
        ) {
            Column(
                modifier = Modifier
                    .padding(48.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val email = remember { mutableStateOf("") }
                val password = remember { mutableStateOf("") }
                val username = remember { mutableStateOf("") }

                LogicHeader()

                SignUpFields(
                    email = email.value,
                    password = password.value,
                    username.value,
                    onEmailChange = { email.value = it },
                    onPasswordChange = { password.value = it },
                    OnUsernameChange = { username.value = it },
                )

                SignUpFooter (
                    onSignUpClick = {
                        with(Routes.MainRoute.Login) {
                            navController.toLogin()
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun LogicHeader() {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Sign Up Now", fontSize = 36.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)

    }
}


@Composable
private fun SignUpFields(email: String, password: String, username: String, onEmailChange: (String) -> Unit, onPasswordChange: (String) -> Unit, OnUsernameChange: (String) -> Unit) {
    Column {
        TextField(
            value = email,
            label = "Email",
            placeholder = "Enter your email address",
            onValueChange = onEmailChange,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = "Email")
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = password,
            label = "Password",
            placeholder = "Enter your password",
            onValueChange = onPasswordChange,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Go
            ),
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = "Password")
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = username,
            label = "Username",
            placeholder = "Enter your username",
            onValueChange = OnUsernameChange,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = "Username")
            }
        )
    }
}

@Composable
private fun SignUpFooter(
    onSignUpClick: () -> Unit,
) {
    Spacer(modifier = Modifier.height(20.dp))
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = onSignUpClick, modifier = Modifier.fillMaxWidth().padding(20.dp, 0.dp)) {
            Text(text = "Sign Up")
        }

    }
}


