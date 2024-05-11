@apiSetup
Feature: Testing feature
  This feature contains steps to get dashboard

  Scenario: 01 | Get all dashboards - happy path
    When the Apache "GET" request is sent to the '166078' endpoint without params
    Then the status code is 200 using Apache Http
    And retrieved data size is equal to 13 using Apache Http

  Scenario: 02 | Get all dashboards - negative path
    When the Apache "GET" request is sent to the '166077' endpoint without params
    Then the status code is 404 using Apache Http
    And  message contains: "Dashboard with ID '166077' not found on project" using Apache Http

  @createDashboardApache
  Scenario: 03 | Delete dashboards - positive path
    When the Apache "DELETE" request is sent with endpoint from precondition
    Then the status code is 200 using Apache Http
    And  the message contains the ID of the deleted dashboard using Apache Http

  Scenario: 04 | Delete dashboards with the same id - negative path
    When the Apache "DELETE" request is sent with endpoint from precondition
    Then the status code is 404 using Apache Http
    And error message dashboard with ID from precondition is not found using Apache Http