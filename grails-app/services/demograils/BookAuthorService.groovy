package demograils

import demograils.exception.AbstractHttpException
import demograils.exception.BadRequestException
import demograils.exception.NotFoundException
import grails.gorm.transactions.Transactional

@Transactional
class BookAuthorService implements Pagination{

    def messageSource

    def list(def params, def request) throws AbstractHttpException {
        validateParamsForPagination(messageSource, params)
        Long bookId = getLongBookId(params)

        def criteria = BookAuthor.createCriteria()

        def result = criteria.list(max: params.max, offset: params.offset) {
            eq("book.id", bookId)
        }

        return result
    }

    def single(def params, def request) throws AbstractHttpException {
        Long bookId = getLongBookId(params)
        Long authorId = getLongAuthorId(params)

        def criteria = BookAuthor.createCriteria()
        def result = criteria.get {
            eq("book.id", bookId)
            eq('id', authorId)
        }
        if (result == null) {
            throw new NotFoundException(messageSource.getMessage("bookAuthor.book_with_id_not_found", [authorId] as Object[], Locale.getDefault()))
        }
        return result
    }

    def save(def params, def request) throws AbstractHttpException {
        def bookId = getLongBookId(params.bookId)
        def book = Book.get(bookId)
        if (book == null) {
            throw new NotFoundException(messageSource.getMessage("book.book_with_id_not_found", [bookId] as Object[], Locale.getDefault()))
        }

        def authorJson = request.JSON
        def authorInstance = new BookAuthor(authorJson)

        book.addToAuthors(authorInstance)

        return authorInstance
    }

    def update(def params, def request) throws AbstractHttpException {
        def authorInstance = getBookAuthor(params)
        def authorJson = request.JSON
        authorInstance.properties = authorJson

        authorInstance = authorInstance.merge()

        return authorInstance
    }

    def delete(def params, def request) throws AbstractHttpException {
        def authorInstance = getBookAuthor(params)
        authorInstance = authorInstance.delete()

        return authorInstance
    }

    private BookAuthor getBookAuthor(def params) throws AbstractHttpException {
        Long authorId = getLongAuthorId(params)

        def authorInstance = BookAuthor.get(authorId)
        if (authorInstance == null) {
            throw new NotFoundException(messageSource.getMessage("bookAuthor.author_with_id_not_found", [authorId] as Object[], Locale.getDefault()))
        }
        return authorInstance
    }

    private getLongBookId(def params) throws AbstractHttpException {
        try {
            return Long.parseLong(params.bookId)
        } catch(NumberFormatException ignored) {
            throw new BadRequestException(messageSource.getMessage("bookAuthor.book_id_must_be_a_number", null, Locale.getDefault()))
        }
    }

    private getLongAuthorId(def params) throws AbstractHttpException {
        try {
            return Long.parseLong(params.authorId)
        } catch(NumberFormatException ignored) {
            throw new BadRequestException(messageSource.getMessage("bookAuthor.author_id_must_be_a_number", null, Locale.getDefault()))
        }
    }
}
