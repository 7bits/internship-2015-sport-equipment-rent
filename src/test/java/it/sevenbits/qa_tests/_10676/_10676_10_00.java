package it.sevenbits.qa_tests._10676;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

// Проверка работоспособности ссылки "Выход" на странице
public class _10676_10_00 extends TestCase {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    //driver = new FirefoxDriver();
    System.setProperty("webdriver.chrome.driver", "~/src/test/resources/chromedriver");
    driver = new ChromeDriver();
    baseUrl = "http://localhost:9000/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void test106761000() throws Exception {
        driver.get(baseUrl + "login");
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
        assertEquals("Дай погонять", driver.getTitle());
        } catch (Error e) {
        verificationErrors.append(e.toString());
        }
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
    try {
      assertTrue(isElementPresent(By.cssSelector("p")));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    try {
      assertEquals("ВЫХОД", driver.findElement(By.cssSelector("p")).getText());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.cssSelector("p")).click();
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
