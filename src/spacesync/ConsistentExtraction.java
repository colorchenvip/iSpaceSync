package spacesync;

public interface ConsistentExtraction {

	void init();

	void addData(double[] data);

	double[] extractConsistentData();

}
