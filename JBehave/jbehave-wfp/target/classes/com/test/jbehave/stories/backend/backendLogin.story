Story: backend Login

Narrative:

In order to access the workflow4people backend
As a demo user
I want to login into the application


Scenario: A workflo4people backend login attempt with invalid credentials
Meta: @id1 scenario1
Given The workflow4people backend is started
When I enter invalid backend credentials
And I log in to the backend
Then I should not be logged in to the backend

Scenario: A workflo4people backend login attempt with valid credentials
Meta: @id2 scenario2
Given The workflow4people backend is started
When I enter valid backend credentials
And I log in to the backend
Then I should be logged in to the backend