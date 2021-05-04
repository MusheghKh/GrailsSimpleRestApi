package demograils

import grails.gorm.transactions.Transactional

@Transactional
class BookAuthorService {

    def serviceMethod() {

    }

    def list(def params, def request) {
        def bookId = params.bookId
        return BookAuthor.where {
            book.id == bookId
        }.list()
    }

    def single(def params, def request) {
        def bookId = params.bookId
        def authorId = params?.id

        def criteria = BookAuthor.createCriteria()
        def result = criteria.get {
            and {
                eq("book.id", Long.parseLong(bookId))
                eq('id', Long.parseLong(authorId))
            }
        }
        return result
    }

    def save(def params, def request) {
        def bookId = params.bookId
        def book = Book.get(bookId)

        def authorJson = request.JSON
        def authorInstance = new BookAuthor(authorJson)

        book.addToAuthors(authorInstance)

        return authorInstance
    }

    def update(def params, def request) {
        def authorId = params?.id
        def authorJson = request.JSON

        def authorInstance = BookAuthor.get(authorId)
        authorInstance.properties = authorJson

        authorInstance = authorInstance.merge()

        return authorInstance
    }

    def delete(def params, def request) {
        def authorId = params?.id

        def authorInstance = BookAuthor.get(authorId)

        authorInstance = authorInstance.delete()

        return authorInstance
    }
}
