
Scenario: when the zipcode and housenumber are entered the street and city should be automaticly filled in
Meta: @id1 scenario1
Given the aanmelding client form is opened
When I enter a valid postcode and huisnummer
Then the straat and plaats should correctly be generated