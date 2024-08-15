package com.example.shadowparklogin

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shadowparklogin.ui.theme.GreenPk
import com.example.shadowparklogin.ui.theme.ShadowParkLoginTheme
import java.net.Authenticator

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShadowParkLoginTheme {
                Box (modifier = Modifier
                    .fillMaxSize()
                    .paint(
                        painterResource(id = R.drawable.parkhere),
                        contentScale = ContentScale.FillBounds
                    )
                ) {
                    val navController = rememberNavController()
                    NavGraph(navController = navController)

                    myBottomAppBar()
                }
            }
        }
    }
}

@Composable
fun LoginScreen(onLoginSuccess : () -> Unit) {
    var username by remember {
        mutableStateOf("")
    }
    var  password by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current.applicationContext

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 26.dp, vertical = 142.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally) {

        OutlinedTextField(value = username, onValueChange ={username = it},
            label = { Text(text = "Username")}, shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.colors(
                focusedLeadingIconColor = GreenPk,
                unfocusedLeadingIconColor = GreenPk,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = GreenPk,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Blue,
                unfocusedIndicatorColor = Color.Blue,
                unfocusedPlaceholderColor = GreenPk
            ), leadingIcon = {
                Icon(imageVector = Icons.Default.Person, contentDescription = "Username")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        )
        OutlinedTextField(value = password, onValueChange = {password = it},
            label = { Text(text = "Password")}, shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Blue,
                unfocusedIndicatorColor = Color.Blue,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = GreenPk,
                unfocusedPlaceholderColor = GreenPk,
                focusedLeadingIconColor = GreenPk,
                unfocusedLeadingIconColor = GreenPk
            ), leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription ="Password" )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            visualTransformation = PasswordVisualTransformation()
        )
        Button(onClick = {
            if (Validate(username, password)){
                onLoginSuccess()
                Toast.makeText(context, "Login Successfull!!!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context,"Check Your Credentials!!", Toast.LENGTH_SHORT).show()
            }
        }, colors = ButtonDefaults.buttonColors(Color.Blue),
            contentPadding = PaddingValues(start = 60.dp, end = 60.dp, top = 10.dp, bottom = 10.dp),
            modifier = Modifier.padding(top = 18.dp)
        ) {
            Text(text = "Login", fontSize = 22.sp)
        }
    }
}
private fun Validate(username: String, password: String): Boolean {
    val validUsername = "SuperUser"
    val validPassword = "Liz1234"
    return username == validUsername && password == validPassword
}

@Composable
private fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(onLoginSuccess = {
                navController.navigate("home") {
                    popUpTo(id)
                }
            })
        }
        composable("home") {
            HomeScreen()
        }
    }
}
@Composable
fun myBottomAppBar(){
    val navigationController = rememberNavController()
    val context = LocalContext.current.applicationContext
    val selected = remember {
        mutableStateOf(Icons.Default.Home)
    }
    Scaffold (bottomBar = {
        BottomAppBar(
            containerColor = GreenPk) {
            IconButton(
                onClick = {
                    selected.value = Icons.Default.Home
                    navigationController.navigate(Screens.Home.screen){
                        popUpTo(0)
                    }
                },
                modifier = Modifier.weight(1f) ) {
                Icon(Icons.Default.Home, contentDescription = null, modifier = Modifier.size(26.dp),
                    tint = if (selected.value == Icons.Default.Home) Color.White else Color.DarkGray)

            }
            IconButton(onClick = {
                selected.value = Icons.Default.Search
                navigationController.navigate(Screens.Search.screen){
                    popUpTo(0)
                }
            }, modifier = Modifier.weight(1f) ) {
                Icon(Icons.Default.Search, contentDescription = null, modifier = Modifier.size(26.dp),
                    tint = if (selected.value == Icons.Default.Search) Color.White else Color.DarkGray)
                }
            Box(modifier = Modifier
                .weight(1f)
                .padding(16.dp),
                contentAlignment = Alignment.Center){
                FloatingActionButton(onClick = {Toast.makeText(context, "View Data", Toast.LENGTH_SHORT).show()}) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = GreenPk )
                }
            }
            IconButton(onClick = {
                selected.value = Icons.Default.Notifications
                navigationController.navigate(Screens.Notification.screen){
                    popUpTo(0)
                }
            }, modifier = Modifier.weight(1f) ) {
                Icon(Icons.Default.Notifications, contentDescription = null, modifier = Modifier.size(26.dp),
                    tint = if (selected.value == Icons.Default.Notifications) Color.White else Color.DarkGray)
            }
            IconButton(onClick = {
                selected.value = Icons.Default.Person
                navigationController.navigate(Screens.Profile.screen){
                    popUpTo(0)
                }
            }, modifier = Modifier.weight(1f) ) {
                Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(26.dp),
                    tint = if (selected.value == Icons.Default.Person) Color.White else Color.DarkGray)
            }
        }
    }){paddingValues ->
        NavHost(navController = navigationController, startDestination =Screens.Home.screen,
            modifier = Modifier.padding(paddingValues)) {
            composable(Screens.Home.screen){ HomeScreen()}
            composable(Screens.Search.screen){ search() }
            composable(Screens.Notification.screen){ Notification() }
            composable(Screens.Profile.screen){ Profile()}


        }


    }
}
@Preview
@Composable
fun ShadowParkLoginTheme() {
    ShadowParkLoginTheme{

        myBottomAppBar()
    }
}