package tests;

import configurator.UIBaseClass;
import org.testng.annotations.Test;
import pageObject.RoasterCustomerDetailsPage;
import pageObject.RoasterHomePage;
import pageObject.SFHomePage;
import pageObject.SFLoginPage;
import services.RoasterService;
import utils.dataProvider;
import utils.ymlReaderUtil;

import java.util.Map;

public class LeadCreationFromSFWithAPITest extends UIBaseClass {

    @Test(dataProvider = "leadDataProvider", dataProviderClass = dataProvider.class)
    public void createLead(Map<String, String> data) throws InterruptedException {
        // Read credentials from YAML
        String env = "preProd";
        String credFile = "configuration/credential.yml";
        Map<String, Object> credential = ymlReaderUtil.readCredentials(env, credFile);
        String url = credential.get("url").toString();
        String username = credential.get("username").toString();
        String password = credential.get("password").toString();

        // Launch SF app
        initializeDriver();
        driver.get(url);
        driver.manage().window().maximize();

        // Page objects
        SFLoginPage SFloginPage = new SFLoginPage(driver);
        SFHomePage homePage = new SFHomePage(driver);
        RoasterHomePage roasterHomePage = new RoasterHomePage(driver);
        RoasterCustomerDetailsPage roasterCustomerDetailsPage = new RoasterCustomerDetailsPage(driver);

        // Login
        SFloginPage.enterUsername(username);
        SFloginPage.enterPassword(password);
        SFloginPage.clickLoginButton();

        // Lead creation flow
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

        Thread.sleep(2000); // Required wait for UI stabilization

        homePage.clickSaveButton();

        // Roaster API call
        String userId = homePage.clickRoasterLink();
        driver.quit();
        System.out.println("User ID: " + userId);

        RoasterService roasterService = new RoasterService();
        Map<String, String> SCproData  = roasterService.doCreateProLead(userId);
        System.out.println("SCproUrl: " + SCproData.get("SCpro_url"));

    }
}
