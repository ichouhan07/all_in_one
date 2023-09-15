package com.applligent.hilt_di_retrofit_viewmodel.biometric

sealed class BiometricFormState

data class FailedLoginFormState(
    val usernameError: Int? = null,
    val passwordError: Int? = null
) : BiometricFormState()

data class SuccessfulLoginFormState(
    val isDataValid: Boolean = false
) : BiometricFormState()