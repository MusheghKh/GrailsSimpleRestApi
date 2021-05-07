package demograils.exception

import org.springframework.http.HttpStatus

class BadRequestException extends AbstractHttpException{

    BadRequestException() {
        super()
    }

    BadRequestException(String s) {
        super(s)
    }

    @Override
    HttpStatus getHttpStatus() {
        HttpStatus.BAD_REQUEST
    }
}
