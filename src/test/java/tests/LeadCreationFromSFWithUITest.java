package tests;

import configurator.UIBaseClass;
import org.testng.annotations.Test;
import pageObject.RoasterCustomerDetailsPage;
import pageObject.RoasterHomePage;
import pageObject.SFHomePage;
import pageObject.SFLoginPage;
import utils.dataProvider;
import utils.ymlReaderUtil;

import java.util.Map;

import static utils.PropertiesReader.*;

public class LeadCreationFromSFWithUITest extends UIBaseClass {

    @Test(dataProvider = "leadDataProvider", dataProviderClass = dataProvider.class)
    public void createRoasterLead(Map<String, String> data) throws InterruptedException {
        // Environment Setup
        String env = "preProd";
        String credFile = "configuration/credential.yml";
        Map<String, Object> credential = ymlReaderUtil.readCredentials(env, credFile);

        String url = credential.get("url").toString();
        String username = credential.get("username").toString();
        String password = credential.get("password").toString();

        // Browser Initialization
        initializeDriver();
        driver.get(url);
        driver.manage().window().maximize();

        // Page Object Initialization
        SFLoginPage loginPage = new SFLoginPage(driver);
        SFHomePage sfHomePage = new SFHomePage(driver);
        RoasterHomePage roasterHomePage = new RoasterHomePage(driver);
        RoasterCustomerDetailsPage customerDetailsPage = new RoasterCustomerDetailsPage(driver);

        // Login
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();

        // Lead Form Filling
        sfHomePage.clickLeadsTab();
        sfHomePage.clickLeadsTabDropdown();
        sfHomePage.clickDCSalesManagerRadioButton();
        sfHomePage.clickNextButton();

        sfHomePage.clickMeetingTypeDropdown();
        sfHomePage.selectDropdownOptionByText(data.get("meetingType"));
        sfHomePage.clickMeetingVenueDropdown();
        sfHomePage.selectDropdownOptionByText(data.get("venue"));
        sfHomePage.clickApproxBudgetDropdown();
        sfHomePage.selectDropdownOptionByText(data.get("budget"));
        sfHomePage.enterMeetingDate();
        sfHomePage.clickDesignUserDropdown(data.get("designerName"));
        sfHomePage.enterCity(data.get("city"));
        sfHomePage.enterLastName(data.get("lastName"));
        sfHomePage.enterEmail(data.get("email"));
        sfHomePage.enterMobilePhone(data.get("phone"));

        sfHomePage.clickViewAllDependenciesButton();
        sfHomePage.clickChannelDropdown();
        sfHomePage.clickSource();
        sfHomePage.clickCampaignSource();
        sfHomePage.clickLeadSource();
        sfHomePage.clickApplyButton();

        Thread.sleep(2000);
        sfHomePage.clickSaveButton();

        // Switch to Roaster
        String userId = sfHomePage.clickRoasterLink();
        Thread.sleep(5000);
        sfHomePage.switchToWindowByTitleOrUrl("DesignCafe");

        // Login to Roaster
        roasterHomePage.enterEmail(dp_email);
        roasterHomePage.enterPassword(dp_password);
        roasterHomePage.clickSignInButton();

        // Launch SpaceCraft Flow
        customerDetailsPage.clickLauchButton();
        customerDetailsPage.selectBHKOption();
        customerDetailsPage.enterFloorArea("100");
        customerDetailsPage.selectDate();
        customerDetailsPage.enterClientBudget("500000");
        customerDetailsPage.selectPossessionOfTheSite();
        customerDetailsPage.selectFloorPlan();
        customerDetailsPage.clickNextButton();

        customerDetailsPage.enterPropertyDetails("Test yantra, South End Circle, Jaya Nagar 1st Block, Vijayarangam Layout, Jayanagar, Bengaluru, Karnataka, India");
        customerDetailsPage.enterFlatHouseNo("1");
        customerDetailsPage.selectExperienceCentre("MGDC");
        customerDetailsPage.clickNextButton();

        Thread.sleep(10000);
        customerDetailsPage.clickNextButton();

        Thread.sleep(5000);
        customerDetailsPage.clickSendOtpButton();
        customerDetailsPage.enterOtp("000111");
        customerDetailsPage.clickLaunchSpaceCraftButton();
    }
}
