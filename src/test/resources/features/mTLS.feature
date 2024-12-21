@mTLS
Feature: mTLS Test

  Scenario: Verify mTLS secured endpoint
    Given I have the client certificate and key
    When I make a GET request to the secured endpoint
    Then I should receive a 200 status code
