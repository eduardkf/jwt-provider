package kattyfashion.jwtprovider.errors;

public class KfError extends RuntimeException{

    public KfError(ErrorMessage errorMessage) {
        super(errorMessage.getErrorMessage());
    }

}
