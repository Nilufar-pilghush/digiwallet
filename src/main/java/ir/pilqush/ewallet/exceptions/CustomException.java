package ir.pilqush.ewallet.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class CustomException {
    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<DateException> badRequest(BadRequestException ex) {
        DateException dataEx = new DateException(ex.getMessage(), HttpStatus.BAD_REQUEST
                , ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(dataEx, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<DateException> notFoundException(NotFoundException ex) {
        DateException dataEx = new DateException(
                ex.getMessage(), HttpStatus.NOT_FOUND, ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(dataEx, HttpStatus.NOT_FOUND);
    }
}
