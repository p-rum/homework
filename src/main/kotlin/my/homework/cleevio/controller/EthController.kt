package my.homework.cleevio.controller



import my.homework.cleevio.service.EthService

import org.springframework.web.bind.annotation.GetMapping

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/eth")
class EthController(
    private val ethService: EthService,

    ) {

    @GetMapping("/balance")
    fun getBalance(@RequestParam address: String): GetBalanceResp {
        val balance = ethService.getBalance(address)
        return GetBalanceResp(balance)
    }

    @PostMapping("/send")
    fun sendEth(
        @RequestBody(required = true) req: SendEthReq
    ): SendEthResp {
        return ethService.sendEth(req.privateKey, req.address, req.amount)
    }
}