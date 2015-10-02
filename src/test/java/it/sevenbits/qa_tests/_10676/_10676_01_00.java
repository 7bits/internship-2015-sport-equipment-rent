package it.sevenbits.qa_tests._10676;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

// Проверка на работоспособность ссылки "Дать объявление" для авторизованного пользователя на главной странице
public class _10676_01_00 extends TestCase{
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    //driver = new FirefoxDriver();
    System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
    driver = new ChromeDriver();
    baseUrl = "http://localhost:9000/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void test106760100() throws Exception {
    driver.get(baseUrl + "/");
    try {
      assertTrue(isElementPresent(By.xpath("//div[3]/a/b/p")));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    try {
      assertEquals("ДАТЬ ОБЪЯВЛЕНИЕ", driver.findElement(By.xpath("//div[3]/a/b/p")).getText());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.xpath("//div[3]/a/b/p")).click();
    try {
      assertEquals("Добавить объявление", driver.getTitle());
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
