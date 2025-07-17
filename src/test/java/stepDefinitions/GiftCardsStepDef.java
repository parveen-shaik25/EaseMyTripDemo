package stepDefinitions;

import java.io.IOException;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import hooks.Hook;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import objectRepositories.GiftCards;
import objectRepositories.HomePage;
import utilities.DriverSetup;
import utilities.ExcelUtils;
import utilities.ExcelWrite;

public class GiftCardsStepDef {
	
	static WebDriver driver;
	static DriverSetup setUp;
	static GiftCards gift;
	static HomePage home;
	public static Logger log;
	ExcelWrite writer;
	Map<String, Map<String,String>> testData;
	String filePath=".\\src\\test\\resources\\data\\GiftCardData.xlsx";
	Map<String,String> row;
	@Given("the user clicks on More tab and selects GiftCard to test {string}")
	public void the_user_clicks_on_more_tab_and_selects_gift_card(String testCaseId) throws IOException {
	    // Write code here that turns the phrase above into concrete actions
		log=DriverSetup.getLogger();
		log.info("***** TC_09-validating email field in GiftCards Section*****");
		log.info("***** TC_10-validating mobile number field in GiftCards Section*****");
		driver=Hook.driver;
		Assert.assertNotNull(driver, "Driver is null");
	    home=new HomePage(driver);
	    gift=new GiftCards(driver);
	    home.gotoHome();
	    gift.navigateToCards();
	    boolean urlCheck=driver.getCurrentUrl().contains("giftcard");
        Assert.assertTrue(urlCheck, "Not navigated to Gift Cards page");
        testData=ExcelUtils.getData(filePath,"FormInfo");
	    row=testData.get(testCaseId.trim());
	}

	@Given("scroll to festival and clicks festival then selects Diwali GiftCard")
	public void scroll_to_festival_and_clicks_festival_then_selects_diwali_gift_card() {
	    // Write code here that turns the phrase above into concrete actions
	    gift.selectingCard();
	}

	@Given("filling the necessary data in the form which is in excel file")
	public void filling_the_necessary_data_in_the_form() {
	    // Write code here that turns the phrase above into concrete actions
		log.info("entering data...");
		gift.fillForm(row.get("Denomination Amount".trim()),row.get("Quantity").trim(),row.get("Sender Name").trim());
	}

	@When("the user enters Email and MobileNumber then clicks Accept check box")
	public void the_user_enters_email_and_mobile_number_then_clicks_accept_chkbox() throws IOException {
	    // Write code here that turns the phrase above into concrete actions
		log.info("validating fields...");
		gift.validateEmail(row.get("Email"));
	    gift.validateMobileNum(row.get("Mobile No"));
	}

	@Then("it displays error message and captures screenshot")
	public void it_displays_error_message_and_captures_screenshot() throws Exception {
	    // Write code here that turns the phrase above into concrete actions
		writer=new ExcelWrite(filePath, "FormInfo");
		writer.setCellValue(0, 6, "status");
		log.info("generating Screenshots");
	    gift.screenShots(writer);
	    log.info("Giftcards TC completed");
	}
}
