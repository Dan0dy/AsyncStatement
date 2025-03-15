package com.jc.asyncstatement.outboundVo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CoreBankingReqVo {
    /**
     * Request Bean for Call Core Banking API
     */
    private String accountNumber;
    private String fromDate;
    private String toDate;
    private int pageNo;
}
