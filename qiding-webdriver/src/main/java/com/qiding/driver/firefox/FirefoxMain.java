package com.qiding.driver.firefox;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FirefoxMain {

	private static WebDriver baseDriver;
	private 	static MajorService majorService;


	public static  void init() throws InterruptedException {
		System.setProperty("webdriver.gecko.driver","/Users/qiding/systemtools/geckodriver");
		baseDriver=new FirefoxDriver();
		TimeUnit.SECONDS.sleep(2);
		System.out.println("浏览器启动成功");
		//打开网站
		baseDriver.get("https://gkcx.eol.cn/specials/school");
		WrapWebDriver webDriver=new WrapWebDriver(baseDriver);
		majorService=new MajorService("医学影像学",webDriver,"南阳市");
		//打开主页
		majorService.homePage(9L);
	}

	public static List<MajorResource> majorResources() throws InterruptedException {
		//页面
		List<WebElement> pageList=majorService.pageButton();
		int pageSize=pageList.size();
		//记录查询结果
		List<MajorResource> countResult=new ArrayList<>();
		for(int i=0;i<pageSize;i++){
			WebElement element=pageList.get(i);
			element.click();
			TimeUnit.SECONDS.sleep(3);

			List<WebElement> schoolList=majorService.schoolList();
			int schoolSize=schoolList.size();

			for(int j=0;j<schoolSize;j++){
				//点击学校链接
				WebElement schoolItem=schoolList.get(j);
				System.out.println(schoolItem.getText());
				schoolItem.click();

				//解析资源
				MajorResource majorResource=majorService.process();
				countResult.add(majorResource);
				//页面回退
				baseDriver.navigate().back();
				baseDriver.navigate().back();
				//重新选择页面
				pageList=majorService.pageButton();
				WebElement pageItem=pageList.get(i);
				pageItem.click();
				//TimeUnit.SECONDS.sleep(1);

				//刷新结果
				schoolList=majorService.schoolList();
			}
			//刷新butoon选择页
			pageList=majorService.pageButton();
		}
		return countResult;
	}

	public static void writeToFile(List<MajorResource>  infoList) throws IOException {
		String filePath=FirefoxMain.class.getClassLoader().getResource("CountResult").getFile();
		RandomAccessFile  accessFile=new RandomAccessFile(filePath,"rw");
		FileChannel fileChannel=accessFile.getChannel();

		ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
		infoList.forEach(info->{
			byteBuffer.put((info.toString()+"\n\n").getBytes(Charset.defaultCharset()));
			try {
				byteBuffer.flip();
				fileChannel.write(byteBuffer);
			} catch (IOException e) {
				e.printStackTrace();
			}
			byteBuffer.clear();
		});
		fileChannel.close();
		accessFile.close();
	}


	public static void main(String[] args) throws InterruptedException, IOException {
		init();
		List<MajorResource> infoList=majorResources();
		writeToFile(infoList);
		close();

	}


	public static void close(){
		Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public synchronized void start() {
				baseDriver.close();
			}
		});
	}










}
