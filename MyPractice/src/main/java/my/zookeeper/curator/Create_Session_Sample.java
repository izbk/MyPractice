package my.zookeeper.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class Create_Session_Sample {

	public static void main(String[] args) {
		RetryPolicy policy = new ExponentialBackoffRetry(1000, 3);

		CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", 5000, 3000, policy);
		client.start();
	}

}
