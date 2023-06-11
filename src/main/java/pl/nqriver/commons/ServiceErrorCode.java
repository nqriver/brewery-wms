package pl.nqriver.commons;

import jakarta.ws.rs.core.Response;

public enum ServiceErrorCode {
    BREWERY_ACCESS_FORBIDDEN("BMS01", "Access to this brewery is denied", Response.Status.FORBIDDEN),
    BREWERY_NOT_FOUND("BMS02", "Brewery cannot be found", Response.Status.NOT_FOUND);


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
