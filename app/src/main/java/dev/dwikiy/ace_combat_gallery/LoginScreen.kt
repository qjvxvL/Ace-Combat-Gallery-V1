package dev.dwikiy.ace_combat_gallery

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    var isPlaying by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = "audio_init") {
        if (!ExoPlayerManager.isPlaying()) {
            ExoPlayerManager.prepare(context, R.raw.hush)
            ExoPlayerManager.play()
        }
        isPlaying = ExoPlayerManager.isPlaying()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ace_combat),
            contentDescription = "Login Image",
            modifier = Modifier
                .fillMaxSize()
               ,
            contentScale = ContentScale.Crop
        )

        Box (
            modifier = Modifier
                .height(600.dp)
                .fillMaxSize(0.85f)
                .clip(RoundedCornerShape(20.dp))// Clip the entire card,
        ) {

            Image(
                painter = painterResource(id = R.drawable.ace_combat),
                contentDescription = "Login Image",
                contentScale = ContentScale.Crop,
                        modifier = Modifier
                    .fillMaxSize()
                    .blur(5.dp),
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.1f))
                    , // Semi-transparent background,
            )
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val email = remember { mutableStateOf("") }
                val password = remember { mutableStateOf("") }
                val username = remember { mutableStateOf("") }
                LogicHeader()
                Spacer(modifier = Modifier.height(20.dp))
                LoginFields(
                    username = username.value,
                    email = email.value,
                    password = password.value,
                    onEmailChange = { email.value = it },
                    onPasswordChange = { password.value = it },
                    onUsernameChange = { username.value = it }
                )
                LoginFooter(
                    onSignInClick = {
                        with(Routes.MainRoute.Home) {
                            navController.toHome()
                        }
                    },

                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Don't have an account? ",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Sign Up now",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            with(Routes.MainRoute.SignUp) {
                                navController.toSignUp()
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun LoginFooter(
    onSignInClick: () -> Unit,
) {
    Spacer(modifier = Modifier.height(20.dp))
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = onSignInClick, modifier = Modifier.fillMaxWidth().padding(20.dp, 0.dp)) {
            Text(text = "Sign In")
        }

    }
}

@Composable
fun LoginFields(
    email: String,
    password: String,
    username: String,
    onUsernameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
) {
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
            onValueChange = onUsernameChange,
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
private fun LogicHeader() {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome back", fontSize = 36.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
        Text(text = "Login to your account", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
    }
}

