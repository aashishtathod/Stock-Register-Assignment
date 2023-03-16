package dev.aashishtathod.stockregisterassignment.utils

sealed class Either<out T> {
    data class Success<out T>(
        val data: T
    ) : Either<T>()
	
    data class Error(
        val message: String
    ) : Either<Nothing>()
}
