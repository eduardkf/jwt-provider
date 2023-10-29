package kattyfashion.jwtprovider.errors;

import kattyfashion.jwtprovider.errors.ErrorMessage;

public class JwtError extends RuntimeException{

    public JwtError(ErrorMessage errorMessage) {
        super(errorMessage.getErrorMessage());
    }

}
