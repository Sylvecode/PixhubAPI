package com.example.pixhubapi.model


//import org.springframework.security.crypto.factory.PasswordEncoderFactories
//import org.springframework.security.crypto.password.PasswordEncoder
import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import java.security.MessageDigest
import java.security.SecureRandom
import java.security.spec.KeySpec
import java.util.*
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec


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
interface AccountRepository : JpaRepository<AccountBean, Long> {
    fun findByUsername(username : String): AccountBean?
}



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
        val salt = username.toByteArray(Charsets.UTF_8)
        val spec: KeySpec = PBEKeySpec(password.toCharArray(), salt,65536, 128)
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        val hash = factory.generateSecret(spec).encoded

        // Encoder le haché en Base64 pour le stockage en tant que chaîne
        val hashBase64 = Base64.getEncoder().encodeToString(hash)

        val account = AccountBean(null, username, familyName, name, email, hashBase64)
        accountRep.save(account)
        return account
    }

    fun updateAccount(updatedAccount: AccountBean): AccountBean {
        // Supposons que l'ID de l'utilisateur est contenu dans updatedAccount.id
        val existingAccountOptional = updatedAccount.id?.let { accountRep.findById(it) } ?: throw IllegalArgumentException("Account not found")

        // Déballer l'Optional
        val existingAccount = existingAccountOptional.orElseThrow { IllegalArgumentException("Account not found") }

        // Mettez à jour les propriétés non nulles
        if (updatedAccount.name != null) {
            existingAccount.name = updatedAccount.name
        }
        if (updatedAccount.familyName != null) {
            existingAccount.familyName = updatedAccount.familyName
        }
        // Sauvegardez l'objet mis à jour dans la base de données
        accountRep.save(existingAccount)
        return existingAccount
    }

    fun deleteAccount(account: AccountBean) {
        accountRep.delete(account)
    }

    fun login(username: String, password: String): AccountBean? {
        if (username.isNullOrBlank()) {
            throw Exception("Veuillez rentrer un identifiant")
        } else if (password.isNullOrBlank()) {
            throw Exception("Veuillez rentrer un mot de passe")
        }
       // val account = accountRep.findByUsernameAndPassword(username, password)
        val account = accountRep.findByUsername(username)?: return null
        // Utiliser le username comme sel
        val salt = username.toByteArray(Charsets.UTF_8)
        // Récupérer le mot de passe haché stocké
        val spec: KeySpec = PBEKeySpec(password.toCharArray(), salt, 65536, 128)
        // Recrée le hash à partir du mot de passe saisi par l'utilisateur
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        val generatedHash = factory.generateSecret(spec).encoded

        // Récupére le mot de passe haché stocké
        val storedHash = Base64.getDecoder().decode(account.password)

        // Compare le hash généré avec le hash stocké
        if (MessageDigest.isEqual(generatedHash, storedHash)) {
            return account
        } else {
            return null
        }
    }


}



