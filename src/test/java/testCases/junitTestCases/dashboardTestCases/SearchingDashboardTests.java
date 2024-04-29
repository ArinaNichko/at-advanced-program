package testCases.junitTestCases.dashboardTestCases;

import junit.framework.Assert;
import org.example.pages.DashboardPage;
import org.example.setUp.junit.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class SearchingDashboardTests extends BaseTest {

    @ParameterizedTest
    @ValueSource(strings = {"test", "TEST", "tEST",""})
    public void searchDashboardPositiveResult(String name) {
        DashboardPage dashboardPage = new DashboardPage(driver);

        new DashboardPage(driver)
                .searchByName(name);

        Assert.assertEquals(dashboardPage.getExpectedDashboardsSizeByName(name)
                , dashboardPage.getActualDashboardsSize());
    }

    @ParameterizedTest
    @ValueSource(strings = {"regression"})
    public void searchDashboardNegativeResult(String name) {
        DashboardPage dashboardPage = new DashboardPage(driver);

        new DashboardPage(driver).searchByName(name);

        Assert.assertTrue(dashboardPage.isNoDashboardMessageDisplayed());
    }

}
