package com.example.pokemonapi

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokemonapi.api.RetrofitHelper
import com.example.pokemonapi.data.Result
import com.example.pokemonapi.`interface`.Retrofitservice
import com.example.pokemonapi.ui.theme.PokemonAPITheme
import com.google.gson.Gson


class MainActivity : ComponentActivity() {

    var pokemonList: List<Result> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var pokemonList: List<Result> = emptyList()

        setContent {
            PokemonAPITheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background) {


                    val pokemonApi = remember { RetrofitHelper.getInstance().create(Retrofitservice::class.java) }
                    var pokemonList: List<Result> = emptyList()

                    LaunchedEffect(pokemonApi) {

                            val result = pokemonApi.getPokemonList()
                            if (result != null && result.isSuccessful) {
                                val data = result.body()
                                if (data != null) {
                                    // Parse the API response into PokemonModel
                                    val parsedData = data.results.map { result ->
                                        Result(
                                            name = result.name,
                                            url = result.url
                                              )
                                    }
                                    pokemonList = parsedData


                                    Log.e("TAG", "onCreate: " + Gson().toJson(pokemonList))
                                }
                            }

                    }
                    Log.e("TAG", "onCreate: called " )
                    PokemonList()

                }
            }
        }
    }
}


@Composable
fun PokemonList() {
    Column(modifier = Modifier
        .verticalScroll(state = rememberScrollState()))
    {
        for (i in 1..10) {
            ItemList()
        }

    }

}

@Composable
fun ItemList() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Set the background color to white
       ) {
        Card(elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp),
             modifier = Modifier
                 .padding(10.dp)
                 .fillMaxWidth()
                 .wrapContentHeight()
                 .background(Color.White)
            ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(Color.White)
                    .padding(10.dp)
               ) {

                Image(painter = painterResource(id = R.drawable.ic_launcher_background),
                      contentDescription = "",
                      modifier = Modifier
                          .padding(16.dp)
                          .size(50.dp)
                          .fillMaxSize())
                Text(
                    text = "Name",
                    color = Color.Black,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxSize()

                    )
            }

        }
    }


}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column(modifier = Modifier.background(color = Color.Cyan))

    {

        Text(
            text = "Hello $name!",
            color = Color.Black,
            fontSize = 30.sp,
            modifier = Modifier
                .padding(16.dp)
                .size(400.dp)
                .fillMaxSize()

            )

        Text(
            text = "Some Other text",
            color = Color.Blue,
            fontSize = 30.sp,
            modifier = Modifier.fillMaxSize()
            )
    }


}


@Composable
fun GreetingPreview() {
    PokemonAPITheme {
        PokemonList()
    }
}