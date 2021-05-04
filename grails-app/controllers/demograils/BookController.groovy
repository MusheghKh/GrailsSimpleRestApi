package demograils


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
        respond bookService.list(params, request)
    }

    @Override
    def show() {
        respond bookService.single(params, request)
    }

    @Override
    def save() {
        def book = bookService.save(params, request)
        respond super.save()
    }

    @Override
    def update() {
        def book = bookService.update(params, request)
        respond book
    }

    @Override
    def delete() {
        def book = bookService.delete(params, request)
        respond book
    }
}
