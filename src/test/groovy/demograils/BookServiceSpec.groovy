package demograils

import demograils.exception.NotFoundException
import grails.converters.JSON
import grails.testing.gorm.DataTest
import grails.testing.services.ServiceUnitTest
import grails.testing.web.GrailsWebUnitTest
import org.grails.plugins.testing.GrailsMockHttpServletRequest
import spock.lang.Specification

class BookServiceSpec extends Specification implements GrailsWebUnitTest, DataTest, ServiceUnitTest<BookService> {

    def setupSpec() {
        mockDomain Book
        mockDomain BookAuthor
    }

    def setup() {
        List<Book> books = new ArrayList<>()
        (1..17).each {
            books.add(new Book(id: it, name: "name$it"))
        }
        Book.saveAll(books)
    }

    def cleanup() {
        Book.deleteAll()
    }

    void "test list with default params"() {
        given: 'params.max == 10 and params.page == 0'
        def params = [max: 10, page: 0]

        when: 'get list with default params'
        List<Book> books = service.list(params, null) as List<Book>

        then: 'books size must be 10'
        books.size() == 10
    }

    void "test list second page"() {
        given: 'params.max == 10 and params.page == 1'
        def params = [max: 10, page: 1]

        when: 'get list second page'
        List<Book> books = service.list(params, null) as List<Book>

        then: 'books size must be 7'
        books.size() == 7
    }

    void "test list half page"() {
        given: 'params.max == 5 and params.page == 0'
        def params = [max: 5, page: 0]

        when: 'get list with default params'
        List<Book> books = service.list(params, null) as List<Book>

        then: 'books size must be 10'
        books.size() == 5
    }

    void "test get Book by id"() {
        given: 'id == 1'
        def params = [id: "1"]

        when: 'get Book by id'
        Book book = service.single(params, null)

        then: 'book must be non null'
        book != null
    }

    void "test get Book by id not found"() {
        given: 'id == 123'
        messageSource.addMessage("book.book_with_id_not_found", Locale.getDefault(), "book with id {0} not found")
        def params = [id: "123"]

        when: 'get Book by id'
        Book book = service.single(params, null)

        then: "NotFoundException will thrown"
        NotFoundException exception = thrown(NotFoundException)
        exception.message == messageSource.getMessage("book.book_with_id_not_found", [params.id] as Object[], Locale.getDefault())
    }

    void "test save Book"() {
        given: 'book object in httpServletRequest'
        GrailsMockHttpServletRequest request = new GrailsMockHttpServletRequest()
        Book requestBook = new Book(name: "some name")
        request.setJSON(new JSON(requestBook))

        when: 'save Book'
        Book book = service.save(null, request)

        then: 'book name must be same as in request'
        book.name == requestBook.name
    }

    void "update exiting book"() {
        given: 'book object in httpServletRequest'
        GrailsMockHttpServletRequest request = new GrailsMockHttpServletRequest()
        Book requestBook = new Book(name: "some name")
        request.setJSON(new JSON(requestBook))
        def params = ["id": "1"]

        when: 'update Book'
        Book book = service.update(params, request)

        then: 'book name must be same as in request'
        book.name == requestBook.name
    }

    void "update book not found"() {
        given: 'book object in httpServletRequest and params with id'
        messageSource.addMessage("book.book_with_id_not_found", Locale.getDefault(), "book with id {0} not found")

        GrailsMockHttpServletRequest request = new GrailsMockHttpServletRequest()
        Book requestBook = new Book(name: "some name")
        request.setJSON(new JSON(requestBook))
        def params = ["id": "123"]

        when: 'update Book'
        Book book = service.update(params, request)

        then: 'book name must be same as in request'
        NotFoundException exception = thrown(NotFoundException)
        exception.message == messageSource.getMessage("book.book_with_id_not_found", [params.id] as Object[], Locale.getDefault())
    }

    void "delete book" () {
        given: 'params with id'
        def params = ["id": "2"]

        when: 'delete Book'
        Book book = service.delete(params, null)

        then: 'no exception is thrown'
    }

    void "delete book not found"() {
        given: 'params with id'
        messageSource.addMessage("book.book_with_id_not_found", Locale.getDefault(), "book with id {0} not found")

        def params = ["id": "234"]

        when: 'delete Book'
        Book book = service.delete(params, null)

        then: 'book must be not null'
        NotFoundException exception = thrown(NotFoundException)
        exception.message == messageSource.getMessage("book.book_with_id_not_found", [params.id] as Object[], Locale.getDefault())
    }
}
