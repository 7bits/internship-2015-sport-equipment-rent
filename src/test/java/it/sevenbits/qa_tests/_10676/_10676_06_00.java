package it.sevenbits.qa_tests._10676;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

// Проверка полей "Цена..." на пустой ввод на тсранице подачи объявления
public class _10676_06_00 extends TestCase{
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
  public void test106760600() throws Exception {
    driver.get(baseUrl + "/login");
    driver.findElement(By.id("userEmail")).clear();
    driver.findElement(By.id("userEmail")).sendKeys("meliannaelf@gmail.com");
    driver.findElement(By.id("passtext")).clear();
    driver.findElement(By.id("passtext")).sendKeys("1");
    driver.findElement(By.cssSelector("input.b-button--default.b-button--blue")).click();
    try {
      assertTrue(isElementPresent(By.cssSelector("div.b-header__give-announcement > a.header-a-color > b > p")));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.cssSelector("div.b-header__give-announcement > a.header-a-color > b > p")).click();
    try {
      assertEquals("Добавить объявление", driver.getTitle());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.id("from")).clear();
    driver.findElement(By.id("from")).sendKeys("Название инвентаря");
    driver.findElement(By.id("announcementsDescription")).clear();
    driver.findElement(By.id("announcementsDescription")).sendKeys("какое-то описание some text with description");
    driver.findElement(By.id("PriceForHour")).clear();
    driver.findElement(By.id("PriceForHour")).sendKeys("");
    driver.findElement(By.id("PriceForDay")).clear();
    driver.findElement(By.id("PriceForDay")).sendKeys("");
    driver.findElement(By.id("PriceForWeek")).clear();
    driver.findElement(By.id("PriceForWeek")).sendKeys("");
    driver.findElement(By.cssSelector("input.b-button--default.b-button--yellow")).click();
    try {
      assertEquals("Добавить объявление", driver.getTitle());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    // Warning: verifyTextPresent may require manual changes
      try {
          assertEquals("Поле Цена за час не может быть пустым", driver.findElement(By.cssSelector("p")).getText());
      } catch (Error e) {
          verificationErrors.append(e.toString());
      }
    try {
      assertEquals("Поле Цена за сутки не может быть пустым", driver.findElement(By.cssSelector("p")).getText());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    try {
      assertEquals("Поле Цена за неделю не может быть пустым", driver.findElement(By.cssSelector("p")).getText());
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
