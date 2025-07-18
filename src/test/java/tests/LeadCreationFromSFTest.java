package tests;

import configurator.Base;
import org.testng.annotations.Test;
import pageObject.HomePage;
import pageObject.LoginPage;
import utils.dataProvider;
import utils.ymlReaderUtil;

import java.util.Map;

public class LeadCreationFromSFTest extends Base {

    @Test(dataProvider = "leadDataProvider", dataProviderClass = dataProvider.class)
    public void createLead(Map<String, String> data) throws InterruptedException {
        String env = "preProd";
        String credFile = "configuration/credential.yml";

        Map<String, Object> credential = ymlReaderUtil.readCredentials(env, credFile);
        String url = credential.get("url").toString();
        String username = credential.get("username").toString();
        String password = credential.get("password").toString();
        initializeDriver();
        driver.get(url);
        driver.manage().window().maximize();
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
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
        Thread.sleep(2000);
        homePage.clickSaveButton();
    }
}