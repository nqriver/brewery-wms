package pl.nqriver.commons;

public class ServiceException extends RuntimeException {

    protected final ServiceErrorCode code;

    public ServiceException(ServiceErrorCode code) {
        this.code = code;
    }

    public ServiceException(String message, ServiceErrorCode code) {
        super(message);
        this.code = code;
    }

    public ServiceErrorCode getCode() {
        return code;
    }
}
