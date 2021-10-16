package com.davidcastella.domain

import kotlinx.coroutines.flow.Flow

typealias FlowUseCase<P, R> = (P) -> Flow<R>

object NoParams