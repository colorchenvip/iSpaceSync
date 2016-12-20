package spacesync;

import java.util.LinkedList;
import java.util.Queue;

import com.mkobos.pca_transform.PCA;

import Jama.Matrix;
import utils.MatrixUtils;
import utils.MyDataBuffer;

public class ConsistentExtractionImpl implements ConsistentExtraction {

	MyDataBuffer buffer;

	public ConsistentExtractionImpl(MyDataBuffer buffer) {
		setBuffer(buffer);
	}

	public MyDataBuffer getBuffer() {
		return buffer;
	}

	public void setBuffer(MyDataBuffer buffer) {
		this.buffer = buffer;
	}

	@Override
	public void init() {

	}

	@Override
	public void addData(double[] data) {
		buffer.add(data);
	}

	@Override
	public double[] extractConsistentData() {
		double[][] data = buffer.get();
		Matrix trainingData = new Matrix(data);
		PCA pca = new PCA(trainingData);
		Matrix testData = new Matrix(data);
		Matrix transformedData = pca.transform(testData, PCA.TransformationType.ROTATION);
		double[][] pca_rs = transformedData.getArray();
		return MatrixUtils.getColumn(pca_rs, 0);
	}

}
