package nz.ac.canterbury.seng440.examcompose

import android.annotation.SuppressLint
import android.content.Context
import android.content.Loader
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import nz.ac.canterbury.seng440.examcompose.ui.theme.ExamComposeTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExamComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

data class ApplicationState (
    val matchedList: List<String> = emptyList(),
)

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyApp() {
    var wordText by rememberSaveable { mutableStateOf("") }
    var matchText by rememberSaveable { mutableStateOf("") }
    val WordList: MutableList<String> = emptyList<String>().toMutableList()
    val MatchList: MutableList<String> = emptyList<String>().toMutableList()

    var wordListt by rememberSaveable { mutableStateOf(WordList)  }
    var matchListt by rememberSaveable { mutableStateOf(MatchList)  }

    var context = LocalContext.current

    var random = 0

    val navController = rememberNavController()

    Scaffold (
        topBar = { TopAppBar(title = { Text(text = "MatchGame", color = Color.White) }, colors = TopAppBarDefaults.mediumTopAppBarColors(Color.Red))},
        content = {
            NavHost(navController = navController, startDestination = "main_screen") {
                composable("main_screen") {
                    BackHandler {}
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 70.dp)
                        .fillMaxWidth()) {
                        Button(

                            colors = ButtonDefaults.buttonColors(Color.Red),

                            onClick = {

                            if (wordListt.size <= 3) {
                                Toast.makeText(context, "There must be at least 4 matches", Toast.LENGTH_SHORT).show()
                            } else {

                                random = Random.nextInt(0, wordListt.size);

                                println(random)

                                navController.navigate("playing_screen")
                            }



                                         }, modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp)
                                .border(7.dp, color = Color.White, shape = RectangleShape)) {
                            Text(text = "PLAY MATCH")
                        }

                        TextField(value = wordText, onValueChange = {wordText = it}, placeholder = { Text(text = "Word") },
                            maxLines = 1, modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                            colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent))

                        TextField(value = matchText, onValueChange = {matchText = it}, placeholder = { Text(text = "Match") },
                            maxLines = 1, modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                            colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent))



                        Button(onClick = {

                                         if (wordText != "" && matchText != "") {



                                             wordListt = wordListt.toMutableList().apply { add(wordText) }
                                             matchListt = matchListt.toMutableList().apply { add(matchText) }

                                             println(wordListt)
                                             println(matchListt)

                                             wordText = ""
                                             matchText = ""

                                         } else if (wordText == "" && matchText == "") {
                                             Toast.makeText(context, "Cannot be empty", Toast.LENGTH_SHORT).show()
                                         } else if (wordText == "") {
                                             Toast.makeText(context, "New word cannot be empty", Toast.LENGTH_SHORT).show()
                                         } else if (matchText == "") {
                                             Toast.makeText(context, "New match cannot be empty", Toast.LENGTH_SHORT).show()
                                         }


                        }, modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                            .border(7.dp, color = Color.White, shape = RectangleShape),
                                colors = ButtonDefaults.buttonColors(Color.Red),) {
                            Text(text = "ADD NEW WORD")
                        }


                        LazyColumn() {
                            items(wordListt.size) {index ->
                                val wordd = wordListt[index]
                                val matchh = matchListt[index]
                                Row (verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp)){
                                    Button(
                                        colors = ButtonDefaults.buttonColors(Color.Red),
                                        modifier = Modifier
                                            .align(
                                                Alignment.CenterVertically
                                            )
                                            .border(
                                                10.dp,
                                                color = Color.White,
                                                shape = RectangleShape
                                            ),
                                        onClick = {
                                            wordListt = wordListt.toMutableList().apply { removeAt(index) }
                                            matchListt = matchListt.toMutableList().apply { removeAt(index) }
                                        println(wordListt)
                                        println(matchListt)
                                    }) {
                                        Text(text = "x", style = TextStyle(fontSize = 16.sp))
                                    }
                                    Spacer(modifier = Modifier.width(5.dp))
                                    Text(text = wordd + " ~ " + matchh, style = TextStyle(fontSize = 23.sp))
                                }

                            }
                        }


                    }
                }

                composable("playing_screen") { PlayGameScreen(navController, wordListt, matchListt, random) }
            }

        }
    )
}

@SuppressLint("MutableCollectionMutableState")
@Composable
fun PlayGameScreen(navController: NavController, WordList: MutableList<String>, MatchList: MutableList<String>, randomIndex: Int) {
    var wordListt by rememberSaveable { mutableStateOf(WordList)  }
    var matchListt by rememberSaveable { mutableStateOf(MatchList)  }
    var showDialog by rememberSaveable { mutableStateOf(false) }
    var correct by rememberSaveable { mutableStateOf(false) }

    var shuffled by rememberSaveable {
        mutableStateOf(MatchList.shuffled())
    }



    Column (modifier = Modifier
        .fillMaxSize()
        .padding(top = 70.dp)) {


        Text(text = "The match of " + wordListt[randomIndex] + " is:", style = TextStyle(fontSize = 25.sp))
        LazyColumn() {
            items(matchListt.size) { index ->
                val item = shuffled[index]
                var bool by rememberSaveable {
                    mutableStateOf(false)
                }
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ){
                    RadioButton(selected = bool, onClick = {

                        bool = true


                        if (item == matchListt[randomIndex]) {
                            correct = true
                            showDialog = true
                        } else {
                            showDialog = true

                        }


                    })
                    Text(text = item)
                }

            }
        }

        if (showDialog) {
            Dialog(onDismissRequest = {
                if (correct) {
                    showDialog = false
                    navController.navigate("main_screen")
                } else {
                    showDialog = false
              }
            }) {

                Box(modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .background(Color.White)) {
                    Column (horizontalAlignment = Alignment.End, modifier = Modifier.padding(5.dp), verticalArrangement = Arrangement.SpaceBetween){
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(if (correct) "You picked the right answer. Nice work!" else "Uh oh. You might need to study more.",
                                style = TextStyle(fontSize = 16.sp), color = Color.Black)
                        }

                        Button(modifier = Modifier.border(
                            10.dp,
                            color = Color.White,
                            shape = RectangleShape
                        ),
                            onClick = {
                            if (correct) {
                                showDialog = false
                                navController.navigate("main_screen")
                            } else {
                                showDialog = false
                            }

                        }) {
                            Text(if (correct) "OKAY" else "TRY AGAIN")
                        }

                    }
                }

            }
        }
    }
}