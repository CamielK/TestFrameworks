package specs.backend
import geb.spock.GebSpec
import pages.BackendLoginPage
import spock.lang.Stepwise
/**
 *
 * This class contains test steps to execute the backend login test case
 *
 * Created by camiel on 11/24/15.
 */
@Stepwise
class BackendLoginSpec extends GebSpec {

    def "Navigate to backend"() {
        when:
        to BackendLoginPage
        then:
        title == "Login" //title login means login page was succesfully opened
    }

    def "Enter invalid credentials"() {
        when:
        usernameField.value("piet")
        passwordField.value("123")
        and:
        submitBtn.click()
        then:
        title == "Login" //title remains the same wich means login failed
    }

    def "Enter valid credentials"() {
        when:
        usernameField.value("admin")
        passwordField.value("admin")
        and:
        submitBtn.click()
        then:
        title == "Workflow Definition List" //title changes wich means login was succesful
    }
}
