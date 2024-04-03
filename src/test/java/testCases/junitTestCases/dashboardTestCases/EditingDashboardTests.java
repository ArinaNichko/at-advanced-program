package testCases.junitTestCases.dashboardTestCases;

import junit.framework.Assert;
import org.example.pages.DashboardPage;
import org.example.pages.LoginPage;
import org.example.setUp.junit.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class EditingDashboardTests extends BaseTest {
    private final String DASHBOARD_TEST_DATA = "/testData/editDashboardPositiveResult.csv";
    private final String DASHBOARD_NEGATIVE_TEST_DATA = "/testData/editDashboardNegativeResult.csv";

    @BeforeEach
    public void login() {
        new LoginPage(driver)
                .goToLoginPage()
                .enterLogin("Arina_Nichko")
                .enterPassword("qsdty19467!")
                .clickLogin();
    }

    @ParameterizedTest
    @CsvFileSource(resources = DASHBOARD_TEST_DATA, numLinesToSkip = 1)
    public void editDashboardPositiveResult(String initialName, String newName, String description) {
        DashboardPage dashboardPage = new DashboardPage(driver);

        new DashboardPage(driver)
                .clickDashboardEditIcon(initialName)
                .enterDashboardName(newName)
                .enterDashboardDescription(description)
                .clickUpdateButton();

        Assert.assertTrue(dashboardPage.isDashboardDisplayed(newName));
    }


    @ParameterizedTest
    @CsvFileSource(resources = DASHBOARD_NEGATIVE_TEST_DATA, numLinesToSkip = 1)
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
