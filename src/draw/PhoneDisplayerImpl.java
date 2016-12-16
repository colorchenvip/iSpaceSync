package draw;

import javax.media.j3d.Appearance;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.geometry.Box;

public class PhoneDisplayerImpl implements PhoneDisplayer {

	private float scale = 1;
	private TransformGroup tg;

	@Override
	public void initView() {
		Appearance appearance = new Appearance();
		appearance.setPolygonAttributes(
				new PolygonAttributes(PolygonAttributes.POLYGON_LINE, PolygonAttributes.CULL_BACK, 0.0f));
		Box sphere = new Box(0.25f * scale, 0.5f * scale, 0.03f * scale, appearance);
		WorldView worldView = new WorldView();
		tg = new TransformGroup();
		tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tg.addChild(sphere);
		worldView.getGroup().addChild(tg);
		worldView.show();
	}

	@Override
	public void updateView() {
		// TODO Auto-generated method stub

	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRotationMatrix_b2g(double[][] rtm_b2g) {
		Transform3D transform3 = new Transform3D();
		double[] rtm = new double[9];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				rtm[i*3+j] = rtm_b2g[i][j];
			}
		}
		Matrix3d matrix3d = new Matrix3d(rtm);
		transform3.set(matrix3d);
//		transform3.mul(getTransform3D());
		// transform.mul(transform3);
		// tg.setTransform(transform);
//		transform3.setTranslation(new Vector3d(x, y, z));

		tg.setTransform(transform3);
	}

}
