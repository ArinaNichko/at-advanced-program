Feature: Testing feature
  This feature contains steps to create and edit a dashboard in ReportPortal

  Background:
    When user log in ReportPortal

  @cleanup
  Scenario Outline: Create dashboard positive result
    When user clicks add new dashboard button
    And user enters dashboard name -  "<name>"
    And user enters dashboard description - "<description>"
    And user clicks add button
    Then new dashboard page is created with "<name>"

    Examples:
      | name   | description |
      | test   |             |
      | test 2 | description |


  Scenario Outline: Create dashboard negative result
    When user clicks add new dashboard button
    And user enters dashboard name -  "<name>"
    And user enters dashboard description - "<description>"
    And user clicks add button
    Then dashboard name is highlighted red
    Then dashboard window is opened

    Examples:
      | name | description |
      |      |             |
      | te   |             |
      |      | description |

  @restoreInitialState
  Scenario Outline: Edit dashboard positive result
    When user clicks dashboard edit icon for "<initialName>"
    And user enters dashboard name -  "<name>"
    And user enters dashboard description - "<description>"
    And user clicks update button
    Then user sees new "<name>" dashboard in a list

    Examples:
      | initialName        | name       | description     |
      | change name test 1 | new test 1 |                 |
      | change name test 2 | new test 1 | new description |


  Scenario Outline: Edit dashboard negative result
    When user clicks dashboard edit icon for "<initialName>"
    And user enters dashboard name -  "<name>"
    And user enters dashboard description - "<description>"
    Then dashboard name is highlighted red
    Then dashboard window is opened

    Examples:
      | initialName        | name | description     |
      | change name test 1 | ne   |                 |
      | change name test 2 |      |                 |
      | change name test 2 |      | new description |
