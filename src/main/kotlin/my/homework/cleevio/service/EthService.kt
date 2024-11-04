package my.homework.cleevio.service

import my.homework.cleevio.config.Web3jConfig
import my.homework.cleevio.controller.SendEthResp
import my.homework.cleevio.enumeration.TxStatus
import my.homework.cleevio.exception.TransactionFailedException
import my.homework.cleevio.repository.TransactionRepository
import my.homework.cleevio.repository.entity.TransactionEntity
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.tx.Transfer
import org.web3j.utils.Convert
import java.math.BigDecimal


@Service
class EthService @Autowired constructor(
    private val transactionRepository: TransactionRepository,
    web3jConfig: Web3jConfig
) {

    val web3j: Web3j = web3jConfig.web3j()

    private val logger = LoggerFactory.getLogger(EthService::class.java)

    fun sendEth(privateKey: String, toAddress: String, amount: BigDecimal): SendEthResp {
        val credentials = Credentials.create(privateKey)
        val savedTx = saveTransaction(credentials.address, toAddress, amount)

        return try {
            val txHash = sendTransaction(credentials, toAddress, amount)
                ?: throw TransactionFailedException("Transaction failed to create tx hash")

            savedTx.txHash = txHash
            savedTx.status = TxStatus.SUCCESS
            transactionRepository.save(savedTx)
            SendEthResp(txHash)
        } catch (e: Exception) {
            logger.error("Transaction failed: ${e.message}")
            savedTx.status = TxStatus.FAILED
            transactionRepository.save(savedTx)
            throw TransactionFailedException("Transaction failed")
        }
    }

    fun getBalance(address: String): BigDecimal {
        val ethGetBalance = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send()
        return Convert.fromWei(ethGetBalance.balance.toString(), Convert.Unit.ETHER)
    }

    private fun sendTransaction(credentials: Credentials, address: String, amount: BigDecimal): String? {
        return try {
            val transactionReceipt = Transfer.sendFunds(web3j, credentials, address, amount, Convert.Unit.ETHER).send()
            transactionReceipt.transactionHash
        } catch (e: Exception) {
            logger.error("Failed to send transaction", e)
            null
        }
    }

    private fun saveTransaction(sender: String, address: String, amount: BigDecimal): TransactionEntity {
        return transactionRepository.save(
            TransactionEntity(
                sender = sender,
                address = address,
                amount = amount,
                status = TxStatus.PENDING
            )
        )
    }
}
