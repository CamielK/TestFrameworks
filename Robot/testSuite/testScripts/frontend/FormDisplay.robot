*** Settings ***
Documentation     A test suite to test the ontslagRequest wfp form.
Resource          frontendResource.robot

*** Test Cases ***
Suite setup
    Open Browser To Login Page
    Input Username    demo
    Input Password    demo
    Submit Credentials
    Welcome Page Should Be Open

Dynamic element visibility
    Open Ontslag Formulier
    Function Should Not Be Visible
    Input Naam    Piet
    Function Should Be Visible
    Input Naam    ${EMPTY}
    Function Should Not Be Visible

Suite teardown
    Browser Should Be Open
    [Teardown]    Close Browser
