package com.spaceapps.myapplication.core.repositories.auth

import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.messaging.FirebaseMessaging
import com.spaceapps.myapplication.core.local.DataStoreManager
import com.spaceapps.myapplication.core.models.remote.auth.AuthRequest
import com.spaceapps.myapplication.core.models.remote.auth.DeviceRequest
import com.spaceapps.myapplication.core.models.remote.auth.ResetPasswordRequest
import com.spaceapps.myapplication.core.models.remote.auth.SocialSignInRequest
import com.spaceapps.myapplication.core.network.calls.AuthorizationCalls
import com.spaceapps.myapplication.core.repositories.auth.results.*
import com.spaceapps.myapplication.core.utils.DispatchersProvider
import com.spaceapps.myapplication.core.utils.Error
import com.spaceapps.myapplication.core.utils.Success
import com.spaceapps.myapplication.core.utils.request
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val calls: AuthorizationCalls,
    private val dataStoreManager: DataStoreManager,
    private val dispatchersProvider: DispatchersProvider
) : AuthRepository {

    override suspend fun signIn(email: String, password: String): SignInResult =
        withContext(dispatchersProvider.io) {
            val request = AuthRequest(
                email = email,
                password = password,
                device = provideDeviceModel()
            )
            when (val response = request { calls.signIn(request = request) }) {
                is Success -> {
                    dataStoreManager.storeTokens(response = response.data)
                    SignInResult.Success
                }
                is Error -> SignInResult.Failure
            }
        }

    override suspend fun signUp(email: String, password: String): SignUpResult =
        withContext(dispatchersProvider.io) {
            val request = AuthRequest(
                email = email,
                password = password,
                device = provideDeviceModel()
            )
            when (val response = request { calls.signUp(request = request) }) {
                is Success -> {
                    dataStoreManager.storeTokens(response = response.data)
                    SignUpResult.Success
                }
                is Error -> SignUpResult.Failure
            }
        }

    override suspend fun signInWithGoogle(accessToken: String): SocialSignInResult =
        withContext(dispatchersProvider.io) {
            val request = SocialSignInRequest(
                accessToken = accessToken,
                device = provideDeviceModel()
            )
            when (
                val response =
                    request { calls.googleSignIn(request = request) }
            ) {
                is Success -> {
                    dataStoreManager.storeTokens(response = response.data)
                    SocialSignInResult.Success
                }
                is Error -> SocialSignInResult.Failure
            }
        }

    override suspend fun signInWithFacebook(accessToken: String): SocialSignInResult =
        withContext(dispatchersProvider.io) {
            val request = SocialSignInRequest(
                accessToken = accessToken,
                device = provideDeviceModel()
            )
            when (
                val response =
                    request { calls.facebookSignIn(request = request) }
            ) {
                is Success -> {
                    dataStoreManager.storeTokens(response = response.data)
                    SocialSignInResult.Success
                }
                is Error -> SocialSignInResult.Failure
            }
        }

    override suspend fun signInWithApple(accessToken: String): SocialSignInResult =
        withContext(dispatchersProvider.io) {
            val request = SocialSignInRequest(
                accessToken = accessToken,
                device = provideDeviceModel()
            )
            when (val response = request { calls.appleSignIn(request = request) }) {
                is Success -> {
                    dataStoreManager.storeTokens(response = response.data)
                    SocialSignInResult.Success
                }
                is Error -> SocialSignInResult.Failure
            }
        }

    override suspend fun sendResetCode(email: String): SendResetCodeResult =
        withContext(dispatchersProvider.io) {
            when (request { calls.sendResetCode(email = email) }) {
                is Success -> SendResetCodeResult.Success
                is Error -> SendResetCodeResult.Failure
            }
        }

    override suspend fun verifyResetCode(email: String, code: String): VerifyResetCodeResult =
        withContext(dispatchersProvider.io) {
            when (request { calls.verifyResetCode(email = email, resetCode = code) }) {
                is Success -> VerifyResetCodeResult.Success
                is Error -> VerifyResetCodeResult.Failure
            }
        }

    override suspend fun resetPassword(
        email: String,
        code: String,
        password: String
    ): ResetPasswordResult = withContext(dispatchersProvider.io) {
        val request =
            ResetPasswordRequest(email = email, resetCode = code, newPassword = password)
        when (request { calls.resetPassword(request = request) }) {
            is Success -> ResetPasswordResult.Success
            is Error -> ResetPasswordResult.Failure
        }
    }

    override suspend fun logOut(): LogOutResult = withContext(dispatchersProvider.io) {
        val response = request {
            calls.logOut(installationId = FirebaseInstallations.getInstance().id.await())
        }
        when (response) {
            is Success -> LogOutResult.Success
            is Error -> LogOutResult.Failure
        }
    }

    private suspend fun provideDeviceModel() = DeviceRequest(
        token = FirebaseMessaging.getInstance().token.await(),
        installationId = FirebaseInstallations.getInstance().id.await()
    )
}