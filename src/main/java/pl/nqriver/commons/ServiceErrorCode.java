package pl.nqriver.commons;

import jakarta.ws.rs.core.Response;

public enum ServiceErrorCode {
    BREWERY_ACCESS_FORBIDDEN("BMS01", "Access to this brewery is denied", Response.Status.FORBIDDEN),
    BREWERY_NOT_FOUND("BMS02", "Brewery cannot be found", Response.Status.NOT_FOUND),
    MANAGER_EMAIL_TAKEN("BMS03", "User with chosen email already exists", Response.Status.CONFLICT),
    MANAGER_LOGIN_TAKEN("BMS04", "User with chosen login already exists", Response.Status.CONFLICT),
    MANAGER_NOT_FOUND("BMS05", "Manager cannot be found", Response.Status.NOT_FOUND),
    INVALID_BREWERY("BMS06", "This brewery is not managed by chosen manager", Response.Status.BAD_REQUEST),
    BEER_STYLE_NOT_FOUND("BM07", "Beer style cannot be found", Response.Status.NOT_FOUND),
    BEER_NOT_FOUND("BM08", "Beer cannot be found", Response.Status.NOT_FOUND),
    BEER_ALREADY_PRODUCED("BMS09", "Beer is already produced by chosen brewery", Response.Status.BAD_REQUEST),
    BEER_IS_NOT_PRODUCED("BMS10", "Beer is not produced by chosen brewery", Response.Status.BAD_REQUEST);


    private final String code;
    private final String message;

    private final Response.Status status;

    ServiceErrorCode(String code, String message, Response.Status status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Response.Status getStatus() {
        return status;
    }
}
