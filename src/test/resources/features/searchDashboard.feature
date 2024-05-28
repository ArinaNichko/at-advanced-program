@uiSetup
Feature: Testing feature
  This feature contains steps to create and edit a dashboard in ReportPortal

  Background:
    When user log in ReportPortal


  Scenario Outline: Search dashboard positive result
    When user searches by "<partialName>"
    Then dashboard list size equals expected result

    Examples:
      | partialName |
      | test        |
      | TEST        |
      | teST        |
      |             |

  Scenario: Search dashboard negative result
    When user searches by "regression"
    But user sees zero result
