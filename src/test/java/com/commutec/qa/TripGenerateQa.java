package com.commutec.qa;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Unit test for TripGenerateQa.
 */
public class TripGenerateQa {

    WebDriver driver;
    String homeUrl = "http://dev.commutec.in/client1/home";

    @BeforeMethod
    public void beforeMethod() throws IOException {
        
        // initialize chrome browser
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-infobars");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        driver.get("http://dev.commutec.in/client1");

        // Do login
        driver.findElement(By.xpath("//div[@class='col-md-6']//input[@id='mobile_no']")).sendKeys("9783839582");
        driver.findElement(By.xpath("//div[@class='col-md-6']//input[@id='password']")).sendKeys("Commuteck123");
        driver.findElement(By.xpath("//button[@id='btnContinue']")).click();

        // verify homepage Url
        Assert.assertEquals(homeUrl, "http://dev.commutec.in/client1/home", "Homepage Url not matched");
    }

    @Test
    public void tripRoute() throws InterruptedException {

        // Select trip&Route and click "Generate Trips"
        driver.findElement(By.xpath("//li/a[@data-toggle='dropdown'][contains(text(),'Trips & Routes')]")).click();

        WebElement trip = driver.findElement(By.xpath("//li/a[contains(text(),'Generate Trips')]"));
        Actions a = new Actions(driver);
        a.moveToElement(trip).click().build().perform();

        driver.findElement(By.xpath(".//input[@id='routeDate']")).click();
        driver.findElement(By.xpath("//td[@data-title='r3c4']")).click();

        Select shift = new Select(driver.findElement(By.xpath("//select[@id='shift_code']")));
        shift.selectByVisibleText("Evening");

        // select pickup
        driver.findElement(By.xpath("//input[@name='optradio'][@value='pickup']")).click();
        driver.findElement(By.xpath("//input[@name='addressRdio'][@value='nodal']")).click();

        // Select Routing Option(Customized Routes)
        driver.findElement(By.xpath(".//input[@data-ng-model='generateRoutes' and @value='manual']")).click();
        driver.findElement(By.xpath("//*[text()='Generate Routes']")).click();
    }

    @AfterMethod
    public void afterMethod() {
        driver.quit();
    }
}
