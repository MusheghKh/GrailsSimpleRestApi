package demograils

import demograils.exception.BadRequestException
import demograils.exception.NotFoundException

class ErrorController {
	static responseFormats = ['json', 'xml']
	
    def badRequest() {
        BadRequestException targetException = request.exception.cause.target
        response.status = targetException.code
        render(targetException.json)
    }

    def notFound() {
        NotFoundException targetException = request.exception.cause.target
        response.status = targetException.code
        render(targetException.json)
    }
}
