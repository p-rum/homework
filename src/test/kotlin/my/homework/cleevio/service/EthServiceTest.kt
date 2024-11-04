package my.homework.cleevio.service

import my.homework.cleevio.enumeration.TxStatus
import my.homework.cleevio.repository.TransactionRepository
import my.homework.cleevio.repository.entity.TransactionEntity
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`

import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameter
import org.web3j.protocol.core.methods.response.EthGetBalance
import java.math.BigDecimal
import java.math.BigInteger

@ExtendWith(SpringExtension::class)
@SpringBootTest
@ActiveProfiles("test")
class EthServiceTest {

    @MockBean
    lateinit var transactionRepository: TransactionRepository

    @Autowired
    lateinit var ethService: EthService

    var web3j: Web3j = Mockito.mock(Web3j::class.java)


    @Test
    fun `should return correct balance`() {
        val mockBalanceResponse = Mockito.mock(EthGetBalance::class.java)
        val mockBlockParameter = Mockito.mock(DefaultBlockParameter::class.java)
        val address = "0x0000000000000000000000000000000000000000"

        whenever(mockBalanceResponse.balance).thenReturn(BigInteger("1000000000000000000"))
        `when`(web3j.ethGetBalance(address, mockBlockParameter).send()).thenReturn(mockBalanceResponse)
        val balance = ethService.getBalance(address)

        assertNotNull(balance)
        assertEquals(BigDecimal("1.0"), balance)
    }

    @Test
    fun `should successfully send ETH and store transaction`() {
        val privateKey = "0x0000000000000000000000000000000000000000"
        val address = "0x0000000000000000000000000000000000000000"
        val amount = BigDecimal("0.01")

        val mockCredentials = Credentials.create(privateKey)
        val mockTransactionEntity = TransactionEntity(
            sender = mockCredentials.address,
            address = address,
            amount = amount,
            status = TxStatus.SUCCESS,
            txHash = "mockTxHash"
        )

        whenever(transactionRepository.save(mockTransactionEntity)).thenReturn(mockTransactionEntity)
        whenever(transactionRepository.findByTxHash("mockTxHash")).thenReturn(mockTransactionEntity)

        val response = ethService.sendEth(privateKey, address, amount)

        assertNotNull(response.txHash)
        assertEquals("mockTxHash", response.txHash)

        val transaction = transactionRepository.findByTxHash(response.txHash)
        assertNotNull(transaction)
        assertEquals(TxStatus.SUCCESS, transaction?.status)
        assertEquals(amount, transaction?.amount)
        assertEquals(address, transaction?.address)
    }
}