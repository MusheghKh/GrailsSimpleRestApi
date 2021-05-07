package demograils

import demograils.exception.AbstractHttpException

class ErrorController {
	static responseFormats = ['json', 'xml']
	
    def handleAbstractHttpException() {
        AbstractHttpException targetException = request.exception.cause.target
        response.status = targetException.code
        render(targetException.json)
    }
}
