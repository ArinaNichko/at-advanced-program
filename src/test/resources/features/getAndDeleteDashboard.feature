@apiSetup
Feature: Testing feature
  This feature contains steps to get dashboard

  Scenario: 01 | Get all dashboards - happy path
    When the GET request is sent to the '166078' endpoint without params
    Then the status code is 200
    And retrieved data size is equal to 13

  Scenario: 02 | Get all dashboards - negative path
    When the GET request is sent to the '166077' endpoint without params
    Then the status code is 404
    And  message contains: "Dashboard with ID '166077' not found on project"

  @createDashboard
  Scenario: 03 | Delete dashboards - positive path
    When the DELETE request is sent with endpoint from precondition
    Then the status code is 200
    And  the message contains the ID of the deleted dashboard

  Scenario: 03 | Delete dashboards with the same id - negative path
    When the DELETE request is sent with endpoint from precondition
    Then the status code is 404
    And error message dashboard with ID from precondition is not found