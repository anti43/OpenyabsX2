package openyabsx2

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(controller:"contact")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
