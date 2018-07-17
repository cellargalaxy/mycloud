package top.cellargalaxy.mycloud;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedTransferQueue;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
public class MainTest {
	public static void main(String[] args) throws IOException, InterruptedException {
		BlockingQueue<Integer> fileTasks = new LinkedTransferQueue<>();
		for (int i = 0; i < 5; i++) {
			fileTasks.add(i);
		}
		for (int i = 0; i < 10; i++) {
			System.out.println(fileTasks.take());
			System.out.println(fileTasks);
		}
	}
}
