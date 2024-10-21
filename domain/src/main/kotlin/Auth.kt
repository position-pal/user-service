/**
 * Represents a secret value used for authentication.
 *
 * @property value The secret value. Must not be blank.
 * @constructor Creates a Secret with the specified value.
 * @throws IllegalArgumentException if the value is blank.
 */
data class Secret(val value: String) {
    init {
        require(value.isNotBlank()) { "Secret cannot be blank" }
    }

    /**
     * Returns the string representation of the secret value.
     *
     * @return The secret value as a string.
     */
    override fun toString(): String = value
}

/**
 * Represents an issuer value used for authentication.
 *
 * @property value The issuer value. Must not be blank.
 * @constructor Creates an Issuer with the specified value.
 * @throws IllegalArgumentException if the value is blank.
 */
data class Issuer(val value: String) {
    init {
        require(value.isNotBlank()) { "Issuer cannot be blank" }
    }

    /**
     * Returns the string representation of the issuer value.
     *
     * @return The issuer value as a string.
     */
    override fun toString(): String = value
}

/**
 * Represents an audience value used for authentication.
 *
 * @property value The audience value. Must not be blank.
 * @constructor Creates an Audience with the specified value.
 * @throws IllegalArgumentException if the value is blank.
 */
data class Audience(val value: String) {
    init {
        require(value.isNotBlank()) { "Audience cannot be blank" }
    }

    /**
     * Returns the string representation of the audience value.
     *
     * @return The audience value as a string.
     */
    override fun toString(): String = value
}
