package com.example.pixhubapi.apirest

import com.example.pixhubapi.model.AccountBean
import com.example.pixhubapi.model.AccountService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.sql.Date


@RestController
class PixhubRestController (val accountService: AccountService){

    //http://localhost:8080/test
    @GetMapping("/test")
    fun testMethode(): String {
        println("/test")

        return "<b>helloWorld</b>"
    }


    //http://localhost:8080/addGame?equipe1=Lyon&equipe2=Marseille&date=2024-03-23
    //Créer un compte en rentrant le nom le pseudo(username), le nom de famille, le prénom, l'email et le mot de passe en paramètre
    @PostMapping("/addAccount")
    fun addAccount(@RequestBody accountBean: AccountBean): AccountBean{
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
}