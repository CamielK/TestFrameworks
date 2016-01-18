package pages

import geb.Page
import org.openqa.selenium.By

/**
 *
 * This class contains all information of the user page and make user form
 *
 * Created by camiel on 11/25/15.
 */
class PersonPage extends Page{

    static url = "http://localhost:8082/workflow4people/person/list"
    static at = { title == "Person List"}

    //content describes locators for all elements that need to be interacted with
    static content = {
        adminBtn(wait: true) { $("a", text: "Admin") }
        UsersBtn { $("a", text: "Users") }
        newUserBtn(required: false, wait: true) { $(By.xpath("//span[@onclick=\"dialog.formDialog(null,'person', { refresh : 'detailTable_person'}, {})\"]")) }
        usernameField { $("input", id: "username") }
        realnameField { $("input", id: "userRealName") }
        passwordBtn { $("a", href: "#dialog_org_workflow4people_Person_null_Password") }
        passwordField(wait: true) { $("input", id: "newPassword") }
        password2Field { $("input", id: "newPassword2") }
        newUserOKBtn { $("span", text: "OK") }

        idSortBtn { $("th", text: "Id") }
        firstIdCell { $("#detailTable_person").find("tr").find("td:nth-child(1n)") }
        firstNameCell { $("#detailTable_person").find("tr").find("td:nth-child(2n)") }

        deleteUserBtn { $(By.xpath("//span[@onclick=\"dialog.deleteDialog('"+ firstIdCell.text() +"','person',{ refresh : 'null'}, null)\"]")) }
        confirmDeleteBtn(wait: true) { $(By.xpath("//span[text()='Delete']")) }
    }
}
