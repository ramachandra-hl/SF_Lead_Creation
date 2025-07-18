package tests;

import configurator.Base;
import org.testng.annotations.Test;
import pageObject.HomePage;
import pageObject.LoginPage;
import testDataProvider.TestDataProvider;
import java.util.Map;

public class LeadCreationTest extends Base {

    @Test(dataProvider = "leadDataProvider", dataProviderClass = TestDataProvider.class )
    public void createLead(Map<String, String> data) {
        initializeDriver();
        driver.get("https://design-cafe--newint.sandbox.my.salesforce.com/");
        driver.manage().window().maximize();

        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);

        loginPage.enterUsername(data.get("username"));
        loginPage.enterPassword(data.get("password"));
        loginPage.clickLoginButton();

        homePage.clickLeadsTab();
        homePage.clickLeadsTabDropdown();
        homePage.clickDCSalesManagerRadioButton();
        homePage.clickNextButton();
        homePage.clickMeetingTypeDropdown();
        homePage.selectDropdownOptionByText(data.get("meetingType"));
        homePage.clickMeetingVenueDropdown();
        homePage.selectDropdownOptionByText(data.get("venue"));
        homePage.clickApproxBudgetDropdown();
        homePage.selectDropdownOptionByText(data.get("budget"));
        homePage.enterMeetingDate();
        homePage.clickDesignUserDropdown(data.get("designerName"));
        homePage.enterCity(data.get("city"));
        homePage.enterLastName(data.get("lastName"));
        homePage.enterEmail(data.get("email"));
        homePage.enterMobilePhone(data.get("phone"));
        homePage.clickViewAllDependenciesButton();
        homePage.clickChannelDropdown();
        homePage.clickSource();
        homePage.clickCampaignSource();
        homePage.clickLeadSource();
        homePage.clickApplyButton();
        homePage.clickSaveButton();
    }


}

