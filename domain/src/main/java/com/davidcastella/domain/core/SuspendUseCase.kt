package com.davidcastella.domain.core

typealias SuspendUseCase<P, R> = suspend (P) -> R