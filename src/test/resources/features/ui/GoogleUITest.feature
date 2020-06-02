@ui
Feature: Google UI Test

  Scenario: Test the Google search function

    Given I am on the Google homepage

    When I enter "cybersmart" into the search box

    Then I read a page that is titled "cybersmart - Google Search"

