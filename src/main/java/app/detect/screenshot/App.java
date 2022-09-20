package app.detect.screenshot;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
public class App {
	
	public static void main(String[] args)  throws IOException {
		String os = System.getProperty("os.name").toLowerCase();
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--disable-gpu");
		final WebDriver driver = new ChromeDriver(chromeOptions);
		if (os.contains("mac")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/chromedriver");
		} else {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\chromedriver.exe");
		}
		driver.manage().window().maximize();
		
		new Timer().scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
            	File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            	String fileName = "";
            	
        		try {
        			fileName = System.getProperty("user.dir")+"/pictures/screenshot" + String.valueOf(System.nanoTime())+".png";
					FileUtils.copyFile(src, new File(fileName));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					String[] files = ImageProcessing.cropImage(fileName);
	        		if (files.length > 0) {
	        			String value1 = ImageCracker.crackImage(files[0]);
	            		String value2 = ImageCracker.crackImage(files[1]);
	            		String value3 = ImageCracker.crackImage(files[2]);
	            		String result = "";
	            		int timedown = -1000000;
	            		try {
	            			result = ImageProcessing.getColor(files[3]);	
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	            		System.out.println("-------------------------------");
	            		System.out.println("Times: "+Tools.getCurrentTime());
	            		System.out.println("DETECTED_VAL1: "+Tools.stringDetected(value1)+" DETECTED_VAL2: "+Tools.stringDetected(value2));
	            		
	            		if (Tools.getTimedown() == -1000000) {
	            			NumberFormat format = NumberFormat.getInstance();
		            		Number current;
		            		timedown = 60;
							try {
								if (value3.length() < 5) {
									current = format.parse(Tools.stringDetected(value3));
									timedown = current.intValue();
									System.out.println("TIMEDOWN_1: "+timedown);
									if (timedown < 80 && timedown >=0) {
										Tools.countDown(60, timedown);
									} else {
										System.out.println("TIMEDOWN IS EXCEPTION");
									}
								} else {
									System.out.println("TIMEDOWN IS NOT DETECTED");
								}
								
							} catch (ParseException e) {
								System.out.println("TIMEDOWN IS NOT DETECTED");
							}
							
							if(timedown < 80 && timedown >=0 && !Tools.stringDetected(value1).equals("") && !Tools.stringDetected(value2).equals("")) {
								int val1 = Integer.parseInt(Tools.stringDetected(value1));
			            		int val2 = Integer.parseInt(Tools.stringDetected(value2));
			            		if (timedown != -1000000) {
			            			Item item = new Item(val1, val2, timedown, result);
			            			SQLiteJDBC.insert(item);
			            			System.out.println("item is inserted: "+item.toInsertData());
			            		}
		            		} else {
			        			System.out.println("VAL1 OR VAL2 OR TIMEDOWN NOT DETECTED");
			        		}
	            		} else {
	            			int TIMEDOWN = Tools.getTimedown();
	            			System.out.println("TIMEDOWN_2: "+Tools.getTimedown());
	            			if(TIMEDOWN < 80 && TIMEDOWN >=0 && !Tools.stringDetected(value1).equals("") && !Tools.stringDetected(value2).equals("")) {
								int val1 = Integer.parseInt(Tools.stringDetected(value1));
			            		int val2 = Integer.parseInt(Tools.stringDetected(value2));
			            		Item item = new Item(val1, val2, TIMEDOWN, result);
			            		SQLiteJDBC.insert(item);
			            		System.out.println("item is inserted: "+item.toInsertData());
		            		} else {
			        			System.out.println("VAL1 OR VAL2 OR TIMEDOWN NOT DETECTED");
			        		}
	            		}
	            		
	        		} else {
	        			System.out.println("RESULTS ARE NOT DETECTED");
	        		}
				}
            }
        },0,20*1000);
	}
}
