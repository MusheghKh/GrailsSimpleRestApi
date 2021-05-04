package demograils

class Book {

    Long id
    String name

    static hasMany = [authors: BookAuthor]

    static constraints = {
        name blank: false, size: 1..100
    }

}
