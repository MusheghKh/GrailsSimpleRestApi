package demograils

import demograils.exception.BadRequestException
import demograils.exception.NotFoundException
import grails.gorm.transactions.Transactional

@Transactional
class BookAuthorService {

    def messageSource

    def list(def params, def request) {
        if (params.max < 0) {
            throw new BadRequestException(messageSource.getMessage("pagination.max_can_not_be_less_than_zero", null, Locale.getDefault()))
        }
        if (params.offset < 0) {
            throw new BadRequestException(messageSource.getMessage("pagination.offset_can_not_be_less_than_zero", null, Locale.getDefault()))
        }
        def bookId = params.bookId

        def criteria = BookAuthor.createCriteria()

        def result = criteria.list(max: params.max, offset: params.offset) {
            eq("book.id", bookId)
        }

        return result
    }

    def single(def params, def request) {
        Long bookId = Long.parseLong(params.bookId)
        Long authorId
        try {
            authorId = Long.parseLong(params?.id)
        } catch(NumberFormatException ignored) {
            throw new BadRequestException(messageSource.getMessage("bookAuthor.author_id_must_be_a_number", null, Locale.getDefault()))
        }

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

    def save(def params, def request) {
        def bookId = params.bookId
        def book = Book.get(bookId)
        if (book == null) {
            throw new NotFoundException(messageSource.getMessage("book.book_with_id_not_found", [bookId] as Object[], Locale.getDefault()))
        }

        def authorJson = request.JSON
        def authorInstance = new BookAuthor(authorJson)

        book.addToAuthors(authorInstance)

        return authorInstance
    }

    def update(def params, def request) {
        def authorInstance = getBookAuthor(params)
        def authorJson = request.JSON
        authorInstance.properties = authorJson

        authorInstance = authorInstance.merge()

        return authorInstance
    }

    def delete(def params, def request) {
        def authorInstance = getBookAuthor(params)
        authorInstance = authorInstance.delete()

        return authorInstance
    }

    private BookAuthor getBookAuthor(def params) {
        Long authorId
        try {
            authorId = Long.parseLong(params?.id)
        } catch(NumberFormatException ignored) {
            throw new BadRequestException(messageSource.getMessage("params.id_must_be_a_number", null, Locale.getDefault()))
        }

        def authorInstance = BookAuthor.get(authorId)
        if (authorInstance == null) {
            throw new NotFoundException(messageSource.getMessage("bookAuthor.author_with_id_not_found", [authorId] as Object[], Locale.getDefault()))
        }
        return authorInstance
    }
}
