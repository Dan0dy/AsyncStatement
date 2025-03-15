package com.jc.asyncstatement.outboundVo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemplateEngineReqVo {
    private String templateId;
    private List<Transaction> data;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Transaction implements Serializable {
        private String trxReferenceNo;
        private String valueDate;
        private String description;
        private String trxType;
        private BigDecimal amount;
        private String beneficiaryDetails;
        private String tranCurrency;
    }
}
