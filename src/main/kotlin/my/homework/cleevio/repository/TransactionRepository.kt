package my.homework.cleevio.repository

import my.homework.cleevio.repository.entity.TransactionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository : JpaRepository<TransactionEntity, Long> {

    fun findByTxHash(txHash: String): TransactionEntity?
}