*** Settings ***
Documentation     A test suite to test the wfp frontend login.
Resource          frontendResource.robot

*** Test Cases ***
Suite setup
    Open Browser To Login Page

Invalid password
    Input Username    demo
    Input Password    123
    Submit Credentials
    Login Page Should Be Open

Invalid username
    Input Username    piet
    Input Password    demo
    Submit Credentials
    Login Page Should Be Open

Invalid password and username
    Input Username    piet
    Input Password    123
    Submit Credentials
    Login Page Should Be Open

Valid password and username
    Input Username    demo
    Input Password    demo
    Submit Credentials
    Welcome Page Should Be Open

Suite teardown
    Browser Should Be Open
    [Teardown]    Close Browser
