package org.example.b104.domain.account.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.example.b104.domain.account.controller.record.InquireAccountListRecord;

@Builder
@Getter
@AllArgsConstructor
@ToString
public class InquireAccountListResponse
{
    String result;
    AccountResponseHeader Header;
    InquireAccountListRecord[] REC;
}
