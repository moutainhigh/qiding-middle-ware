package com.qiding.driver.firefox;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class WrapWebDriver {

	WebDriver webDriver;


	public WebDriver getWebDriver() {
		return webDriver;
	}

	public WrapWebDriver(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	WebElement findElement(By condition){
		while (true){
			try {
				WebElement webElement=webDriver.findElement(condition);
				TimeUnit.SECONDS.sleep(1);
				return webElement;
			}catch (Exception e){
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException interruptedException) {
					interruptedException.printStackTrace();
				}
			}
		}
	}



	WebElement findElement(By condition,Long delyTime){
		while (true){
			try {
				WebElement webElement=webDriver.findElement(condition);
				TimeUnit.SECONDS.sleep(delyTime);
				return webElement;
			}catch (Exception e){
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException interruptedException) {
					interruptedException.printStackTrace();
				}
			}
		}
	}




	List<WebElement> findElements(By condition){
		while (true){
			try {
				List<WebElement> webElements=webDriver.findElements(condition);
				TimeUnit.SECONDS.sleep(1);
				return webElements;
			}catch (Exception e){
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException interruptedException) {
					interruptedException.printStackTrace();
				}
			}
		}
	}




}
