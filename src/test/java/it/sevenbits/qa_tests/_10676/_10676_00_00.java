package it.sevenbits.qa_tests._10676;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

// Проверка подачи объявления неавторизованным пользователем
public class _10676_00_00 extends TestCase {
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
  public void test106760000() throws Exception {
    driver.get(baseUrl + "/add");
    try {
      assertTrue(isElementPresent(By.id("from")));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.id("from")).clear();
    driver.findElement(By.id("from")).sendKeys("Название инвентаря");
    try {
      assertTrue(isElementPresent(By.id("announcementsDescription")));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.id("announcementsDescription")).clear();
    driver.findElement(By.id("announcementsDescription")).sendKeys("Описание инвентаря");
    try {
      assertTrue(isElementPresent(By.id("PriceForHour")));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.id("PriceForHour")).clear();
    driver.findElement(By.id("PriceForHour")).sendKeys("100");
    try {
      assertTrue(isElementPresent(By.id("PriceForDay")));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.id("PriceForDay")).clear();
    driver.findElement(By.id("PriceForDay")).sendKeys("200");
    try {
      assertTrue(isElementPresent(By.id("PriceForWeek")));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.id("PriceForWeek")).clear();
    driver.findElement(By.id("PriceForWeek")).sendKeys("500");
    try {
      assertTrue(isElementPresent(By.cssSelector("input.b-button--default.b-button--blue")));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.cssSelector("input.b-button--default.b-button--blue")).click();
    try {
      assertEquals("Авторизация", driver.getTitle());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    try {
      assertTrue(isElementPresent(By.id("userEmail")));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.id("userEmail")).clear();
    driver.findElement(By.id("userEmail")).sendKeys("meliannaelf@gmail.com");
    try {
      assertTrue(isElementPresent(By.id("passtext")));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.id("passtext")).clear();
    driver.findElement(By.id("passtext")).sendKeys("1");
    try {
      assertTrue(isElementPresent(By.xpath("//input[@value='ВОЙТИ']")));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.xpath("//input[@value='ВОЙТИ']")).click();
    try {
      assertEquals("Название инвентаря", driver.findElement(By.id("from")).getText());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    try {
      assertEquals("Описание инвентаря", driver.findElement(By.id("announcementsDescription")).getText());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    try {
      assertEquals("100", driver.findElement(By.id("PriceForHour")).getText());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    try {
      assertEquals("200", driver.findElement(By.id("PriceForDay")).getText());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    try {
      assertEquals("500", driver.findElement(By.id("PriceForWeek")).getText());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    try {
      assertTrue(isElementPresent(By.cssSelector("input.b-button--default.b-button--blue")));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.cssSelector("input.b-button--default.b-button--blue")).click();
    try {
      assertEquals("Просмотр объявление", driver.getTitle());
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
