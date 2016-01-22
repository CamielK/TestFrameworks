
Scenario: form test aanmelding client, valid BSN check
Meta: @id1 scenario1
Given the aanmelding client form is opened
When I enter a valid BSN
Then the BSN should be accepted

Scenario: form test aanmelding client, invalid BSN check
Meta: @id2 scenario2
Given the aanmelding client form is opened
When I enter an invalid BSN
Then the BSN should be rejected

Scenario: form test aanmelding client, already used BSN check
Meta: @id3 scenario3
Given the aanmelding client form is opened
When I enter a used BSN
Then the BSN should be rejected