package selenium;

import junit.framework.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class SeleniumTest  extends TestCase{
    
    public void testLogin(){
        long end = System.currentTimeMillis() + 15000;
        while (System.currentTimeMillis() < end) ;
        
        WebDriver driver = new HtmlUnitDriver();

        // And now use this to visit Google
        driver.get("http://apt-public.appspot.com/testing-lab-login.html");

        WebElement username = driver.findElement(By.name("userId"));
        WebElement pswd = driver.findElement(By.name("userPassword"));
        username.clear();
        pswd.clear();
        //------test andy
        username.sendKeys("andy");
        pswd.sendKeys("apple");
        username.submit();        
        assertTrue(driver.getTitle().equals("Online temperature conversion calculator"));
        
        
        driver.navigate().back();
        username = driver.findElement(By.name("userId"));
        pswd = driver.findElement(By.name("userPassword"));
        username.clear();
        pswd.clear();
        //---------------test bob
        username.sendKeys("bob");
        pswd.sendKeys("bathtub");
        username.submit();        
        assertTrue(driver.getTitle().equals("Online temperature conversion calculator"));
        
        
        driver.navigate().back();
        username = driver.findElement(By.name("userId"));
        pswd = driver.findElement(By.name("userPassword"));
        username.clear();
        pswd.clear();
        //--------test charley
        username.sendKeys("charley");
        pswd.sendKeys("china");
        username.submit();        
        assertTrue(driver.getTitle().equals("Online temperature conversion calculator"));
        
        end = System.currentTimeMillis() + 15000;
        while (System.currentTimeMillis() < end) ;
        
        driver.navigate().back();
        username = driver.findElement(By.name("userId"));
        pswd = driver.findElement(By.name("userPassword"));
        username.clear();
        pswd.clear();
        username.sendKeys("andy");
        pswd.sendKeys("Apple");
        username.submit();      
//        System.out.println(driver.getTitle());
        assertTrue(driver.getTitle().equals("Bad Login"));
        
        end = System.currentTimeMillis() + 15000;
        while (System.currentTimeMillis() < end) ;
        
        driver.get("http://apt-public.appspot.com/testing-lab-login.html");
        username = driver.findElement(By.name("userId"));
        pswd = driver.findElement(By.name("userPassword"));
        username.clear();
        pswd.clear();
        username.sendKeys("Andy");
        pswd.sendKeys("apple");
        username.submit();      
//        System.out.println(driver.getTitle());
        assertFalse(driver.getTitle().equals("Bad Login"));
        
        driver.get("http://apt-public.appspot.com/testing-lab-login.html");
        username = driver.findElement(By.name("userId"));
        pswd = driver.findElement(By.name("userPassword"));
        username.clear();
        pswd.clear();
        username.sendKeys("Andy");
        pswd.sendKeys("Apple");
        username.submit();
        driver.get("http://apt-public.appspot.com/testing-lab-login.html");
        username = driver.findElement(By.name("userId"));
        pswd = driver.findElement(By.name("userPassword"));
        username.clear();
        pswd.clear();
        username.sendKeys("Andy");
        pswd.sendKeys("Apple");
        username.submit();
        assertTrue(driver.getTitle().equals("Frequent Login"));
        driver.quit();
    }
    
    public void testInput(){
        long end = System.currentTimeMillis() + 15000;
        while (System.currentTimeMillis() < end) ;
        
        WebDriver driver= new HtmlUnitDriver();
        driver.get("http://apt-public.appspot.com/testing-lab-login.html");
        WebElement element=driver.findElement(By.name("userId"));
        element.clear();
        WebElement element1=driver.findElement(By.name("userPassword"));
        element1.clear();
        element.sendKeys("bob");
        element1.sendKeys("bathtub");
        element.submit();
        

        element=driver.findElement(By.name("farenheitTemperature"));
        element.clear();
        element.sendKeys("211.99999");
        element.submit();
        assertTrue(driver.getPageSource().contains("211.99999 Farenheit = 100 Celsius"));
        

        driver.navigate().back();
        element=driver.findElement(By.name("farenheitTemperature"));
        element.clear();
        element.sendKeys("joce");
        element.submit();
        assertTrue(driver.getPageSource().contains("Got a NumberFormatException on joce"));
        

        driver.navigate().back();
        element=driver.findElement(By.name("farenheitTemperature"));
        element.clear();
        element.sendKeys("222.222");
        element.submit();
        assertTrue(driver.getPageSource().contains("222.222 Farenheit = 105.68 Celsius"));
        

        driver.navigate().back();
        element=driver.findElement(By.name("farenheitTemperature"));
        element.clear();
        element.sendKeys("500");
        element.submit();
        assertTrue(driver.getPageSource().contains("500 Farenheit = 260 Celsius"));
        

        driver.navigate().back();
        element=driver.findElement(By.name("farenheitTemperature"));
        element.clear();
        element.sendKeys("-456");
        element.submit();
        assertTrue(driver.getPageSource().contains("-456 Farenheit = -271.11 Celsius"));
        
        
        driver.quit();
    }
    
    public void testGeneral() {
    	
        long end = System.currentTimeMillis() + 15000;
        while (System.currentTimeMillis() < end) ;
        
        WebDriver driver= new HtmlUnitDriver();
        driver.get("http://apt-public.appspot.com/testing-lab-login.html");
        WebElement element=driver.findElement(By.name("userId"));
        element.clear();
        WebElement element1=driver.findElement(By.name("userPassword"));
        element1.clear();
        element.sendKeys("bob");
        element1.sendKeys("bathtub");
        element.submit();
        
        assertEquals("Online temperature conversion calculator",driver.getTitle());
        
        element=driver.findElement(By.name("farenheitTemperature"));
        element.clear();
        element.sendKeys("100");
        element.submit();
        assertTrue(driver.getPageSource().contains("100 Farenheit = 37.78 Celsius"));
        
        element=driver.findElement(By.cssSelector("input[value='Berkeley']"));
        element.click();
        element.submit();
        
        assertTrue(driver.getPageSource().contains("Temperature in Berkeley = 72 degrees Farenheit"));
        driver.quit();
    }
}
