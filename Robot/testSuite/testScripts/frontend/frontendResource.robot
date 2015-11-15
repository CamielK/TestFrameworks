*** Settings ***
Documentation     A resource file with reusable keywords and variables.
...
...               The system specific keywords created here form our own
...               domain specific language. They utilize keywords provided
...               by the imported Selenium2Library.
Library           Selenium2Library

*** Variables ***
${SERVER}         localhost:8084
${BROWSER}        Chrome
${DELAY}          0
${LOGIN URL}      http://${SERVER}/forms/authentication/login
${WELCOME URL}    http://${SERVER}/forms/list/tasklist/group
${ERROR URL}      http://${SERVER}/forms/authentication/loginsubmit/loginform


*** Keywords ***
Open Browser To Login Page
    Open Browser    ${LOGIN URL}    ${BROWSER}
    Maximize Browser Window
    Set Selenium Speed    ${DELAY}
    Login Page Should Be Open

Login Page Should Be Open
    Title Should Be    Login

Input Username
    [Arguments]    ${username}
    Wait Until Element Is Visible    id=username
    Input Text    id=username    ${username}

Input Password
    [Arguments]    ${password}
    Input Text    id=password    ${password}

Submit Credentials
    Submit form    

Welcome Page Should Be Open
    Location Should Be    ${WELCOME URL}
    Title Should Be    Our tasks

Open Ontslag Formulier
    Click link    New
    Click link    Ontslag Procedure
    Wait Until Element Is Visible    id=update-document.persoonsgegevens.naam

Input Naam
    [Arguments]    ${naamInput}
    Input Text    id=update-document.persoonsgegevens.naam    ${naamInput}
    Click Element    id=update-document.persoonsgegevens.geslacht
    
Function Should Not Be Visible
    Element Should Not Be Visible    id=update-document.persoonsgegevens.functie

Function Should Be Visible
    Element Should Be Visible    id=update-document.persoonsgegevens.functie

Browser Should Be Open
    Location Should Contain    http://${SERVER}/forms/
