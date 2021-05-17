package demograils

class BookController extends BaseRestfulController<Book> {

    static responseFormats = ['json', 'xml']

    def bookService

    BookController() {
        super(Book)
    }

    @Override
    def index() {
        params.max = params.max ?: 10
        params.page = params.page ?: 0
        doOrHandleHttpError {
            respond bookService.list(params, request)
        }
    }

    @Override
    def show() {
        doOrHandleHttpError {
            respond bookService.single(params, request)
        }
    }

    @Override
    def save() {
        respond bookService.save(params, request)
    }

    @Override
    def update() {
        doOrHandleHttpError {
            respond bookService.update(params, request)
        }
    }

    @Override
    def delete() {
        doOrHandleHttpError {
            respond bookService.delete(params, request)
        }
    }
}
