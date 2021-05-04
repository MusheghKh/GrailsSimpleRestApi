package demograils


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
        respond bookAuthorService.list(params, request)
    }

    @Override
    def show() {
        respond bookAuthorService.single(params, request)
    }

    @Override
    def save() {
        def author = bookAuthorService.save(params, request)
        respond author
    }

    @Override
    def update() {
        def author = bookAuthorService.update(params, request)
        respond author
    }

    @Override
    def delete() {
        def author = bookAuthorService.delete(params, request)
        respond author
    }
}
