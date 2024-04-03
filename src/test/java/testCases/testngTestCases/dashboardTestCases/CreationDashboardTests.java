package testCases.testngTestCases.dashboardTestCases;

import junit.framework.Assert;
import org.example.pages.CreatedDashboardPage;
import org.example.pages.DashboardPage;
import org.example.pages.LoginPage;
import org.example.setUp.testng.BaseTest;
import org.example.utils.DashboardArgumentsProvider;
import org.example.utils.DataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CreationDashboardTests extends BaseTest {

//    @BeforeMethod
//    public void login() {
//        new LoginPage(driver)
//                .goToLoginPage()
//                .enterLogin("Arina_Nichko")
//                .enterPassword("qsdty19467!")
//                .clickLogin();
//    }

    @Test(dataProviderClass = DataUtil.class, dataProvider = "dashboard")
    public void createDashboardPositiveResult(String name, String description) {
        CreatedDashboardPage dashboardPage = new CreatedDashboardPage(driver);

        new DashboardPage(driver)
                .clickAddNewDashboardButton()
                .enterDashboardName(name)
                .enterDashboardDescription(description)
                .clickAddButton();

        Assert.assertEquals(name, dashboardPage.getBreadcrumbTitle());
    }


    @Test(dataProviderClass = DataUtil.class, dataProvider = "dashboard")
    public void createDashboardNegativeResult(String name, String description) {
        DashboardPage dashboardPage = new DashboardPage(driver);
        new DashboardPage(driver)
                .clickAddNewDashboardButton()
                .enterDashboardName(name)
                .enterDashboardDescription(description)
                .clickAddButton();

        Assert.assertTrue(dashboardPage.isDashboardNameHighlightedRed());
        Assert.assertTrue(dashboardPage.isAddDashboardWindowOpened());
    }
}
