package spacesync;

import com.mkobos.pca_transform.PCA;

import Jama.Matrix;
import utils.MatrixUtils;

public class ConsistentExtractionImpl implements ConsistentExtraction {

	@Override
	public double[] extractConsistentData(double[][] data) {
		Matrix trainingData = new Matrix(data);
		PCA pca = new PCA(trainingData);
		Matrix testData = new Matrix(data);
		Matrix transformedData = pca.transform(testData, PCA.TransformationType.ROTATION);
		double[][] pca_rs = transformedData.getArray();
		return MatrixUtils.selectColumn(pca_rs, 0);
	}

}
