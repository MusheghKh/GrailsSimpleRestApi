package demograils

class Book {

    Long id
    String name

    static hasMany = [authors: BookAuthor]

    static constraints = {
        id nullable: false
        name blank: false
    }
}
