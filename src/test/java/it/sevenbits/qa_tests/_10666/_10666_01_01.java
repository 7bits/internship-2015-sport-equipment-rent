package it.sevenbits.qa_tests._10666;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

// Проверка на ввод имени длиной >50 символов
public class _10666_01_01 extends TestCase {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    System.setProperty("webdriver.chrome.driver", "/home/marina/chromedriver");
    driver = new ChromeDriver();
    baseUrl = "http://localhost:9000/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void test106660101() throws Exception {
    driver.get(baseUrl + "registration");
    driver.findElement(By.id("name")).clear();
    driver.findElement(By.id("name")).sendKeys("testtesttesttesttesttesttesttesttesttesttesttesttest");
    driver.findElement(By.id("userEmail")).clear();
    driver.findElement(By.id("userEmail")).sendKeys("me@test.com");
    driver.findElement(By.id("passtext")).clear();
    driver.findElement(By.id("passtext")).sendKeys("123");
    driver.findElement(By.cssSelector("input.b-button--default.b-button--blue")).click();
    try {
      assertEquals("Поле имя должно быть короче 50 символов", driver.findElement(By.cssSelector("p")).getText());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    try {
      assertEquals("Авторизация", driver.getTitle());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
