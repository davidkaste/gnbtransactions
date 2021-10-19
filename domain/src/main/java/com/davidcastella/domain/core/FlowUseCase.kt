package com.davidcastella.domain.core

import kotlinx.coroutines.flow.Flow

typealias FlowUseCase<P, R> = (P) -> Flow<R>

object NoParams