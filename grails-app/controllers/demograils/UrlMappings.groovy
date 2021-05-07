package demograils

import demograils.exception.AbstractHttpException
import demograils.exception.BadRequestException
import demograils.exception.NotFoundException

class UrlMappings {

    static mappings = {
        "/books"(controller: "book", action: "index", method: "GET")
        "/books/$id"(controller: "book", action: "show", method: "GET")
        "/books"(controller: "book", action: "save", method: "POST")
        "/books/$id"(controller: "book", action: "update", method: "PUT")
        "/books/$id"(controller: "book", action: "delete", method: "DELETE")

        "/books/$bookId/authors"(controller: "bookAuthor", action: "index", method: "GET")
        "/books/$bookId/authors/$id"(controller: "bookAuthor", action: "show", method: "GET")
        "/books/$bookId/authors"(controller: "bookAuthor", action: "save", method: "POST")
        "/books/$bookId/authors/$id"(controller: "bookAuthor", action: "update", method: "PUT")
        "/books/$bookId/authors/$id"(controller: "bookAuthor", action: "delete", method: "DELETE")

        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
