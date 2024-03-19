package org.example.b104.domain.account.controller.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class InquireTransactionHistoryDetailRequest {
    AccountRequestHeader Header;
    String bankCode;
    String accountNo;
    int transactionUniqueNo;
}
