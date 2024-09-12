
Feature: Test Scenario for the Task

  Scenario: The user run the whole scenario
    Given The user open the website
      And The user fill username and password and click on login
      And Click on Admin tab on the left side menu
        # Get the number of records found
      And Click on add button
      And Fill the required data
      And Click on save button
        # Verify that the number of records increased by 1
      And Search with the username for the new user
      When Delete the new user
      Then Verify that the number of records decreased by 1