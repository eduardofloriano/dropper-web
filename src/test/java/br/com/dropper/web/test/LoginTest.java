package br.com.dropper.web.test;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginTest {

	private static WebDriver driver;
	
	@Test
	public void realizaLogin(){
	
		driver = new ChromeDriver();
		
		driver.get("http://localhost:8080/dropper-web/");
		WebElement email = driver.findElement(By.name("email"));
		email.sendKeys("luis@gmail.com");
		WebElement senha = driver.findElement(By.name("senha"));
		senha.sendKeys("123");
		
		senha.submit();
		
	}
	
	
}
