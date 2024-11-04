package my.homework.cleevio.controller

import com.fasterxml.jackson.annotation.JsonInclude
import java.math.BigDecimal


data class GetBalanceResp(
    val balance: BigDecimal
)

data class SendEthResp(
    val txHash: String
)


data class SendEthReq(

    val privateKey: String,


    val address: String,


    val amount: BigDecimal
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ErrorResponse(
    val message: String?,
    val attributes: Map<String, Any>?,
    val status: Int
)