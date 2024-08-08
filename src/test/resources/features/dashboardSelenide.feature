@selenideUiSetup
Feature: Testing feature
  This feature contains steps to create and edit a dashboard in ReportPortal

  Background:
#    When user log in ReportPortal selenide

  @cleanupSelenide
  Scenario Outline: Create dashboard positive result Selenide
    When user clicks add new dashboard button via selenide
    And user enters dashboard name -  "<name>" via selenide
    And user enters dashboard description - "<description>" via selenide
    And user clicks add button via selenide
    Then new dashboard page is created with "<name>" via selenide

    Examples:
      | name   | description |
      | test   |             |
      | test 2 | description |


  Scenario Outline: Create dashboard negative result
    When user clicks add new dashboard button via selenide
    And user enters dashboard name -  "<name>" via selenide
    And user enters dashboard description - "<description>" via selenide
    And user clicks add button via selenide
    Then dashboard name is highlighted red via selenide
    Then dashboard window is opened via selenide

    Examples:
      | name | description |
      |      |             |
      | te   |             |
      |      | description |

  @restoreInitialStateSelenide
  Scenario Outline: Edit dashboard positive result
    When user clicks dashboard edit icon for "<initialName>" via selenide
    And user enters dashboard name -  "<name>" via selenide
    And user enters dashboard description - "<description>" via selenide
    And user clicks update button via selenide
    Then user sees new "<name>" dashboard in a list via selenide

    Examples:
      | initialName        | name       | description     |
      | change name test 1 | new test 1 |                 |
      | change name test 2 | new test 1 | new description |


  Scenario Outline: Edit dashboard negative result
    When user clicks dashboard edit icon for "<initialName>" via selenide
    And user enters dashboard name -  "<name>" via selenide
    And user enters dashboard description - "<description>" via selenide
    Then dashboard name is highlighted red via selenide
    Then dashboard window is opened via selenide

    Examples:
      | initialName        | name | description     |
      | change name test 1 | ne   |                 |
      | change name test 2 |      |                 |
      | change name test 2 |      | new description |