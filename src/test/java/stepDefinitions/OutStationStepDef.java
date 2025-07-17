package stepDefinitions;

import java.io.IOException;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.google.gson.JsonObject;

import cabsObjectRepo.Outstation;
import hooks.Hook;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import objectRepositories.HomePage;
import utilities.DriverSetup;
import utilities.JsonDataReader;
import utilities.ScreenshotUtil;

public class OutStationStepDef {
	
	static WebDriver driver;
	static DriverSetup setUp;
	static Outstation os;
	static HomePage home;
	public static Logger log;
	String jsonfile="OutstationCabsData.json";
	JsonObject cabData= JsonDataReader.getCabData("OneWayTrip",jsonfile);
	JsonObject cabDataRT=JsonDataReader.getCabData("RoundTrip",jsonfile);
	@Given("The user go to Cabs and select Outstation OneWay Trip")
	public void the_user_go_to_cabs_and_select_outstation_one_way_trip() {
		log=DriverSetup.getLogger();
		driver=Hook.driver;
		Assert.assertNotNull(driver, "Driver is null");
		log.info("***** TC_03-Outstation OneWay Trip cabs*****");
		home=new HomePage(driver);
		os=new Outstation(driver);
		home.goToCabs();
		boolean urlCheck=driver.getCurrentUrl().contains("cabs");
        Assert.assertTrue(urlCheck, "Not navigated to cabs page");
		os.switchToOutstation();
	}

	@When("The user select from city and to city")
	public void user_select_from_city_and_to_city_as() {
		log.info("entering data...");
		
		String fullsource=cabData.get("FromCity").getAsString();
		String fullDest=cabData.get("ToCity").getAsString();
		
	    os.fromField(fullsource);
	    os.toField(fullDest);
	}

	@When("The user selects Date")
	public void the_user_select_date() {
	    // Write code here that turns the phrase above into concrete actions
	    os.pickupDate(cabData.get("PickupDate").getAsString());
	}

	@When("The user choose Time")
	public void the_user_choose_timing_as() {
	    // Write code here that turns the phrase above into concrete actions
	    os.pickupTime(cabData.get("PickupTime").getAsString());
	}

	@Then("The user click Search")
	public void the_user_click_search() {
	    // Write code here that turns the phrase above into concrete actions
	    os.search();
	    log.info("entered data and clicked on search");
	}

	@Then("The user apply SUV filter and printing results based on more options")
	public void the_user_apply_suv_filter_and_printing_results_based_on_more_options() throws IOException {
	    // Write code here that turns the phrase above into concrete actions
	    os.results("OneWay Trip Results");
	    log.info("displayed data");
	    ScreenshotUtil.captureScreenShot(driver, "OutStation_OneWayTC");
	    log.info("Outstation OneWay Trip TC completed");
	}

	@Given("The user go to Cabs and select Outstation Round Trip")
	public void the_user_go_to_cabs_and_select_outstation_round_trip() {
	    // Write code here that turns the phrase above into concrete actions
	    home.goToCabs();
	    log.info("***** TC_04-Outstation Round Trip cabs*****");
	    os.switchToOutstation();
	}

	@When("user select From city and to city")
	public void the_user_select_from_city_and_to_city_as() {
	    // Write code here that turns the phrase above into concrete actions
		log.info("entering data...");
		
		String fullsource=cabDataRT.get("FromCityR").getAsString();
		String fullDest=cabDataRT.get("ToCityR").getAsString();
		
	    os.fromField(fullsource);
	    os.toField(fullDest);
	}

	@When("The user selects pickUp date")
	public void the_user_select_pick_up_date_as() {
	    // Write code here that turns the phrase above into concrete actions
	    os.pickupDate(cabDataRT.get("PickupDate").getAsString());
	}

	@When("The user choose pick up time")
	public void the_user_choose_pick_up_time() {
	    // Write code here that turns the phrase above into concrete actions
	    os.pickupTime(cabDataRT.get("PickupTime").getAsString());
	}

	@When("The user selects return date")
	public void the_user_select_return_date() {
	    // Write code here that turns the phrase above into concrete actions
	    os.returnDate(cabDataRT.get("ReturnDate").getAsString());
	}

	@When("The user choose return time")
	public void the_user_choose_return_time() {
	    // Write code here that turns the phrase above into concrete actions
	    os.returnTime(cabDataRT.get("ReturnTime").getAsString());
	}

	@Then("user click Search")
	public void user_click_search() {
	    // Write code here that turns the phrase above into concrete actions
	    os.search();
	    log.info("entered data and clicked on search");
	}
	
	@Then("The user apply Filter to fetch data")
	public void the_user_apply_filter_to_fetch_data() throws IOException {
	    // Write code here that turns the phrase above into concrete actions
	    os.results("Round Trip Results");
	    log.info("displayed data");
	    ScreenshotUtil.captureScreenShot(driver, "OutStation_RoundTripTC");
	    log.info("Outstation Round Trip TC completed");
	    
	}
}
