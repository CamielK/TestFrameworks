package specs.frontend
import geb.spock.GebSpec
import pages.FrontendLoginPage
import spock.lang.Stepwise


//This class contains test steps to execute the frontend login test case
@Stepwise
class FrontendLoginSpec extends GebSpec {

    def "Navigate to front end"() {
        when:
        to FrontendLoginPage
        then:
        title == "Inloggen"
    }

    def "Enter invalid credentials"() {
        when:
        usernameField.value("piet")
        passwordField.value("123")
        and:
        submitBtn.click()
        then:
        title == "Inloggen"
    }

    def "Enter valid credentials"() {
        when:
        usernameField.value("demo")
        passwordField.value("demo")
        and:
        submitBtn.click()
        then:
        title == "Welkom bij Opleiding wijkverpleging"
    }
}