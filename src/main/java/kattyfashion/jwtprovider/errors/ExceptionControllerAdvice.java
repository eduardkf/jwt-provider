package kattyfashion.jwtprovider.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(KfError.class)
    public ResponseEntity<String> exceptionUserAlreadyExistsHandler(KfError kfError) {
        return ResponseEntity.badRequest().body(
                kfError.getMessage()
        );
 }

    @ExceptionHandler(JwtError.class)
    public ResponseEntity<String> exceptionJwtHandler(JwtError jwtError) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                jwtError.getMessage()
        );
    }
}
