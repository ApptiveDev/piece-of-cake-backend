package apptive.pieceOfCake.authentication.oauth2;

import java.time.LocalDateTime;

public class OAuth2Token {

    private String token;
    private String refreshToken;
    private LocalDateTime expiredAt;

    public OAuth2Token() {
    }

    public OAuth2Token(String token, String refreshToken, LocalDateTime expiredAt) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.expiredAt = expiredAt;
    }
}
