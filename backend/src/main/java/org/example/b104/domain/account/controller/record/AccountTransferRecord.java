package org.example.b104.domain.account.controller.record;

public record AccountTransferRecord(int transactionUniqueNo,
                                    String accountNo,
                                    String transactionDate,
                                    String transactionType,
                                    String transactionTypeName,
                                    String transactionAccountNo) {
}
