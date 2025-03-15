package com.jc.asyncstatement.OutboundController;

import com.jc.asyncstatement.outboundVo.CoreBankingReqVo;
import com.jc.asyncstatement.outboundVo.CoreBankingRespVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CoreBankingController implements GeneralOutboundController<CoreBankingReqVo,CoreBankingRespVo> {
    /**
     * Mock Core Bank API
     */
    @PostMapping("/transactions")
    public CoreBankingRespVo doOut(@RequestBody CoreBankingReqVo reqVo) {
        boolean lastPage = reqVo.getPageNo() >= 2;
        CoreBankingRespVo respVo = new CoreBankingRespVo();
        List<CoreBankingRespVo.Transaction> transactionList = new ArrayList<>();
        if (lastPage) {
            transactionList.add(new CoreBankingRespVo.Transaction(
                    "010000034",
                    "2024-11-12T00:00:00",
                    "Fund transfer",
                    "D",
                    BigDecimal.valueOf(100),
                    "Friends Name",
                    "AED"

            ));
            transactionList.add(new CoreBankingRespVo.Transaction(
                    "010000035",
                    "2024-11-12T00:00:00",
                    "Bill Payment",
                    "D",
                    BigDecimal.valueOf(500),
                    "Friends Name",
                    "AED"
            ));

        } else {
            transactionList.add(new CoreBankingRespVo.Transaction(
                    "010000032",
                    "2024-11-12T00:00:00",
                    "Fund transfer",
                    "D",
                    BigDecimal.valueOf(100),
                    "Friends Name",
                    "AED"

            ));
            transactionList.add(new CoreBankingRespVo.Transaction(
                    "010000033",
                    "2024-11-12T00:00:00",
                    "Bill Payment",
                    "D",
                    BigDecimal.valueOf(500),
                    "Friends Name",
                    "AED"
            ));

        }
        respVo.setTransactions(transactionList);

        CoreBankingRespVo.PageInfo pageInfo = new CoreBankingRespVo.PageInfo(lastPage,reqVo.getPageNo());
        respVo.setPage(pageInfo);

        return respVo;
    }
}
