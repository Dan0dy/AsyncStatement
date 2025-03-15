package com.jc.asyncstatement.inboundVo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class  StatementReqVo{
    String accountNumber;
    LocalDate fromDate;
    LocalDate toDate;
}
