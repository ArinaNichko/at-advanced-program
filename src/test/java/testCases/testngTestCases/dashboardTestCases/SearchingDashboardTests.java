package testCases.testngTestCases.dashboardTestCases;

import junit.framework.Assert;
import org.example.pages.DashboardPage;
import org.example.setUp.testng.BaseTest;
import org.example.utils.DataUtil;
import org.testng.annotations.Test;

public class SearchingDashboardTests extends BaseTest {

    @Test(dataProviderClass = DataUtil.class, dataProvider = "dashboard")
    public void searchDashboardPositiveResult(String name) {
        DashboardPage dashboardPage = new DashboardPage(driver);

        new DashboardPage(driver).searchByName(name);

        Assert.assertEquals(dashboardPage.getExpectedDashboardsSizeByName(name)
                , dashboardPage.getActualDashboardsSize());
    }

    @Test(dataProviderClass = DataUtil.class, dataProvider = "dashboard")
    public void searchDashboardNegativeResult(String name) {
        DashboardPage dashboardPage = new DashboardPage(driver);

        new DashboardPage(driver).searchByName(name);

        Assert.assertTrue(dashboardPage.isNoDashboardMessageDisplayed());
    }
}
