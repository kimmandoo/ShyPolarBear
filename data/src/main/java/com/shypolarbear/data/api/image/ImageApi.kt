package com.shypolarbear.data.api.image

import com.shypolarbear.domain.model.image.ImageDeleteRequest
import com.shypolarbear.domain.model.image.ImageDeleteResponse
import com.shypolarbear.domain.model.image.ImageModifyRequest
import com.shypolarbear.domain.model.image.ImageModifyResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Multipart
import retrofit2.http.PUT

interface ImageApi {

    @Multipart
    @PUT("/api/images")
    suspend fun imageModify(
        @Body
        imageModifyRequest: ImageModifyRequest,
    ): Response<ImageModifyResponse>

    @DELETE("/api/images")
    suspend fun imageDelete(
        @Body
        imageDeleteRequest: ImageDeleteRequest,
    ): Response<ImageDeleteResponse>

}