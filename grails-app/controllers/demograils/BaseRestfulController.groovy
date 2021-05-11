package demograils

import demograils.exception.AbstractHttpException
import grails.rest.RestfulController

class BaseRestfulController extends RestfulController {

    BaseRestfulController(Class resource) {
        super(resource)
    }

    protected doOrHandleHttpError(Closure closure) {
        try {
            closure.call()
        } catch (AbstractHttpException e) {
            response.status = e.code
            render e.json
        }
    }
}
