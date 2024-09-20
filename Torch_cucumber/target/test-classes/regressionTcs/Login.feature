Feature: Login Functionality
  Scenario : Successful login with valid credentials
    Given The user opens the Login page
    When User enters valid username "<username>", password "<pwd>", and confirm password "<confirmpwd>"
    Then I should be logged in successfully



