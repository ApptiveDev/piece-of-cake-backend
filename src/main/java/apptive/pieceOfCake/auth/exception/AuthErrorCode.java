package apptive.pieceOfCake.auth.exception;

public enum AuthErrorCode {

    ILLEGAL_REGISTRATION_ID("잘못된 등록 ID입니다."),
    INVALID_TOKEN("유효하지 않은 토큰입니다."),
    USER_NOT_FOUND("사용자를 찾을 수 없습니다.");

    private final String message;

    AuthErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
