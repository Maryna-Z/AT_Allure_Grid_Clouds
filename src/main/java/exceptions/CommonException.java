package exceptions;

public class CommonException extends RuntimeException {

    public CommonException() {

    }

    public CommonException(String message, Throwable t) {
        super(message, t);
    }
}
