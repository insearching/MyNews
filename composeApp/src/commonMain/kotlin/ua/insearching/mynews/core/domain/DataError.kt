package ua.insearching.mynews.core.domain

sealed interface DataError: Error {
    enum class Remote: DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        SERVER,
        SERIAlIZATION,
        UNKNOWN
    }
    
    enum class Local: DataError {
        DATA_ERROR,
        UNKNOWN
    }
}