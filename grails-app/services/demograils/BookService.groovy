package demograils

import demograils.exception.BadRequestException
import demograils.exception.NotFoundException
import grails.gorm.transactions.Transactional

@Transactional
class BookService {

    def messageSource

    def list(def params, def request) {
        if (params.max < 0) {
            throw new BadRequestException(messageSource.getMessage("pagination.max_can_not_be_less_than_zero", null, Locale.getDefault()))
        }
        if (params.offset < 0) {
            throw new BadRequestException(messageSource.getMessage("pagination.offset_can_not_be_less_than_zero", null, Locale.getDefault()))
        }

        def criteria = Book.createCriteria()
        def result = criteria.list(max: params.max, offset: params.offset) {}
        return result
    }

    def single(def params, def request) {
        Long id
        try {
            id = Long.parseLong(params?.id)
        } catch(NumberFormatException ignored) {
            throw new BadRequestException(messageSource.getMessage("params.id_must_be_a_number", null, Locale.getDefault()))
        }
        def book = Book.findById(id)
        if (book == null) {
            throw new NotFoundException(messageSource.getMessage("book.book_with_id_not_found", [id] as Object[], Locale.getDefault()))
        }
        return book
    }

    def save(def params, def request) {
        def bookJson = request.JSON
        def bookInstance = new Book(bookJson)

        bookInstance = bookInstance.save()
        return bookInstance
    }

    def update(def params, def request) {
        def bookInstance = getBookInstance(params)
        def bookJson = request.JSON
        bookInstance.properties = bookJson

        bookInstance = bookInstance.merge()
        return bookInstance
    }

    def delete(def params, def request) {
        def bookInstance = getBookInstance(params)

        bookInstance = bookInstance.delete()

        return bookInstance
    }

    private Book getBookInstance(def params) {
        Long id
        try {
            id = Long.parseLong(params?.id)
        } catch(NumberFormatException ignored) {
            throw new BadRequestException(messageSource.getMessage("params.id_must_be_a_number", null, Locale.getDefault()))
        }

        def bookInstance = Book.get(id)
        if (bookInstance == null) {
            throw new NotFoundException(messageSource.getMessage("book.book_with_id_not_found", [id] as Object[], Locale.getDefault()))
        }
        return bookInstance
    }
}
