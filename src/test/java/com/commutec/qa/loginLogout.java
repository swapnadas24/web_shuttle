package com.commutec.qa;

import java.io.IOException;
import java.util.Properties;
import com.commutec.qa.model.ShuttleData;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

public class LoginLogout {

    public WebDriver driver;
    public Properties props;

    String homeUrl = "https://app.commutec.in/demo/home";

    @BeforeTest
    public void lunch() throws IOException, InterruptedException {

        ChromeOptions options = new ChromeOptions();
        // options.setHeadless(true);
        //options.addArguments("--window-size=1440x900");
        options.addArguments("disable-infobars"); 
        driver = new ChromeDriver(options);

        // Load the properties File
        props = new Properties();
        props.load(ShuttleData.class.getClassLoader().getResourceAsStream("application.properties"));
        driver.manage().deleteAllCookies();
        // Get Url
        driver.get(props.getProperty("url"));
        driver.manage().window().maximize();
        Thread.sleep(500);
    }
    @BeforeMethod
    public void login() throws InterruptedException {
        // Do login
        driver.findElement(By.xpath(props.getProperty("uname"))).sendKeys("show");
        Thread.sleep(500);
        driver.findElement(By.xpath(props.getProperty("password"))).sendKeys("showme");
        Thread.sleep(500);
        driver.findElement(By.xpath(props.getProperty("loginTab"))).click();
        Thread.sleep(1000);
        Assert.assertEquals(driver.getCurrentUrl(), homeUrl, "Home page not found");
    }

    @AfterMethod
    public void logout() {

        WebElement profile = driver.findElement(By.xpath(props.getProperty("logout_profile")));
        WebElement logout = driver.findElement(By.xpath(props.getProperty("logoutTab")));

        Actions log = new Actions(driver);
        log.moveToElement(profile).moveToElement(logout).click().build().perform();
    }
    @AfterTest
    public void closeSite() {

        driver.quit();
    }
}