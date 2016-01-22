
Scenario: Checks if client information is correctly searchable
Given the anamnese form is opened
When I search for a client based on <searchtype>
Then the correct client should be displayed

Examples:
|searchtype|
|Achternaam|
|BSN|
|Clientnummer|