package org.example.b104.domain.account.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@AllArgsConstructor
public class DrawingTransferRequest {
    AccountRequestHeader Header;
    String depositBankCode;//입금계좌은행 코드
    String depositAccountNo;//입금계좌번호
    Long transactionBalance;//거래 금액
    String withdrawalBankCode;//출금 계좌 은행코드
    String withdrawalAccountNo;//출금 계좌번호
    String depositTransactionSummary;//거래 요약 내용 (입금계좌)
    String withdrawalTransactionSummary;// 거래 요약 내용 (출금계좌)
}
