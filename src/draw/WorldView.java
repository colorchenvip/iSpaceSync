package draw;

import java.util.Scanner;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Group;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.universe.SimpleUniverse;

/**
 * 用于显示手机界面
 * 
 * @author leocai
 *
 */
public class WorldView {

	private BranchGroup group;
	private SimpleUniverse universe;

	public WorldView() {
		universe = new SimpleUniverse();
		group = new BranchGroup();
	}

	public void show() {
		Color3f light1Color = new Color3f(.1f, 1.4f, .1f); // green light
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
		Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
		DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
		light1.setInfluencingBounds(bounds);
		group.addChild(light1);
		universe.getViewingPlatform().setNominalViewingTransform();
		universe.addBranchGraph(group);
	}

	public BranchGroup getGroup() {
		return group;
	}

}
