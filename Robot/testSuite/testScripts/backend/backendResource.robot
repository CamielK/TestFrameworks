*** Settings ***
Documentation     A resource file with reusable keywords and variables.
...
...               The system specific keywords created here form our own
...               domain specific language. They utilize keywords provided
...               by the imported Selenium2Library.
Library           Selenium2Library

*** Variables ***
${SERVER}         localhost:8082
${BROWSER}        Chrome
${DELAY}          0
${LOGIN URL}       http://${SERVER}/workflow4people/login/auth
${WELCOME URL}     http://${SERVER}/workflow4people/workflowDefinition/list
${ERROR URL}       http://${SERVER}/workflow4people/login/auth?login_error=1

*** Keywords ***
Open Browser To Login Page
    Open Browser    ${LOGIN URL}    ${BROWSER}
    Maximize Browser Window
    Set Fast Speed
    Login Page Should Be Open

Login Page Should Be Open
    Title Should Be    Login
    
Input Username
    [Arguments]    ${username}
    Wait Until Element Is Visible    id=j_username
    Input Text    id=j_username    ${username}

Input Password
    [Arguments]    ${password}
    Input Text    id=j_password    ${password}

Submit Credentials
    Submit form    

Welcome Page Should Be Open
    Location Should Be    ${WELCOME URL}
    Title Should Be    Workflow Definition List

Browser Should Be Open
    Location Should Contain    http://${SERVER}/

Open Person List
    Click link    Admin
    Click link    Users
    Wait Until Element Is Visible    id=newButton_detailTable_person
    Title Should Be    Person List

Add New User
    Click Element    id=newButton_detailTable_person
    Wait Until Element Is Visible    id=username

Enter New User Details
    Input Text    id=username    testUser123
    Input Text    id=userRealName    test user
    Input Text    id=givenName   test
    Input Text    id=familyName    user
    Input Text    id=email    testuser@gmail.com

Enter New User Password
    Click link    Password
    Input Text    id=newPassword    test123
    Input Text    id=newPassword2    test123
    Click Element    xpath=//span[text()='OK']
    Wait Until Element Is Visible    id=newButton_detailTable_person

User Should Be Added
    #sorts list to Id descending > new user should be first table entry
    Set Slow Speed
    ${idTable}    Get Table Cell    detailTable_person    1    1
    Click Element     xpath=//th[text()='${idTable}']
    ${username}    Get Table Cell    detailTable_person    2    2
    Set Fast Speed
    Should Be Equal    ${username}    testUser123
    Run Keyword If    '${username}' == 'testUser123'    Delete New User

Delete New User
    # deletes first table entry (this will be the new user)
    ${userID}    Get Table Cell    detailTable_person    2    1
    Click Element    xpath=//span[@onclick="dialog.deleteDialog('${userID}','person',{ refresh : 'null'}, null)"]
    Wait Until Element Is Visible    xpath=//span[text()='Delete']
    Click Element    xpath=//span[text()='Delete']
    Wait Until Element Is Visible    id=newButton_detailTable_person

Set Slow Speed
    Set Selenium Speed    0.2

Set Fast Speed
    Set Selenium Speed    0
