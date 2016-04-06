*** Settings ***
Documentation     A test suite to test the wfp backend login.
Resource          backendResource.robot

*** Test Cases ***
Backend setup
    Open Browser To Login Page

Invalid password
    Input Username    admin
    Input Password    123
    Submit Credentials
    Login Page Should Be Open

Invalid username
    Input Username    piet
    Input Password    piet
    Submit Credentials
    Login Page Should Be Open

Invalid password and username
    Input Username    piet
    Input Password    123
    Submit Credentials
    Login Page Should Be Open

Valid password and username
    Input Username    admin
    Input Password    admin
    Submit Credentials
    Welcome Page Should Be Open

Backend teardown
    Browser Should Be Open
    [Teardown]    Close Browser
