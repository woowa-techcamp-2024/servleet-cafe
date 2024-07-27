package codesquad.javacafe.common.exception;

public enum ClientErrorCode implements ErrorCode{
	USER_ALREADY_EXIST(HttpStatus.CONFLICT, "해당 아이디는 이미 사용중입니다."),
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
	INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
	POST_IS_NULL(HttpStatus.BAD_REQUEST, "존재하지 않는 게시글입니다."),
	PAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "요청 경로를 확인해 주세요."),
	METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "요청 메소드를 확인해 주세요.");

	private final HttpStatus httpStatus;
	private final String message;

	ClientErrorCode(HttpStatus httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}

	public CustomException customException(String debugMessage) {
		return new CustomException(getHttpStatus(), name(), getMessage(), debugMessage);
	}

	@Override
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
