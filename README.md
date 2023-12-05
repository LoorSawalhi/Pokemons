# Pokemon Task
In this task we are going to build application for Pokemon :) , this application will contains only three screens :
Splash screen. 
Main Pokemon list screen.
Details Pokemon screen.
## API:
We will use a free Api called PokeApi , a public API that provides information on all things Pokemon. For this project specifically, we will be limiting our scope to using only the Pokemon and Pokedex related information.


## Getting Data:
To get the data we want from the API, we need to be able to access the website properly. 


We will use the below api’s only :


1- To fetch the Pokemon list we will use below api. Note: (limit & offset for pagination data loading).
https://pokeapi.co/api/v2/pokemon?limit=20&offset=0 .

2- To fetch Pokemon details we will use below api. 
https://pokeapi.co/api/v2/pokemon/{pokemon_name} 


## Libraries must be used:
1- Retrofit  (For network request calls )
2- Glide (For Image loading)

