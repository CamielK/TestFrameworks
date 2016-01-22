Story: Frontend Login

Narrative:

In order to access the workflow4people frontend
As a demo user
I want to login into the application

Scenario: A workflo4people frontend login attempt with both invalid credentials
Meta: @id1 scenario1
Given The workflow4people frontend is started
When I enter invalid frontend credentials
And I log in to the frontend
Then I should not be logged in to the frontend

Scenario: A workflo4people frontend login attempt with both valid credentials
Meta: @id2 scenario2
Given The workflow4people frontend is started
When I enter valid frontend credentials
And I log in to the frontend
Then I should be logged in to the frontend

