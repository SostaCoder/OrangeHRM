
Feature: Login to website successfully

    Scenario: The user login with valid username and valid password
        Given The user open the website
        When The user fill username and password and click on login
        Then The user should navigate to home page