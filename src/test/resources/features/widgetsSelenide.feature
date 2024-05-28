@selenideUiSetup
Feature: Testing feature
  This feature contains steps to create and edit a dashboard in ReportPortal

  Background:
    When user log in ReportPortal selenide

  @cleanupWidgetSelenide
  Scenario Outline: Create widget positive result Selenide
    When user chooses "DEMO DASHBOARD" dashboard via selenide
    And user clicks add new widget button
    And user chooses widget "<type>" from list
    And user clicks next step
    And user chooses filter from the list below
    And user clicks next step
    And user enters widget name -  "<name>"
    And user clicks add widget button
    Then new widget is created with "<name>" in dashboard

    Examples:
      | name   | type              |
      | test 1 | statisticTrend    |
      | test 2 | launchStatistics  |
      | test 3 | investigatedTrend |


  Scenario Outline: Create widget negative result
    When user chooses "DEMO DASHBOARD" dashboard via selenide
    And user clicks add new widget button
    And user chooses widget "<type>" from list
    And user clicks next step
    And user chooses filter from the list below
    And user clicks next step
    And user enters widget name -  "<name>"
    And user clicks add widget button
    Then widget name is highlighted red via selenide
    Then widget popup is not closed

    Examples:
      | name            | type              |
      | DEMO_FILTER_004 | statisticTrend    |
      |                 | launchStatistics  |
      | 1               | investigatedTrend |