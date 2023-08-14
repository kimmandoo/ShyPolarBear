package com.shypolarbear.android.di

import com.shypolarbear.domain.usecase.AccessTokenUseCase
import com.shypolarbear.domain.usecase.RefreshTokenUseCase
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val accessTokenUseCase: AccessTokenUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // TODO("TokenRepoImpl에서 토큰 가져오는 동작 구현되면 주석 해제하기")
//        val accessToken = accessTokenUseCase.loadAccessToken()
//        val refreshToken = refreshTokenUseCase.loadRefreshToken()

//        val request = chain.request().newBuilder().addHeader("accessToken","$accessToken").build()
        val addedAccessTokenRequest = chain.request().newBuilder().addHeader("accessToken","testHeader").build()
        val response = chain.proceed(addedAccessTokenRequest)

        when (response.code) {
            401 -> {
                // Access Token 갱신하는 동작
            }
        }

        Timber.d("헤더에 잘 붙음?: $addedAccessTokenRequest")

        return response
    }
}