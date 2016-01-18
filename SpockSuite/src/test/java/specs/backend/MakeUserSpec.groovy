package specs.backend
import geb.spock.GebSpec
import pages.BackendLoginPage
import pages.PersonPage
import spock.lang.Stepwise


/**
 *
 * This class contains test steps to execute the 'make user' test case
 *
 * Created by camiel on 11/25/15.
 */
@Stepwise
class MakeUserSpec extends GebSpec{

    //method is executed at the start of this test case
    def setupSpec() {
        to BackendLoginPage
        usernameField.value("admin")
        passwordField.value("dUfKB2yacJ")
        submitBtn.click()
        assert title == "Workflow Definition List"
    }

    def "Open person list"() {
        when:
        to PersonPage
        then:
        assert newUserBtn.displayed
    }

    def "Open new user form"() {
        when:
        newUserBtn.click()
        then:
        assert usernameField.displayed
    }

    def "Submit new user details"() {
        when:
        usernameField.value("testUser123")
        realnameField.value("Test User")
        passwordBtn.click()
        passwordField.value("test123")
        password2Field.value("test123")
        newUserOKBtn.click()
        then:
        assert newUserBtn.displayed
    }

    def "Check if user was added"() {
        when:
        idSortBtn.click()
        then:
        assert true

        when:
        String userId = firstIdCell.text()
        String userName = firstNameCell.text()
        then:
        assert userName == "testUser123"

    }

    def "Delete new user"() {
        when:
        String userName = firstNameCell.text()
        if (userName == "testUser123") {
            deleteUserBtn.click()
            confirmDeleteBtn.click()
            Thread.sleep(300)
        }
        userName = firstNameCell.text()
        then:
        assert userName != "testUser123"

    }

}