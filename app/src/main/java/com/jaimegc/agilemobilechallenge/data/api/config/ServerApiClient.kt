package com.jaimegc.agilemobilechallenge.data.api.config

import com.jaimegc.agilemobilechallenge.data.api.error.Server4xxApiException
import com.jaimegc.agilemobilechallenge.data.api.error.Server500ApiException
import com.jaimegc.agilemobilechallenge.data.api.error.ServerConnectionApiException
import com.jaimegc.agilemobilechallenge.data.api.error.ServerUnknownApiException
import retrofit2.Call
import retrofit2.Response

open class ServerApiClient(private val serverApiConfig: ServerApiConfig) {

    companion object {
        const val RESPONSE_MAX_INTERVAL: Int = 99
        const val RESPONSE_ERROR_500: Int = 500
        const val RESPONSE_ERROR_400: Int = 400
    }

    fun <T> getApi(apiRest: Class<T>): T {
        return serverApiConfig.retrofit.create(apiRest)
    }

    suspend fun <T : Any> suspendApiCall(call: suspend () -> Response<T>?): Response<T>? =
            try {
                call.invoke()
            } catch (e: Exception) {
                null
            }

    fun <T> execute(call: Call<T>): T? {
        var response: Response<T>

        try {
            response = call.execute()
        } catch (e: Exception) {
            throw ServerConnectionApiException
        }

        return if (response.isSuccessful) {
            if (response.body() != null) {
                response.body()
            } else {
                null
            }
        } else {
            response.parseErrorCode()
            null
        }
    }

    fun <T> handleResponse(response: Response<T>?): T? {
        response?.let {
            return if (it.isSuccessful) {
                if (it.body() != null) {
                    it.body()
                } else {
                    null
                }
            } else {
                response.parseErrorCode()
                null
            }
        } ?: throw ServerConnectionApiException
    }

    fun <T> Response<T>.parseErrorCode() {

        when (this.code()) {
            RESPONSE_ERROR_500 -> throw Server500ApiException
            in RESPONSE_ERROR_400..RESPONSE_ERROR_400 + RESPONSE_MAX_INTERVAL -> throw Server4xxApiException
            else -> throw ServerUnknownApiException
        }
    }
}