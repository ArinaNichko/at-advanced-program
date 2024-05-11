@apiSetup
Feature: Testing feature
  This feature contains steps to get dashboard

  Scenario: 01 | Post new dashboard - happy path
    When the Apache "POST" request is sent with body from file:
      | path | src/test/resources/requests/post-new-dashboard.json |
    Then the status code is 201 using Apache Http

  @apacheApiCleanup
  Scenario: 02 | Post duplicate dashboard - negative path
    When the Apache "POST" request is sent with body from file:
      | path | src/test/resources/requests/post-new-dashboard.json |
    Then the status code is 409 using Apache Http
    And message contains: "Resource 'new' already exists. You couldn't create the duplicate." using Apache Http

  Scenario: 03 | Create new dashboard with missing required field - negative path
    When the Apache "POST" request is sent with body from file:
      | path | src/test/resources/requests/post-new-dashboard-bad-path.json |
    Then the status code is 400 using Apache Http
    And message contains: "Incorrect Request. [Field 'name' should not be null.]" using Apache Http

  Scenario: 04 | Put dashboard with new name and description - happy path
    When the Apache "PUT" request is sent to endpoint "166078" with body from file:
      | path | src/test/resources/requests/put-dashboard.json |
    Then the status code is 200 using Apache Http
    And message contains: "Dashboard with ID = '166078' successfully updated" using Apache Http

  @apacheApiInitialState
  Scenario: 05 | Put dashboard with new name and description - happy path
    When the Apache "PUT" request is sent to endpoint "166078" with body from file:
      | path | src/test/resources/requests/put-dashboard.json |
    Then the status code is 200 using Apache Http
    And message contains: "Dashboard with ID = '166078' successfully updated" using Apache Http

  Scenario: 06 | Put dashboard with new description - negative path
    When the Apache "PUT" request is sent to endpoint "166078" with body from file:
      | path | src/test/resources/requests/put-dashboard-bad-path.json |
    Then the status code is 400 using Apache Http
    And message contains: "Incorrect Request. [Field 'name' should not be null.]" using Apache Http
