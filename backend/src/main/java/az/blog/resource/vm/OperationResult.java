package az.blog.resource.vm;

public class OperationResult {
    private final String status;
    private final String message;

    public OperationResult(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }
}
