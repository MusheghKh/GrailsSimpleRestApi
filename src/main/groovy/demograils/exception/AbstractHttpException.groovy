package demograils.exception

import grails.converters.JSON
import org.springframework.http.HttpStatus

abstract class AbstractHttpException extends RuntimeException{

    AbstractHttpException() {
        super("")
    }

    AbstractHttpException(String s) {
        super(s)
    }

    abstract HttpStatus getHttpStatus()

    int getCode() {
        getHttpStatus().value()
    }

    JSON getJson() {
        [code: getCode(), message: message] as JSON
    }
}
