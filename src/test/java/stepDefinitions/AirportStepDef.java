package stepDefinitions;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import cabsObjectRepo.Airport;
import hooks.Hook;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import objectRepositories.HomePage;
import utilities.DriverSetup;
import utilities.ExcelUtils;
import utilities.ExcelWrite;
import utilities.ScreenshotUtil;

public class AirportStepDef {	
	WebDriver driver;
    HomePage home;
    Airport airport;
    ExcelWrite writer;
    public static Logger log;
    Map<String, Map<String, String>> testData;
    String filePath=".\\src\\test\\resources\\data\\AirportCabsData.xlsx";
    Map<String, String> row;
    @Given("the user loads test data for {string}")
    public void load_test_data(String testCaseId) throws IOException {
    	log=DriverSetup.getLogger();
    	if(testCaseId.equalsIgnoreCase("TC01")) {
    		log.info("***** TC_01-Airport Pickup cabs*****");
    	}
    	else if(testCaseId.equalsIgnoreCase("TC02")) {
    		log.info("***** TC_02-Airport Drop cabs*****");
    	}
        driver = Hook.driver;
        Assert.assertNotNull(driver, "Driver is null");
        home = new HomePage(driver);
        airport = new Airport(driver);
        testData = ExcelUtils.getData(filePath, "AirportCab");
        row = testData.get(testCaseId);
    }
 
    @And("the user clicks on Airport transfer and selects Type")
    public void click_airport_transfer() {
        home.goToCabs();
        
        boolean urlCheck=driver.getCurrentUrl().contains("cabs");
        Assert.assertTrue(urlCheck, "Not navigated to cabs page");
        airport.clickOnAirportTransfer();
        if (row.get("Type").equalsIgnoreCase("Pickup")) {
            airport.clickPickup();
            log.info("clicked on Pickup");
        } else {
            airport.clickDrop();
            log.info("clicked on Drop");
        }
    }
 
    @And("the user enters the source city and selects SourceValue")
    public void enter_source_city() {
    	log.info("entering data...");
        airport.clickSource();
        airport.sendValToSrc(row.get("SourceCity"));
        airport.selectSrcVal(" "+row.get("FullSource"));
    }
 
    @And("the user enters the destination city and selects DestinationValue")
    public void enter_destination_city() {
        airport.clickDestination();
        airport.sendValToDest(row.get("DestinationCity"));
        airport.selectDestVal(" "+row.get("FullDestination"));
    }
 
    @And("the user selects the date and time")
    public void select_date_and_time() {
        airport.clickOnCalendar();
        airport.selectDate(row.get("Date"));
        airport.selectTime(row.get("Time"));
    }
 
    @When("the user clicks the Search button")
    public void click_search_button() {
        	airport.clickSearch();
        String filterOpt = row.get("SearchFilter");
        if (filterOpt != null && !filterOpt.trim().isEmpty()) {
            if (filterOpt.toLowerCase().contains("sedan")) {
                airport.clickOnSedanChk();
            }
        }
    }
 
    @Then("the filtered cab results should be displayed")
    public void display_filtered_cabs() throws IOException {
    	writer = new ExcelWrite(".\\src\\test\\resources\\data\\AirportCabsData.xlsx","Results");
    	System.out.println("Entering into : "+row.get("Results"));
        if (row.get("Results").equalsIgnoreCase("names and prices")) {
           airport.displayNames(writer);
           airport.displayPrices(writer);
           ScreenshotUtil.captureScreenShot(driver, "AirportPickUpTC");
        }
        else {
            airport.displayPrice(writer);
            ScreenshotUtil.captureScreenShot(driver, "AirportDropTC");
        }
        log.info("displayed data");
        writer.save();
    }
}

