package org.example.b104.global.response;

import lombok.Data;
import java.util.Date;

@Data
public class BankApiResponse {
    private String code;
    private Payload payload;
    private Date now;

    @Data
    public static class Payload {
        private String userId;
        private String userName;
        private String institutionCode;
        private String userKey;
        private Date created;
        private Date modified;

    }
}
