package demograils.exception

import org.springframework.http.HttpStatus

class NotFoundException extends AbstractHttpException{

    NotFoundException() {
    }

    NotFoundException(String s) {
        super(s)
    }

    @Override
    HttpStatus getHttpStatus() {
        HttpStatus.NOT_FOUND
    }
}
