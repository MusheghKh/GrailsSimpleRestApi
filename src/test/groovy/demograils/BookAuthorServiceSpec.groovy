package demograils

import grails.testing.gorm.DataTest
import grails.testing.services.ServiceUnitTest
import grails.testing.web.GrailsWebUnitTest
import spock.lang.Specification

class BookAuthorServiceSpec extends Specification implements GrailsWebUnitTest, DataTest, ServiceUnitTest<BookAuthorService>{

    def setupSpec() {
        mockDomain Book
    }

    def setup() {
        List<Book> books = []
        (1..17).each {
            books.add(new Book(name: "book$it"))
        }

        books.eachWithIndex { book, index ->
            (1..17).each {
                def author = new BookAuthor(name: "book${index + 1} author$it")
                book.addToAuthors(author)
            }

        }
        books.each {
            it.save()
        }
    }

    def cleanup() {
        BookAuthor.deleteAll()
        Book.deleteAll()
    }

    void "test list with default params"() {
        given: 'params.max == 10 and params.page == 0 and bookId == 1'
        def params = [max: 10, page: 0, bookId: "1"]

        when: 'get list with default params'
        List<Book> books = service.list(params, null) as List<Book>

        then: 'list size must be 10'
        books.size() == 10
    }
}
