package com.davidcastella.domain.core.util

sealed interface Result<out L, out R> {
    data class Success<R>(val value: R) : Result<Nothing, R>
    data class Failure<L>(val failure: L) : Result<L, Nothing>
}

inline fun <L, R, A> Result<L, R>.map(f: (R) -> A): Result<L, A> = when (this) {
    is Result.Success -> Result.Success(f(value))
    is Result.Failure -> this
}

inline fun <L, R, A> Result<L, R>.flatMap(f: (R) -> Result<L, A>): Result<L, A> = when (this) {
    is Result.Success -> f(value)
    is Result.Failure -> this
}

inline fun <L, R, A> Result<L, R>.fold(
    isFailure: (failure: L) -> A,
    isSuccess: (success: R) -> A
): A =
    when (this) {
        is Result.Success -> isSuccess(value)
        is Result.Failure -> isFailure(failure)
    }

