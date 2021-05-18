package demograils

class BookAuthor {

    Long id
    String name

    static belongsTo = [book: Book]

    static constraints = {
        name nullable: false, blank: false, size: 1..100
    }
}
