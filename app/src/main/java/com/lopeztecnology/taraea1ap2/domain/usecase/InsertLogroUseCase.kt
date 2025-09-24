package com.lopeztecnology.taraea1ap2.domain.usecase

import com.lopeztecnology.taraea1ap2.data.local.LogroEntity
import com.lopeztecnology.taraea1ap2.data.repository.LogroRepository

class InsertLogroUseCase(private val repository: LogroRepository) {
    suspend operator fun invoke(logro: LogroEntity) {
        repository.insertarLogro(logro)
    }
}
