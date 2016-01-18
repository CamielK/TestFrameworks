package pages

import geb.Page

/**
 *
 * This class contains all information of the user page and make user form
 *
 * Created by camiel on 11/20/15.
 */
class FrontendLoginPage extends Page{

    static url = "http://localhost:8084/forms"
    static at = { title == "Inloggen"}

    //content describes locators for all elements that need to be interacted with
    static content = {
        usernameField { $("input", name: "username") }
        passwordField { $("input", name: "password") }
        submitBtn { $("input", class: "button submit btn") }
    }

}

