package testCases.junitTestCases.dashboardTestCases;

import junit.framework.Assert;
import org.example.pages.CreatedDashboardPage;
import org.example.pages.DashboardPage;
import org.example.setUp.junit.BaseTest;
import org.example.utils.DashboardArgumentsProvider;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvFileSource;

public class CreationDashboardTests extends BaseTest {
    private final String DASHBOARD_TEST_DATA = "/testData/createDashboardPositiveResult.csv";

    @ParameterizedTest
    @ArgumentsSource(DashboardArgumentsProvider.class)
    public void createDashboardPositiveResult(String name, String description) {
        CreatedDashboardPage dashboardPage = new CreatedDashboardPage(driver);

        new DashboardPage(driver)
                .clickAddNewDashboardButton()
                .enterDashboardName(name)
                .enterDashboardDescription(description)
                .clickAddButton();

        Assert.assertEquals(name, dashboardPage.getBreadcrumbTitle());
    }

    @ParameterizedTest
    @CsvFileSource(resources = DASHBOARD_TEST_DATA, numLinesToSkip = 1)
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
