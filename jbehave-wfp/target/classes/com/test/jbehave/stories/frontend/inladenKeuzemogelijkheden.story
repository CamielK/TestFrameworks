
Scenario: form test aanmelding client, expand aanhef list
Meta: @id1 scenario1
Given the aanmelding client form is opened
When I expand the <list> list
Then the list should be filled with at least <value1> and <value2>

Examples:
|list|value1|value2|
|aanhef|Heer|Mevrouw|
|geslacht|Mannelijk|Vrouwelijk|
|burgerlijke staat|Gehuwd|Gescheiden|
|type relatie|Zoon|Dochter|