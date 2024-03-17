package org.example.b104.domain.account.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
public class REC {
    public String accountTypeUniqueNo;
    public String bankCode;
    public String bankName;
    public String accountTypeCode;
    public String accountTypeName;
    public String accountName;
}
