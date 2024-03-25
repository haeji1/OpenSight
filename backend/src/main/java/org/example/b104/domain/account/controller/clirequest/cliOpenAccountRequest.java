package org.example.b104.domain.account.controller.clirequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.JSONPropertyName;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class cliOpenAccountRequest {
    String accountTypeUniqueNo;
}
