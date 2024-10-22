import Common.createTestUser
import auth.AuthRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import user.UserRepository

class PostgresAuthRepositoryTest : FunSpec({
    val testDB = DBConnection.getDatabaseObject("admin", "admin", "users_and_groups")
    val userRepository: UserRepository = PostgresUserRepository(testDB)
    val authRepository: AuthRepository = PostgresAuthRepository(testDB)

    context("checkCredentials") {
        test("should return true if credentials are correct") {
            val user = createTestUser(email = "email1", password = "password123")
            userRepository.save(user)

            val result = authRepository.checkCredentials("email1", "password123")

            result shouldBe true
        }

        test("should return false if email is incorrect") {
            val user = createTestUser(email = "email2", password = "password123")
            userRepository.save(user)

            val result = authRepository.checkCredentials("incorrect-email", "password123")

            result shouldBe false
        }

        test("should return false if password is incorrect") {
            val user = createTestUser(email = "email3", password = "password123")
            userRepository.save(user)

            val result = authRepository.checkCredentials("email3", "password456")

            result shouldBe false
        }
    }
})
