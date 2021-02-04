package cz.sinko.realitydog.service;

public class WaitUtils {

	public static void wait(int seconds) throws InterruptedException {
		Thread.sleep(seconds * 1000);
	}

}
