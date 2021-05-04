package demograils

import grails.gorm.transactions.Transactional

@Transactional
class BookService {

    def list(def params, def request) {
        def criteria = Book.createCriteria()
        def result = criteria.list(max: params.max, offset: params.offset) {

        }
        return result
    }

    def single(def params, def request) {
        return Book.findById(params?.id)
    }

    def save(def params, def request) {
        def bookJson = request.JSON
        def bookInstance = new Book(bookJson)

        bookInstance = bookInstance.save()
        return bookInstance
    }

    def update(def params, def request) {
        def bookJson = request.JSON
        def bookInstance = Book.get(params?.id)
        bookInstance.properties = bookJson

        bookInstance = bookInstance.merge()
        return bookInstance
    }

    def delete(def params, def request) {
        def bookInstance = Book.get(params?.id)

        bookInstance = bookInstance.delete()

        return bookInstance
    }
}
