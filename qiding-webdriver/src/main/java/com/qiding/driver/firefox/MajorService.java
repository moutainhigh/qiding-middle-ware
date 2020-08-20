package com.qiding.driver.firefox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MajorService {
	private String majorName;
	private WrapWebDriver webDriver;
	private String cityName;
	private ExecutorService executorService= Executors.newFixedThreadPool(10);

	public MajorService(String majorName, WrapWebDriver webDriver, String cityName) {
		this.majorName = majorName;
		this.webDriver = webDriver;
		this.cityName = cityName;
	}

	/**
	 *
	 * @param seconds 停顿时间
	 */
	public void homePage(Long seconds) throws InterruptedException {
		TimeUnit.SECONDS.sleep(seconds);
		//切换城市
		setCity();
		WebElement element=webDriver.findElement(By.xpath("/html/body/div[1]/div/div/div/div/div/div/div[2]/div[5]/div[2]/div[1]/div[1]/input"));
		element.sendKeys(majorName);
		//查询
		WebElement submit=webDriver.findElement(By.xpath("/html/body/div[1]/div/div/div/div/div/div/div[2]/div[5]/div[2]/div[1]/div[1]/button"));
		submit.click();
	}

	public List<WebElement> schoolList() throws InterruptedException {
		List<WebElement> list= webDriver.findElements(By.className("hoverschoolname"));
		List<WebElement> schoolList=new ArrayList<>(list.size()/2);
		list.forEach(element -> {
			if(!element.getText().startsWith(majorName)){
				schoolList.add(element);
			}
		});
		return schoolList;
	}


	public List<WebElement> pageButton() throws InterruptedException {
		List<WebElement> list=webDriver.findElements(By.className("active"));
		list.addAll(webDriver.findElements(By.className("none")));
		List<WebElement> pageList=new ArrayList<>(list.size()-3);
		list.forEach(element -> {
			char[]chars=element.getText().toCharArray();
            if(chars.length>2){
            	return;
			}
		  char current=	element.getText().toCharArray()[0];
		  int result=current-'0';
		  if(result>0&&result<20){
			  pageList.add(element);
		  }
		});
		return pageList;
	}




	public MajorResource process()throws InterruptedException{

		WebElement 	submit=webDriver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/div/div[2]/div/div/ul/li[6]/span"));
		submit.click();

		CountDownLatch countDown=new CountDownLatch(2);

		MajorResource resource=new MajorResource();

		//学校名称
		executorService.execute(()->{
			WebElement 	schoolNameItem=webDriver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/div/div[2]/div/div/div[2]/div[1]/span[1]"));
			String schoolName=schoolNameItem.getText();
			resource.setSchoolName(schoolName);
			countDown.countDown();
		});

		//学校类型
		executorService.execute(()->{
			WebElement	schoolTypeItem=webDriver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/div/div[2]/div/div/div[2]/div[2]/div[3]"));
			String schoolType=schoolTypeItem.getText();
			resource.setSchoolType(schoolType);
			countDown.countDown();
		});

		countDown.await();
		//分数信息
		return  getMajorInfo(resource,2);
	}

	public void setCity()throws InterruptedException{
		//打开下拉
		WebElement 	currentCity=webDriver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/div/div[2]/div[3]/div/div[3]/div"));

		String currentCityText=currentCity.getText();

		if(currentCityText.equals(cityName)){
			return;
		}

		updateCity();
//
//
//		Actions action=new Actions(webDriver.getWebDriver());
//
//		int i=0;
//
//
//
//
//		while(!city.equals(cityName)){
//
//
//			action.sendKeys(Keys.ARROW_DOWN).perform();
//			action.sendKeys(Keys.ENTER).perform();
//			currentCity=webDriver.findElement(By.xpath("/html/body/div[1]/div/div/div/div/div/div/div[3]/div[1]/div[1]/div/div[1]/div[1]/div/div[1]/div/div/div/div"));
//			cityName=currentCity.getText();
//		}

	}

	public MajorResource getMajorInfo(MajorResource resource,int i) throws InterruptedException {
		return getMajorInfo(resource.getSchoolName(),resource.getSchoolType(),i);
	}

	public MajorResource getMajorInfo(String schoolName,String schoolType, int i) throws InterruptedException {


		MajorResource.MajorResourceBuilder builder= MajorResource.builder().schoolName(schoolName)
			.major(majorName).schoolType(schoolType);

		CountDownLatch countDownLatch=new CountDownLatch(5);


		//2019年数据
		executorService.execute(()->{
			WebElement submit=webDriver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/div/div[3]/div[1]/div[1]/div/div[1]/div[2]/div[1]/table/tbody/tr["+i+"]/td[1]"),1L);
			String year=submit.getText();
			builder.year(year);
			countDownLatch.countDown();
		});

        executorService.execute(()->{
			WebElement	lowScore=webDriver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/div/div[3]/div[1]/div[1]/div/div[1]/div[2]/div[1]/table/tbody/tr["+i+"]/td[4]"),0L);
			String lower=lowScore.getText();
			builder.lowerScore(lower);
			countDownLatch.countDown();
		});


        executorService.execute(()->{
			WebElement	positionItem=webDriver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/div/div[3]/div[1]/div[1]/div/div[1]/div[2]/div[1]/table/tbody/tr["+i+"]/td[5]"),0L);
			String position=positionItem.getText();
			builder.position(position);
			countDownLatch.countDown();
		});


		executorService.execute(()->{
			WebElement	baseScoreItem=webDriver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/div/div[3]/div[1]/div[1]/div/div[1]/div[2]/div[1]/table/tbody/tr["+i+"]/td[6]"),0L);
			String baseScore=baseScoreItem.getText();
			builder.baseScore(baseScore);
			countDownLatch.countDown();
		});


		executorService.execute(()->{
			WebElement	levelItem=webDriver.findElement(By.xpath("/html/body/div[1]/div/div/div/div/div/div/div[3]/div[1]/div[1]/div/div[1]/div[2]/div[1]/table/tbody/tr["+i+"]/td[9]"),0L);
			String level=levelItem.getText();
			builder.level(level);
			countDownLatch.countDown();
		});

		countDownLatch.await();
		MajorResource majorResource=builder.build();
		return majorResource;
	}

	public void updateCity() throws InterruptedException {
		WebElement element=	webDriver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/div/div[2]/div[3]/div/div[3]/p"));
		element.click();

		TimeUnit.SECONDS.sleep(1);
		List<WebElement> elements=webDriver.findElements(By.className("content-city"));

		for(WebElement item:elements){
			if(cityName.equals(item.getText())){
				item.click();
				return;
			}
		}
	}
}
