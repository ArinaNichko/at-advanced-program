package testCases.testngTestCases.dashboardTestCases;

import junit.framework.Assert;
import org.example.pages.DashboardPage;
import org.example.pages.LoginPage;
import org.example.setUp.testng.BaseTest;
import org.example.utils.DataUtil;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class EditingDashboardTests extends BaseTest {

//    @BeforeMethod
//    public void login() {
//        new LoginPage(driver)
//                .goToLoginPage()
//                .enterLogin("Arina_Nichko")
//                .enterPassword("qsdty19467!")
//                .clickLogin();
//    }

    @Test(dataProviderClass = DataUtil.class, dataProvider = "dashboard")
    public void editDashboardPositiveResult(String initialName, String newName, String description) {
        DashboardPage dashboardPage = new DashboardPage(driver);

        new DashboardPage(driver)
                .clickDashboardEditIcon(initialName)
                .enterDashboardName(newName)
                .enterDashboardDescription(description)
                .clickUpdateButton();

        Assert.assertTrue(dashboardPage.isDashboardDisplayed(newName));
    }


    @Test(dataProviderClass = DataUtil.class, dataProvider = "dashboard")
    public void editDashboardNegativeResult(String initialName, String newName, String description) {
        DashboardPage dashboardPage = new DashboardPage(driver);

        new DashboardPage(driver)
                .clickDashboardEditIcon(initialName)
                .enterDashboardName(newName)
                .enterDashboardDescription(description)
                .clickUpdateButton();

        Assert.assertTrue(dashboardPage.isDashboardNameHighlightedRed());
        Assert.assertTrue(dashboardPage.isAddDashboardWindowOpened());
    }
}
