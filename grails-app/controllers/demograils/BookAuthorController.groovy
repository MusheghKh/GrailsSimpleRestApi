package demograils

import demograils.exception.AbstractHttpException
import grails.rest.*
import grails.converters.*

class BookAuthorController extends RestfulController {

    def bookAuthorService

    static responseFormats = ['json', 'xml']
    BookAuthorController() {
        super(BookAuthor)
    }

    @Override
    def index() {
        params.max = params.max ?: 10
        params.offset = params.offset ?: 0
        try {
            respond bookAuthorService.list(params, request)
        } catch(AbstractHttpException e) {
            response.status = e.code
            render e.json
        }
    }

    @Override
    def show() {
        try {
            respond bookAuthorService.single(params, request)
        } catch(AbstractHttpException e) {
            response.status = e.code
            render e.json
        }
    }

    @Override
    def save() {
        try {
            respond bookAuthorService.save(params, request)
        } catch(AbstractHttpException e) {
            response.status = e.code
            render e.json
        }
    }

    @Override
    def update() {
        try {
            respond bookAuthorService.update(params, request)
        } catch(AbstractHttpException e) {
            response.status = e.code
            render e.json
        }
    }

    @Override
    def delete() {
        try {
            respond bookAuthorService.delete(params, request)
        } catch(AbstractHttpException e) {
            response.status = e.code
            render e.json
        }
    }
}
