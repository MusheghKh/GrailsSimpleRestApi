package demograils

class BookAuthorController extends BaseRestfulController<BookAuthor> {

    static responseFormats = ['json', 'xml']

    def bookAuthorService

    BookAuthorController() {
        super(BookAuthor)
    }

    @Override
    def index() {
        params.max = params.max ?: 10
        params.page = params.page ?: 0
        doOrHandleHttpError {
            respond bookAuthorService.list(params, request)
        }
    }

    @Override
    def show() {
        doOrHandleHttpError {
            respond bookAuthorService.single(params, request)
        }
    }

    @Override
    def save() {
        doOrHandleHttpError {
            respond bookAuthorService.save(params, request)
        }
    }

    @Override
    def update() {
        doOrHandleHttpError {
            respond bookAuthorService.update(params, request)
        }
    }

    @Override
    def delete() {
        doOrHandleHttpError {
            respond bookAuthorService.delete(params, request)
        }
    }
}
