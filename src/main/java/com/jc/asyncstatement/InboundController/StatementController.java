package com.jc.asyncstatement.InboundController;

import com.jc.asyncstatement.inboundService.StatementService;
import com.jc.asyncstatement.inboundVo.StatementReqVo;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class StatementController {
    private final StatementService statementService;

    @GetMapping("/statements")
    public ResponseEntity<String> generateStatement(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate,
            @RequestParam String accountNumber) {

        StatementReqVo reqVo = new StatementReqVo(accountNumber, fromDate, toDate);

        String jobId = statementService.doInboundService(reqVo);
        return ResponseEntity.accepted().body("Request accepted. Job ID: " + jobId);
    }
}
