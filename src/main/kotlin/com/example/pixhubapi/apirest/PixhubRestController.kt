package com.example.pixhubapi.apirest

import com.example.pixhubapi.model.AccountBean
import com.example.pixhubapi.model.AccountService
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.web.bind.annotation.*


@RestController
class PixhubRestController(val accountService: AccountService) {

    private val client = OkHttpClient()

    //http://localhost:8080/test
    @GetMapping("/test")
    fun testMethode(): String {
        println("/test")

        return "<b>helloWorld</b>"
    }


    //http://localhost:8080/addAccount
    //Créer un compte en rentrant le nom le pseudo(username), le nom de famille, le prénom, l'email et le mot de passe dans le body
    @PostMapping("/addAccount")
    fun addAccount(@RequestBody accountBean: AccountBean): AccountBean {
        require(!accountBean.username.isNullOrBlank()) { "Veuillez rentrer un identifiant" }
        require(!accountBean.familyName.isNullOrBlank()) { "Veuillez rentrer un nom de famille" }
        require(!accountBean.name.isNullOrBlank()) { "Veuillez rentrer un prénom" }
        require(!accountBean.email.isNullOrBlank()) { "Veuillez rentrer un email" }
        require(!accountBean.password.isNullOrBlank()) { "Veuillez rentrer un mot de passe" }

        return accountService.createAccount(
            accountBean.username,
            accountBean.familyName,
            accountBean.name,
            accountBean.email,
            accountBean.password
        )
    }

    @PatchMapping("/updateAccount")
    fun updateAccount(@RequestBody accountBean: AccountBean): AccountBean {
        require(!accountBean.familyName.isNullOrBlank()) { "Veuillez rentrer un nom de famille" }
        require(!accountBean.name.isNullOrBlank()) { "Veuillez rentrer un prénom" }

        return accountService.updateAccount(
            accountBean
        )
    }

    @DeleteMapping("/deleteAccount")
    fun deleteAccount(@RequestBody account: AccountBean) {

         accountService.deleteAccount(account)
    }

    //http://localhost:8080/login
    @PostMapping("/login")
    fun login(@RequestBody params: Map<String, String>): AccountBean? {
        val username = params["username"]
        val password = params["password"]

        require(!username.isNullOrBlank()) { "Veuillez rentrer un identifiant" }
        require(!password.isNullOrBlank()) { "Veuillez rentrer un mot de passe" }

        return accountService.login(
            username,
            password
        )
    }

    @GetMapping("/upcomingMovies")
    fun upcomingMovies(): String {
        val url = "https://api.themoviedb.org/3/movie/upcoming" +
                "?language=fr-FR&page=1&region=FR&sort_by=popularity.desc"


        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5MTA2Mzg5NjQ5ZWFlMWQ4MWYwN2M4NTk3OGE2MDgwZSIsIm5iZiI6MTcxOTg0MTk1Mi40MTYwODgsInN1YiI6IjY1NTIzMzI1ZWE4NGM3MTA5NTliZGRkOSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.P1gKfod1K6nhpGcPO-ZE4_MqQu920TjGp-auev7txqs")
            .addHeader("accept", "application/json")
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val responseBodyString = response.body.string()
            return responseBodyString
        }
    }

    @GetMapping("/getMovieDetails")
    fun getMovieDetails(@RequestParam movieId: Int): String {
        val url = "https://api.themoviedb.org/3/movie/$movieId?language=fr-FR"


        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5MTA2Mzg5NjQ5ZWFlMWQ4MWYwN2M4NTk3OGE2MDgwZSIsIm5iZiI6MTcyMTc0MTI5NS41MDk2NjUsInN1YiI6IjY1NTIzMzI1ZWE4NGM3MTA5NTliZGRkOSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.hAuJRoFcy617MGl2088cf5i4xlwnJpp_lCORBuosLS4")
            .addHeader("accept", "application/json")
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val responseBodyString = response.body.string()
            return responseBodyString
        }
    }

    @GetMapping("/getArtistDetails")
    fun getArtistDetails(@RequestParam artistId: Int): String {
        val url = "https://api.themoviedb.org/3/person/$artistId?language=fr-FR"


        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5MTA2Mzg5NjQ5ZWFlMWQ4MWYwN2M4NTk3OGE2MDgwZSIsIm5iZiI6MTcyMTc0MTI5NS41MDk2NjUsInN1YiI6IjY1NTIzMzI1ZWE4NGM3MTA5NTliZGRkOSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.hAuJRoFcy617MGl2088cf5i4xlwnJpp_lCORBuosLS4")
            .addHeader("accept", "application/json")
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val responseBodyString = response.body.string()
            return responseBodyString
        }
    }


    @GetMapping("/getMovieArtists")
    fun getMovieArtists(@RequestParam movieId: Int): String {
        val url = "https://api.themoviedb.org/3/movie/$movieId/credits?language=fr-FR"


        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5MTA2Mzg5NjQ5ZWFlMWQ4MWYwN2M4NTk3OGE2MDgwZSIsIm5iZiI6MTcyMTc0MTI5NS41MDk2NjUsInN1YiI6IjY1NTIzMzI1ZWE4NGM3MTA5NTliZGRkOSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.hAuJRoFcy617MGl2088cf5i4xlwnJpp_lCORBuosLS4")
            .addHeader("accept", "application/json")
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val responseBodyString = response.body.string()
            return responseBodyString
        }
    }

    @GetMapping("/getArtistMovies")
    fun getArtistMovies(@RequestParam artistId: Int): String {
        val url = "https://api.themoviedb.org/3/person/$artistId/movie_credits?language=fr-FR"


        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5MTA2Mzg5NjQ5ZWFlMWQ4MWYwN2M4NTk3OGE2MDgwZSIsIm5iZiI6MTcyMDcwMjM4NC40MjU5Nywic3ViIjoiNjU1MjMzMjVlYTg0YzcxMDk1OWJkZGQ5Iiwic2NvcGVzIjpbImFwaV9yZWFkIl0sInZlcnNpb24iOjF9.Ie7g_7RApnUQz5pQpLx04G2KxfQqQVvqOQGaFq4eebE")
            .addHeader("accept", "application/json")
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val responseBodyString = response.body.string()
            return responseBodyString
        }
    }


    @GetMapping("/getRecentGames")
    fun getRecentGames(): String {
        val url = "https://api.rawg.io/api/games?key=4c4473df58f14646aec94971573f50c0&dates=2024-06-01,2024-07-30"


        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val responseBodyString = response.body.string()
            return responseBodyString
        }
    }


    @GetMapping("/search")
    fun search(@RequestParam searchName: String): String {
        val baseUrl = "https://api.themoviedb.org/3/search"
        val movieUrl = "$baseUrl/movie?query=$searchName&include_adult=false&language=fr-FR&page=1"
        val personUrl = "$baseUrl/person?query=$searchName&include_adult=false&language=fr-FR&page=1"


        fun fetchResults(url: String): String {
            val request = Request.Builder()
                .url(url)
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                return response.body?.string() ?: throw IOException("Empty response")
            }
        }

        val movieResults = fetchResults(movieUrl)
        val personResults = fetchResults(personUrl)

        // Combine results
        val combinedResults = mutableMapOf<String, String>()
        combinedResults["movies"] = movieResults
        combinedResults["persons"] = personResults

        // Retourner les résultats sous forme de JSON combiné
        return combinedResults.toString() // Vous pouvez utiliser une librairie JSON pour formater proprement
    }


}
