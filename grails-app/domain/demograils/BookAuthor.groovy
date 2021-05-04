package demograils

class BookAuthor {

    Long id
    String name

    static belongsTo = [book: Book]

    static constraints = {
        name blank: false, size: 1..100
    }
}
