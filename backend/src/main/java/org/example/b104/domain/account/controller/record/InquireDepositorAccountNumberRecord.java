package org.example.b104.domain.account.controller.record;

public record InquireDepositorAccountNumberRecord
        (String bankCode,
         String bankName,
         String accountNo,
         String userName)
{
}
