package com.example.pixhubapi.model

import jakarta.persistence.*
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import java.sql.Date


@Entity
@Table(name = "user_account")
data class AccountBean(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var username: String = "",
    var familyName: String = "",
    var name: String = "",
    var email: String = "",
    var password: String = ""
)


@Repository
interface AccountRepository : JpaRepository<AccountBean, Long>



@Service
class AccountService(val accountRep: AccountRepository) {

    fun createAccount(username: String, familyName: String, name: String, email: String, password: String): AccountBean {
       if (username.isNullOrBlank()) {
            throw Exception("Veuillez rentrer un identifiant")
        } else if (familyName.isNullOrBlank()) {
            throw Exception("Veuillez rentrer un nom de famille")
        } else if (name.isNullOrBlank()) {
           throw Exception("Veuillez rentrer un prénom")
       } else if (email.isNullOrBlank()) {
           throw Exception("Veuillez rentrer un email")
       } else if (password.isNullOrBlank()) {
           throw Exception("Veuillez rentrer un mot de passe")
       }
        val account = AccountBean(null, username, familyName, name, email, password)
        accountRep.save(account)
        return account
    }

}