package com.example.proyecto.utils

import com.example.proyecto.services.ApiService

class ApiUtils {
    companion object {
        val BASE_URL="http://20.206.205.20:8080"
        fun getAPIService(): ApiService {
            return RetrofitClient.getClient(BASE_URL).create(ApiService::class.java)
        }

    }
}