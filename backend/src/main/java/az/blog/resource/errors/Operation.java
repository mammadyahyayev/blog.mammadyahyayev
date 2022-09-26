package az.blog.resource.errors;

import az.blog.resource.vm.OperationResult;

//TODO: make this class abstract and create its subclasses such as UpdateOperation (add some urls (refer to newly updated value) after updating)
public final class Operation {

    private Operation() {

    }

    public static OperationResult createdSuccessfully(String message) {
        return new OperationResult(ExceptionConstant.CREATED, message);
    }

    public static OperationResult unknownError() {
        return new OperationResult(ExceptionConstant.UNKNOWN_ERROR, "Internal server error happened");
    }

    public static OperationResult unknownError(String message) {
        return new OperationResult(ExceptionConstant.UNKNOWN_ERROR, message);
    }

    public static OperationResult updatedSuccessfully(String message) {
        return new OperationResult(ExceptionConstant.UPDATED, message);
    }

    public static OperationResult deletedSuccessfully(String message) {
        return new OperationResult(ExceptionConstant.DELETED, message);
    }
}
