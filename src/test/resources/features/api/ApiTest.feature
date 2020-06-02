@api
Feature: Api Test

  Scenario: API Test

    Given the "api.endpoint" endpoint is available

    When I set the endpoint

    And I get "comments.resource" resource

    Then I should get status code 200 back


