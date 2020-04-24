package com.commutec.qa;

import java.io.IOException;
import java.util.List;

import com.commutec.qa.dao.ShuttleDao;
import com.commutec.qa.model.ShuttleData;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * test for Shuttle
 */

public class Shuttle extends LoginLogout {

	String routeUrl = "https://app.commutec.in/demo/shuttle/routes";
	String routeCreationUrl = "https://app.commutec.in/demo/shuttle/routes/create";
	String groupUrl = "https://app.commutec.in/demo/shuttle/employee-group";
	String tripUrl = "https://app.commutec.in/demo/shuttle";
	String filterscreenUrl = "https://app.commutec.in/demo/trips";

	ShuttleDao shuttleDaoObj = new ShuttleDao();
	ShuttleData shuttleObj;

	@BeforeClass
	public void trydata() {
		try {
			shuttleObj = shuttleDaoObj.prepareShuttleRouteData();
		} catch (Exception ex) {
			throw ex;
		}
	}

	public void createShuttle() throws InterruptedException, IOException {

		// check preRoutelist size
		driver.findElement(By.xpath(props.getProperty("shuttle"))).click();
		WebElement click_shuttle = driver.findElement(By.xpath(props.getProperty("shuttle")));

		Actions preRoute = new Actions(driver);
		preRoute.moveToElement(click_shuttle).sendKeys(Keys.DOWN, Keys.DOWN, Keys.DOWN, Keys.DOWN, Keys.ENTER).click()
				.build().perform();
		Thread.sleep(1000);

		Assert.assertEquals(routeUrl, driver.getCurrentUrl(), "Previous routlist page not found");

		try {
			if (driver.findElement(By.xpath(props.getProperty("routeName"))).isDisplayed()) {
				driver.findElement(By.xpath(props.getProperty("deletRoute"))).click();
				Thread.sleep(300);
				driver.findElement(By.xpath(props.getProperty("deleteConfirm"))).click();
			}
		} catch (Exception e) {
		}
		Thread.sleep(500);
		driver.findElement(By.xpath(props.getProperty("shuttle"))).click();
		driver.findElement(By.xpath(props.getProperty("route"))).click();
		driver.findElement(By.xpath(props.getProperty("creation"))).click();
		Thread.sleep(500);

		Assert.assertEquals(driver.getCurrentUrl(), routeCreationUrl, "Route creation page not found");

		driver.findElement(By.xpath(props.getProperty("startRoute"))).sendKeys("goregaon");
		driver.findElement(By.xpath(props.getProperty("endRoute"))).sendKeys("vashi");

		driver.findElement(By.xpath(props.getProperty("pickUp"))).click();

		int n = 2;
		for (int i = 0; i < n; i++) {
			// Add stops
			driver.findElement(By.xpath(props.getProperty("addStops"))).click();
		}
		// click creation, route start and end
		String pathValue = props.getProperty("path");
		String stopValue = props.getProperty("stopName");
		String locationValue = props.getProperty("searchLocation");
		String landmarkValue = props.getProperty("landmark");
		String localityValue = props.getProperty("locality");
		String timeValue = props.getProperty("arrivalTime");

		// No.of rows
		List<WebElement> rows = driver.findElements(By.xpath(props.getProperty("rows")));

		for (int z = 0; z < rows.size(); z++) {

			shuttleObj = shuttleDaoObj.prepareShuttleRouteData();
			rows.get(z).findElement(By.xpath(pathValue + "[" + (z + 1) + "]" + stopValue))
					.sendKeys(shuttleObj.getStopArea());

			rows.get(z).findElement(By.xpath(pathValue + "[" + (z + 1) + "]" + locationValue))
					.sendKeys(shuttleObj.getLocationArea());

			WebElement loc = rows.get(z).findElement(By.xpath(pathValue + "[" + (z + 1) + "]" + locationValue));

			Thread.sleep(1000);
			// Select 2nd row of location from dropdown
			Actions a = new Actions(driver);
			a.moveToElement(loc).sendKeys(Keys.DOWN).build().perform();
			Thread.sleep(300);
			a.sendKeys(Keys.DOWN).build().perform();
			Thread.sleep(300);
			a.sendKeys(Keys.ENTER).build().perform();
			Thread.sleep(300);

			rows.get(z).findElement(By.xpath(pathValue + "[" + (z + 1) + "]" + landmarkValue))
					.sendKeys(shuttleObj.getLandmarkArea());
			rows.get(z).findElement(By.xpath(pathValue + "[" + (z + 1) + "]" + localityValue))
					.sendKeys(shuttleObj.getLocalityArea());

			rows.get(z).findElement(By.xpath(pathValue + "[" + (z + 1) + "]" + timeValue)).click();
			rows.get(z).findElement(By.xpath(pathValue + "[" + (z + 1) + "]" + timeValue)).click();
		}

		driver.findElement(By.xpath(props.getProperty("save"))).click();
		Thread.sleep(1000);
		Assert.assertEquals(driver.getCurrentUrl(), routeUrl, "Routelist page not found");
	}

	public void manageGroups() throws InterruptedException, IOException {
		// click shuttle,
		driver.findElement(By.xpath(props.getProperty("shuttle"))).click();
		WebElement click_shuttle = driver.findElement(By.xpath(props.getProperty("shuttle")));
		WebElement groupeManage = driver.findElement(By.xpath(props.getProperty("manageGroups")));

		Actions shuttleGen = new Actions(driver);
		shuttleGen.moveToElement(click_shuttle).moveToElement(groupeManage).click().build().perform();
		Thread.sleep(1000);
		Assert.assertEquals(driver.getCurrentUrl(), groupUrl, "Employee group page not found");
		try {
			if (driver.findElement(By.xpath(props.getProperty("searchName"))).isDisplayed()) {
				driver.findElement(By.xpath(props.getProperty("deletePopup"))).click();
				Thread.sleep(300);
				driver.findElement(By.xpath(props.getProperty("deletedName"))).click();
				// Assert.assertFalse(driver.findElement(By.xpath(props.getProperty("searchName"))).isDisplayed(),
				// "Testdata group properly not deleted");
			}
		} catch (Exception e) {

		}
		Thread.sleep(1000);
		driver.findElement(By.xpath(props.getProperty("groupName"))).sendKeys("Testdata");
		Thread.sleep(200);
		driver.findElement(By.xpath(props.getProperty("clickSelect"))).click();
		Thread.sleep(100);
		List<WebElement> routeSelect = driver.findElements(By.xpath(props.getProperty("selectSearch")));

		for (int j = 0; j < routeSelect.size();) {

			routeSelect.get(routeSelect.size() - 1).click();
			break;
		}
		driver.findElement(By.xpath(props.getProperty("AddGroup"))).click();
		Thread.sleep(2000);
		// Assert.assertTrue(driver.findElement(By.xpath(props.getProperty("searchName"))).isDisplayed(),"Testdata
		// group added properly");
		// Find no.of rows manage groups page
		List<WebElement> group = driver.findElements(By.xpath(props.getProperty("groupsRows")));
		for (int k = 0; k < group.size();) {

			group.get(k).findElement(By.xpath(props.getProperty("firstPlusicon"))).click();
			Thread.sleep(500);
			for (String firstchild : driver.getWindowHandles()) {
				driver.switchTo().window(firstchild);
				Thread.sleep(1000);

				driver.findElement(By.xpath(props.getProperty("thirdEmployee"))).click();
				Thread.sleep(300);
				driver.findElement(By.xpath(props.getProperty("fourthEmployee"))).click();
				Thread.sleep(300);
				driver.findElement(By.xpath(props.getProperty("selectedEmployeeTab"))).click();
			}
			Thread.sleep(1000);
			group = driver.findElements(By.xpath(props.getProperty("groupsRows")));
			group.get(k).findElement(By.xpath(props.getProperty("secondPlusicon"))).click();
			Thread.sleep(500);
			for (String secondchild : driver.getWindowHandles()) {

				driver.switchTo().window(secondchild);
				driver.findElement(By.xpath(props.getProperty("firstemployee"))).click();
				Thread.sleep(300);
				driver.findElement(By.xpath(props.getProperty("sixthEmployee"))).click();
				Thread.sleep(300);
				driver.findElement(By.xpath(props.getProperty("selectedEmployeeTab"))).click();
				Thread.sleep(200);
			}
			driver.switchTo().defaultContent();
			break;

		}

	}

	public void generateTrips() throws InterruptedException, IOException {
		WebElement click_shuttle = driver.findElement(By.xpath(props.getProperty("shuttle")));
		// click trip generate
		WebElement trip = driver.findElement(By.xpath(props.getProperty("tripGenerate")));
		Actions shuttleGen = new Actions(driver);
		shuttleGen.moveToElement(click_shuttle).moveToElement(trip).click().build().perform();
		Thread.sleep(500);
		driver.findElement(By.xpath(props.getProperty("dateRange"))).click();
		driver.findElement(By.xpath(props.getProperty("customRange"))).click();
		driver.findElement(By.xpath(props.getProperty("nextIcon"))).click();
		Thread.sleep(300);

		// select first date(2nd row 2nd column) and second date(2nd row 3rd column)
		driver.findElement(By.xpath(props.getProperty("first_date"))).click();
		driver.findElement(By.xpath(props.getProperty("second_date"))).click();
		driver.findElement(By.xpath(props.getProperty("applyTab"))).click();

		// Add route
		driver.findElement(By.xpath(props.getProperty("addRoute"))).click();
		driver.findElement(By.xpath(props.getProperty("routeSelect"))).click();
		WebElement route_search = driver.findElement(By.xpath(props.getProperty("searchRoute")));
		WebElement select_route = driver.findElement(By.xpath(props.getProperty("select_Route")));
		Thread.sleep(300);
		Actions routeSelect = new Actions(driver);
		routeSelect.moveToElement(route_search).moveToElement(select_route).click().build().perform();
		Thread.sleep(300);

		// time slot
		driver.findElement(By.xpath(props.getProperty("timeSlot"))).click();
		WebElement timeslot = driver.findElement(By.xpath(props.getProperty("timeSlot")));
		Actions time = new Actions(driver);
		time.moveToElement(timeslot).sendKeys(Keys.DOWN, Keys.ENTER).build().perform();

		// vehicle select
		Select vehicle = new Select(driver.findElement(By.xpath(props.getProperty("vehicleSelect"))));
		vehicle.selectByIndex(2);
		driver.findElement(By.xpath(props.getProperty("tripType"))).click();
		// select vendor
		driver.findElement(By.xpath(props.getProperty("vendor"))).click();
		WebElement vendorSelect = driver.findElement(By.xpath(props.getProperty("vendor")));
		Actions vendor = new Actions(driver);
		vendor.moveToElement(vendorSelect).sendKeys(Keys.DOWN, Keys.ENTER).build().perform();

		// select employee group
		driver.findElement(By.xpath(props.getProperty("empGroup"))).click();
		WebElement group_search = driver.findElement(By.xpath(props.getProperty("searchGroup")));
		WebElement select_Group = driver.findElement(By.xpath(props.getProperty("selectRoute")));
		Actions groupSelect = new Actions(driver);
		groupSelect.moveToElement(group_search).moveToElement(select_Group).click().build().perform();
		driver.findElement(By.xpath(props.getProperty("generateTab"))).click();
		Thread.sleep(1000);
		Assert.assertEquals(driver.getCurrentUrl(), filterscreenUrl, "Filter trips page not found");
		Thread.sleep(2000);
		driver.findElement(By.xpath(props.getProperty("downIcon"))).click();
		Thread.sleep(500);
		driver.findElement(By.xpath(props.getProperty("dateRange"))).click();
		driver.findElement(By.xpath(props.getProperty("customRange"))).click();
		driver.findElement(By.xpath(props.getProperty("nextIcon"))).click();
		Thread.sleep(300);

		// select first date(2nd row 2nd column) and second date(2nd row 3rd column)
		driver.findElement(By.xpath(props.getProperty("first_date"))).click();
		driver.findElement(By.xpath(props.getProperty("second_date"))).click();
		driver.findElement(By.xpath(props.getProperty("applyTab"))).click();
		Thread.sleep(500);
	}

	@Test
	public void shuttleflow() throws InterruptedException, IOException {
		this.createShuttle();
		this.manageGroups();
		Thread.sleep(1000);
		this.generateTrips();

	}

}