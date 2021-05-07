package demograils

import demograils.exception.AbstractHttpException
import grails.rest.*
import grails.converters.*

class BookController extends RestfulController {

    def bookService

    static responseFormats = ['json', 'xml']
    BookController() {
        super(Book)
    }

    @Override
    def index() {
        params.max = params.max ?: 10
        params.offset = params.offset ?: 0
        try {
            respond bookService.list(params, request)
        } catch(AbstractHttpException e) {
            response.status = e.code
            render e.json
        }
    }

    @Override
    def show() {
        try {
            respond bookService.single(params, request)
        } catch(AbstractHttpException e) {
            response.status = e.code
            render e.json
        }
    }

    @Override
    def save() {
        def book = bookService.save(params, request)
        respond book
    }

    @Override
    def update() {
        try {
            respond bookService.update(params, request)
        } catch(AbstractHttpException e) {
            response.status = e.code
            render e.json
        }
    }

    @Override
    def delete() {
        try {
            respond bookService.delete(params, request)
        } catch(AbstractHttpException e) {
            response.status = e.code
            render e.json
        }
    }
}
