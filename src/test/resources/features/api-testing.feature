@smoke
Feature: User Management API

# mvn test -D"cucumber.filter.tags=@smoke"
#  Background: Delete existing users
#    Given existing users with emails "testuser04101993@testmail.coms" and "testuser04101993ups@testmail.coms" are checked and deleted

  @test
  Scenario: Create and validate a new user
    When a new user is created with valid details
    Then the user creation is successful and an "id" is returned
    And the user details match the input data

  Scenario: Retrieve and validate the user
    When the user is retrieved
    Then the user details match the input data