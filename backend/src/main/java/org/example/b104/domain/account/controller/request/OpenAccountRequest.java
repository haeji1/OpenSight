package org.example.b104.domain.account.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class OpenAccountRequest {
    AccountRequestHeader Header;
    String accountTypeUniqueNo;
}
