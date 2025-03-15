package com.jc.asyncstatement.inboundService;


import com.jc.asyncstatement.outboundVo.CoreBankingReqVo;
import com.jc.asyncstatement.outboundVo.CoreBankingRespVo;
import com.jc.asyncstatement.outboundVo.TemplateEngineReqVo;
import com.jc.asyncstatement.outboundVo.TemplateEngineRespVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
public class StatementServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private StatementService statementService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * test asynchronous method has completed its execution.
     */
    @Test
    void testAsyncGetResult() throws InterruptedException {
        CoreBankingRespVo coreBankingRespVo = new CoreBankingRespVo();
        List<CoreBankingRespVo.Transaction> transactions = new ArrayList<>();
        transactions.add(new CoreBankingRespVo.Transaction(
                "trxRef123", "2024-11-12T00:00:00", "Test Transaction", "DEBIT", new BigDecimal(100), "Beneficiary", "AED"));
        CoreBankingRespVo.PageInfo pageInfo = new CoreBankingRespVo.PageInfo(true, 1);
        coreBankingRespVo.setPage(pageInfo);


        when(restTemplate.postForObject(any(String.class), any(CoreBankingReqVo.class), eq(CoreBankingRespVo.class)))
                .thenReturn(coreBankingRespVo);

        TemplateEngineRespVo templateEngineRespVo = new TemplateEngineRespVo("pdf");

        when(restTemplate.postForObject(any(String.class), any(TemplateEngineReqVo.class), eq(TemplateEngineRespVo.class)))
                .thenReturn(templateEngineRespVo);

        CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> {
            statementService.asyncGetResult("000009093817625", LocalDate.now(), LocalDate.now(), UUID.randomUUID().toString());
            latch.countDown();
        }).start();

        boolean completed = latch.await(5, TimeUnit.SECONDS);
        assertTrue(completed, "The async method did not complete in time");
    }
}