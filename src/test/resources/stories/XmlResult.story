Meta:

Narrative:
As a development team
I want to read/write seat allocations results from/to an xml

Scenario: Read results from unordered xml
Given result is in file stories/xml/unordered-result.xml
Then result seat #3 is Party3
Then result seat #1 is Party3
Then result seat #2 is Party1
Then result seat #0 is Party2
