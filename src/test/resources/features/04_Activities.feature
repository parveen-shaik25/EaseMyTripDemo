Feature: Search Activities on EaseMyTrip
 @regression
  Scenario: Selecting city, date and filtering day trips
    Given The user open the browser and navigate to EaseMyTrip
    When The user click activity
    And The user enters Ba as the partial city
    And The user choose September 2025 and 25 as the date
    And The user click the search button
    And The user enter Delhi as full city and select New Delhi
    And The user click search button again
    Then The user sort results by price low to high
    And The user select Day Trips checkbox
    And The user print the list of city names displayed