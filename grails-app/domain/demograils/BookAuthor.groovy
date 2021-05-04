package demograils

class BookAuthor {

    Long id
    String name

    static belongsTo = [book: Book]

    static constraints = {
        id nullable: false
        name blank: false
    }
}
