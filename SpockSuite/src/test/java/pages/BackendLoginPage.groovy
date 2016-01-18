package pages

import geb.Page

/**
 *
 * This class contains all information of the user page and make user form
 *
 * Created by camiel on 11/23/15.
 */
class BackendLoginPage extends Page{

    static url = "http://localhost:8082/workflow4people"
    static at = { title == "Login"}

    //content describes locators for all elements that need to be interacted with
    static content = {
        usernameField { $("input", name: "j_username") }
        passwordField { $("input", name: "j_password") }
        submitBtn { $("input", type: "submit") }
    }
}
