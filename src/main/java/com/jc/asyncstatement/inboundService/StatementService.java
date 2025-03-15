package com.jc.asyncstatement.inboundService;

import com.jc.asyncstatement.inboundVo.StatementReqVo;
import com.jc.asyncstatement.outboundVo.CoreBankingReqVo;
import com.jc.asyncstatement.outboundVo.CoreBankingRespVo;
import com.jc.asyncstatement.outboundVo.TemplateEngineReqVo;
import com.jc.asyncstatement.outboundVo.TemplateEngineRespVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatementService {
    private final RestTemplate restTemplate;

    public String doInboundService(StatementReqVo reqVo) {
        String jobId = UUID.randomUUID().toString();
        asyncGetResult(reqVo.getAccountNumber(), reqVo.getFromDate(), reqVo.getToDate(), jobId);
        return jobId;
    }

    private TemplateEngineReqVo getTemplateEngineReqVo(String accountNumber, LocalDate startDate, LocalDate endDate) {
        int pageNo = 1;
        boolean lastPage;
        TemplateEngineReqVo templateEngineReqVo = new TemplateEngineReqVo();
        List<TemplateEngineReqVo.Transaction> templateEngineTransactions = new ArrayList<>();
        do {
            CoreBankingRespVo coreBankingRespVo = restTemplate.postForObject(
                    "http://localhost:8080/api/v1/transactions",
                    new CoreBankingReqVo(accountNumber, startDate.toString(), endDate.toString(), pageNo),
                    CoreBankingRespVo.class
            );

            for (CoreBankingRespVo.Transaction coreBankTransaction : coreBankingRespVo.getTransactions()) {
                TemplateEngineReqVo.Transaction templateEngineTransaction = new TemplateEngineReqVo.Transaction(
                        coreBankTransaction.getTrxReferenceNo(),
                        coreBankTransaction.getValueDate(),
                        coreBankTransaction.getDescription(),
                        coreBankTransaction.getTrxType(),
                        coreBankTransaction.getAmount(),
                        coreBankTransaction.getBeneficiaryDetails(),
                        coreBankTransaction.getTranCurrency()
                );
                templateEngineTransactions.add(templateEngineTransaction);
            }

            lastPage = coreBankingRespVo.getPage().isLastPage();
            pageNo++;
        } while (!lastPage);

        templateEngineReqVo.setData(templateEngineTransactions);
        templateEngineReqVo.setTemplateId("AccountStatement");

        return templateEngineReqVo;
    }

    private String generatePdf(TemplateEngineReqVo templateEngineReqVo) {
        String pdf = restTemplate.postForObject(
                "http://localhost:8080/api/v1/pdf",
                templateEngineReqVo,
                TemplateEngineRespVo.class
        ).getData();

        return pdf;
    }

    @Async
    public void asyncGetResult(String accountNumber, LocalDate fromDate, LocalDate toDate, String jobId) {
        try {
            TemplateEngineReqVo templateEngineReqVo = getTemplateEngineReqVo(accountNumber, fromDate, toDate);
            String pdf = generatePdf(templateEngineReqVo);
            log.info("Job {} completed. PDF: {}", jobId, pdf);
        } catch (Exception e) {
            log.error("Job {} failed: {}", jobId, e.getMessage());
        }
    }
}
