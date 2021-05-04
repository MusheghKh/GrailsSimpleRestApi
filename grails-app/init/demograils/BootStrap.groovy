package demograils

class BootStrap {

    def init = { servletContext ->
        def book1 = new Book(name: "book1")
        def book2 = new Book(name: "book2")
        def book3 = new Book(name: "book3")

        100.times {
            def author = new BookAuthor(name: "book1 author$it")
            book1.addToAuthors(author).save()
        }
        100.times {
            def author = new BookAuthor(name: "book2 author$it")
            book2.addToAuthors(author).save()
        }
        100.times {
            def author = new BookAuthor(name: "book3 author$it")
            book3.addToAuthors(author).save()
        }
    }
    def destroy = {
    }
}
