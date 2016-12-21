package spacesync;

import java.util.Arrays;

public class SyncResult {
	double[] e_fi = new double[3];
	double[] e_fc = new double[3];

	public double[] getE_fi() {
		return e_fi;
	}

	public void setE_fi(double[] e_fi) {
		this.e_fi = e_fi;
	}

	public double[] getE_fc() {
		return e_fc;
	}

	public void setE_fc(double[] e_fc) {
		this.e_fc = e_fc;
	}

	public void add(int i, double e_fi, double e_fc) {
		this.e_fi[i] = e_fi;
		this.e_fc[i] = e_fc;
	}

	@Override
	public String toString() {
		return "SyncResult [e_fi=" + Arrays.toString(e_fi) + ", e_fc=" + Arrays.toString(e_fc) + "]";
	}

	public double getAngle() {
		return Math.atan2(e_fi[0], e_fi[1]) / Math.PI * 180;
	}

}
