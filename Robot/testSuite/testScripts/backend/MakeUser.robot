*** Settings ***
Documentation     A test suite to test the making of a new user
Resource          backendResource.robot

*** Test Cases ***
Backend setup
    Open Browser To Login Page
    Input Username    admin
    Input Password    admin
    Submit Credentials
    Welcome Page Should Be Open

Create new user
    Open Person List
    Add New User
    Enter New User Details
    Enter New User Password

Validate and cleanup new user
    User Should Be Added

Backend teardown
    Browser Should Be Open
    [Teardown]    Close Browser
