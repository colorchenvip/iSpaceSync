package spacesync;

import java.util.List;

import utils.CutUtils;
import utils.DataUtils;
import utils.MatrixUtils;
import utils.PlotUtils;
import utils.VectorUtils;

public class SpaceSyncImpl implements SpaceSync {

	CoordinateTracker coordinateTracker = new CoordinateTracker();

	@Override
	public SyncResult spaceSync(SensorDataSet sensorDataSet) {
		List<SensorData> sensorDataList = sensorDataSet.getSensorDataList();
		for (SensorData sensorData : sensorDataList) {

		}
		return null;
	}

	@Override
	public double singleSync(SensorData sensorData) {
		sensorData = CutUtils.cutData(sensorData);
		sensorData = smoothData(sensorData);
		double[][] accs = sensorData.getLinearAccs();
		double[][] gravityAccs = sensorData.getGravityAccs();
		double[][] gyrs = sensorData.getGyrs();
		double[] dt = sensorData.getDT();
		double[][] initMatrix = sensorData.getInitMatrix();
		double[] gravity_vector = gravityAccs[0];
		
		double[][] tracked_acc = coordinateTracker.trackByGyro(accs, gyrs, dt, initMatrix);
		
		double[][] horizental_acc = projectHorizental(tracked_acc, gravity_vector);

		double acc_hori_fi_x[] = MatrixUtils.getColumn(horizental_acc, 0);
		double acc_hori_fi_y[] = MatrixUtils.getColumn(horizental_acc, 1);
		double acc_hori_fi_z[] = MatrixUtils.getColumn(horizental_acc, 2);
		double E_hori_fi_x = mean(acc_hori_fi_x);
		double E_hori_fi_y = mean(acc_hori_fi_y);
		double E_hori_fi_z = mean(acc_hori_fi_z);

		double[][] global_acc = sensorData.getGlobalMagAcc();
		double[][] computed_acc = sensorData.computeGlobalByMag();
		double[] lacc_g = MatrixUtils.getColumn(accs, 1);
		double[] gacc_g = MatrixUtils.getColumn(computed_acc, 1);
//		PlotUtils.plotCompareData(MatrixUtils.getColumn(accs, 1),  MatrixUtils.getColumn(computed_acc, 2));
		PlotUtils.plotData(sensorData.getMagnet());
//		PlotUtils.plotCompareData( MatrixUtils.getColumn(accs, 2),  MatrixUtils.getColumn(computed_acc, 1));
//		PlotUtils.plotCompareData( MatrixUtils.getColumn(accs, 1),  MatrixUtils.getColumn(computed_acc, 1));

//		PlotUtils.plotCompareData(global_acc, computed_acc);
//		PlotUtils.plotCompareData(global_acc, computed_acc);
		double[][] init_mag_global_matrix_g2b = sensorData.getInitGlobalMatrix_G2B();

//		 checkTrackedAcc(tracked_acc, global_acc, init_mag_global_matrix_g2b);
//		 checkHoriAcc(horizental_acc, global_acc, init_mag_global_matrix_g2b);

		return checkResult(sensorData, gravity_vector, E_hori_fi_x, E_hori_fi_y, E_hori_fi_z);
	}

	private void checkTrackedAcc(double[][] tracked_acc, double[][] global_acc, double[][] init_global_matrix_g2b) {
		double tracked_global_acc[][] = new double[tracked_acc.length][tracked_acc[0].length];
		for (int i = 0; i < tracked_acc.length; i++) {
			tracked_global_acc[i] = CoordinateTracker.getGlobalData(tracked_acc[i], MatrixUtils.T(init_global_matrix_g2b));
		}
		PlotUtils.plotCompareData(tracked_acc, global_acc);
	}

	private void checkHoriAcc(double[][] horizental_acc, double[][] global_acc, double[][] init_global_matrix_g2b) {
		double hori_global_acc[][] = new double[horizental_acc.length][horizental_acc[0].length];
		for (int i = 0; i < horizental_acc.length; i++) {
			hori_global_acc[i] = CoordinateTracker.getGlobalData(horizental_acc[i], MatrixUtils.T(init_global_matrix_g2b));
		}
		PlotUtils.plotCompareData(hori_global_acc, global_acc);
	}

	private double checkResult(SensorData sensorData, double[] gravity_vector, double E_hori_fi_x, double E_hori_fi_y,
			double E_hori_fi_z) {
		double[] initMag = sensorData.getMagnet()[0];
		double[] init_hori_mag = MatrixUtils.T(SensorData.getRotationMatrixG2BByMag(gravity_vector, initMag))[1];
		double angle = getSyncDirection(new double[] { E_hori_fi_x, E_hori_fi_y, E_hori_fi_z }, init_hori_mag);
		return angle;
	}

	private double[][] projectHorizental(double[][] tracked_acc, double[] gravity_vector) {
		double[][] hroiData = new double[tracked_acc.length][3];
		for (int i = 0; i < tracked_acc.length; i++) {
			hroiData[i] = projOnHrizenal(tracked_acc[i], gravity_vector);
		}
		return hroiData;
	}

	private SensorData smoothData(SensorData sensorData) {
		double[][] smoothed_data = DataUtils.smooth(sensorData.getData(), 5);
//		PlotUtils.plotCompareData(sensorData.getLinearAccs(), new SensorData(smoothed_data).getLinearAccs());
		sensorData.setData(smoothed_data);
		return sensorData;
	}

	private double[] projOnHrizenal(double[] lv, double[] gv) {
		double absProj = ((lv[0] * gv[0] + lv[1] * gv[1] + lv[2] * gv[2]) / VectorUtils.absVector(gv));
		double absGv = VectorUtils.absVector(gv);
		double pg[] = VectorUtils.vectorNumMultiply(gv, (1 / absGv) * absProj);
		return new double[] { lv[0] - pg[0], lv[1] - pg[1], lv[2] - pg[2] };
	}

	private double getSyncDirection(double[] ds, double[] initMag) {
		return VectorUtils.getAngle(ds, initMag);
	}

	private double mean(double[] data) {
		double mean = 0;
		for (double d : data) {
			mean += d;
		}
		return mean / data.length;
	}

}
