package demograils

class BootStrap {

    def init = { servletContext ->
        if (Book.count < 1) {
            List<Book> books = []
            (1..100).each {
                books.add(new Book(name: "book$it"))
            }

            books.eachWithIndex { book, index ->
                (1..100).each {
                    def author = new BookAuthor(name: "book${index + 1} author$it")
                    book.addToAuthors(author)
                }

            }
            books.each {
                it.save()
            }
        }
    }
    def destroy = {
        if (Book.count > 0) {
            Book.deleteAll()
        }
        if (BookAuthor.count > 0) {
            BookAuthor.deleteAll()
        }
    }
}
