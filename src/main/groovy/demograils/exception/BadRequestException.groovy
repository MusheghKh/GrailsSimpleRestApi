package demograils.exception

import org.springframework.http.HttpStatus

class BadRequestException extends AbstractHttpException{

    BadRequestException() {
    }

    BadRequestException(String s) {
        super(s)
    }

    @Override
    HttpStatus getHttpStatus() {
        HttpStatus.BAD_REQUEST
    }
}
