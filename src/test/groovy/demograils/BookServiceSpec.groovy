package demograils

import demograils.exception.NotFoundException
import grails.testing.gorm.DataTest
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class BookServiceSpec extends Specification implements DataTest, ServiceUnitTest<BookService> {

    def setupSpec() {
        mockDomain Book
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

}
