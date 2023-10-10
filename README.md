### Movie App (TMdb) with Clean Architecture & Kotlin-Flow

### It is an app that lists all upcoming movies using The Movie Db API (TMdb) and then navigate to book movie tickets.
1. Movie List Screen: List down all upcoming movies on the list 					
Movie List API: https://api.themoviedb.org/3/movie/upcoming
Params: api_key - 123456abcdefg (dummy, you can create your one key for free please refer to the themoviedb documentation).

2. Movie Detail Screen: When a user selects any movie from the movie list screen, it navigates to the Movie detail screen. After pressing the “​Watch Trailer​” button on the movie details screen, the application should display a full-screen movie player and should automatically start the playback (to get the needed URLs to use the “movie/#MOVIE_ID#/videos” API call). After the trailer is finished the player should be automatically closed, and the app should return to the detail page. The playback can be also canceled by pressing the “Done” button.

3. Movie Details API : https://api.themoviedb.org/3/movie/<movie-id>
Params: api_key - 123456abcdefg (dummy)

4. Get Images API : https://api.themoviedb.org/3/movie/<movie-id>/images
Params: api_key - 123456abcdefg (dummy)

5. Movie search screen 
