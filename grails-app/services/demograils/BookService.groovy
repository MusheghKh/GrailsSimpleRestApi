package demograils

import demograils.exception.AbstractHttpException
import demograils.exception.BadRequestException
import demograils.exception.NotFoundException
import grails.converters.JSON
import grails.gorm.transactions.Transactional
import org.grails.web.json.JSONObject

import javax.servlet.http.HttpServletRequest

@Transactional
class BookService implements Pagination{

    def messageSource

    def list(def params, def request) throws AbstractHttpException {
        validateParamsForPagination(messageSource, params)

        def criteria = Book.createCriteria()
        def result = criteria.list(max: params.max, offset: params.page * 10) {}
        return result
    }

    def single(def params, def request) throws AbstractHttpException {
        return getBookInstance(params)
    }

    def save(def params, def request) {
        JSONObject bookJson = request.JSON
        Book bookInstance = new Book(bookJson)
        bookInstance.id = null
        bookInstance.authors = Collections.emptySet()

        bookInstance = bookInstance.save()
        return bookInstance
    }

    def update(def params, def request) throws AbstractHttpException {
        Book bookInstance = getBookInstance(params)
        JSONObject bookJson = request.JSON
        bookJson.put("id", bookInstance.id)
        bookJson.put("authors", [])
        bookInstance.properties = bookJson

        bookInstance = bookInstance.merge()
        return bookInstance
    }

    def delete(def params, def request) throws AbstractHttpException {
        def bookInstance = getBookInstance(params)

        bookInstance.delete()
    }

    private Book getBookInstance(def params) throws AbstractHttpException {
        Long id = getLongId(params)

        def bookInstance = Book.findById(id)
        if (bookInstance == null) {
            throw new NotFoundException(messageSource.getMessage("book.book_with_id_not_found", [id] as Object[], Locale.getDefault()))
        }
        return bookInstance
    }

    private Long getLongId(def params) throws AbstractHttpException {
        try {
            return Long.parseLong(params?.id)
        } catch (NumberFormatException ignored) {
            throw new BadRequestException(messageSource.getMessage("params.id_must_be_a_number", null, Locale.getDefault()))
        }
    }
}
