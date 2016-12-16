package spacesync;
import java.util.Random;

public class TestSpaceSync {

	public static void main(String[] args) {
		SpaceSync spaceSync = new SpaceSyncImpl();
		double sd[][] = new double[5][10];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				sd[i][j] = new Random().nextDouble()*5;
			}
		}
		SensorData sensorData = new SensorData(sd);
		double rs = spaceSync.singleSync(sensorData);
		System.out.println(rs);
	}

}
