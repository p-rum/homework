package my.homework.cleevio.repository.entity

import jakarta.persistence.*
import my.homework.cleevio.enumeration.TxStatus
import java.math.BigDecimal

@Entity
@Table(name = "transactions")
data class TransactionEntity(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,

    @Column(name = "tx_hash") var txHash: String? = null,

    @Column(name = "sender") val sender: String,

    @Column(name = "address") val address: String,

    val amount: BigDecimal,

    @Enumerated(EnumType.STRING) var status: TxStatus = TxStatus.PENDING
) {
    constructor(sender: String, address: String, amount: BigDecimal) : this(
        sender = sender, address = address, amount = amount, status = TxStatus.PENDING
    )
}