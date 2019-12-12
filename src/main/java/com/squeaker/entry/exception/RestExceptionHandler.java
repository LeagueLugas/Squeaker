package com.squeaker.entry.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ UserNotFoundException.class })
    protected ResponseEntity<Object> userNotFound(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "User not found",
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({ InvalidBodyException.class })
    protected ResponseEntity<Object> invalidBody(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Invalid Boody",
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ InvalidTokenException.class })
    protected ResponseEntity<Object> invalidToken(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Invalid TokenResponse",
                new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler({ ExpiredTokenException.class })
    protected ResponseEntity<Object> expiredToken(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Expired token",
                new HttpHeaders(), HttpStatus.GONE, request);
    }

    @ExceptionHandler({ UserAlreadyExistsException.class })
    protected ResponseEntity<Object> userAlreadyExits(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "User Already Exits",
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler({ InvalidAuthEmailException.class })
    protected ResponseEntity<Object> failedAuthEmail(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Invalid AuthEmail Code",
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ InvalidAuthCodeException.class })
    protected ResponseEntity<Object> failedAuthCode(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Invalid AuthCode",
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ InvalidFileException.class })
    protected ResponseEntity<Object> invalidFile(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Invalid File",
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ TwittNotFoundException.class })
    protected ResponseEntity<Object> twittNotFound(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Twitt Not Found",
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({ CommentNotFoundException.class })
    protected ResponseEntity<Object> commentNotFound(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Comment Not Found",
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({ UserNotMatchException.class })
    protected ResponseEntity<Object> userNotMatch(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Twitt Not Found",
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ AlreadyLikeException.class })
    protected ResponseEntity<Object> alreadyLike(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Already Like",
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ AlreadyUnLikeException.class })
    protected ResponseEntity<Object> alreadyUnLike(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Already UnLike",
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ AlreadyFollowException.class })
    protected ResponseEntity<Object> alreadyFollow(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Already Follow",
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ AlreadyUnFollowException.class })
    protected ResponseEntity<Object> alreadyUnFollow(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Already UnFollow",
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }


}