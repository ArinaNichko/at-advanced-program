package runner;


import testCases.junitTestCases.dashboardTestCases.EditingDashboardTests;
import testCases.junitTestCases.dashboardTestCases.SearchingDashboardTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({SearchingDashboardTests.class, EditingDashboardTests.class})
//@SelectPackages("dashboardTestCases")
public class DashboardRunner {
}
