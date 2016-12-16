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

public class WorldView {

	private BranchGroup group;
	private SimpleUniverse universe;

	public WorldView() {
		universe = new SimpleUniverse();

		group = new BranchGroup();
		
		

	}

	public void show() {

		Color3f light1Color = new Color3f(.1f, 1.4f, .1f); // green light

		BoundingSphere bounds =

		new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);

		Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);

		DirectionalLight light1

		= new DirectionalLight(light1Color, light1Direction);

		light1.setInfluencingBounds(bounds);

		group.addChild(light1);

		universe.getViewingPlatform().setNominalViewingTransform();

		universe.addBranchGraph(group);
	}

//	public static void main(String[] args) {
//	
//		WorldView worldView = new WorldView();
//		Scanner scanner = new Scanner(System.in);
//
//		PhoneView phoneViewTop = new PhoneView(worldView, -0.3, 1, 0, Math.PI / 2, 0.2, 0.5f);
//		PhoneView phoneViewLeftHand = new PhoneView(worldView, -0.6, 0, 0, Math.PI / 2, -0.4, 0.5f);
//		PhoneView phoneViewRightHand = new PhoneView(worldView, 0.6, 0, 0,  Math.PI / 2, 0.39, 0.5f);
//		PhoneView phoneViewLeftPants = new PhoneView(worldView, 0.6, -1, 0,Math.PI / 2, -0.35, 0.5f);
//		PhoneView phoneViewRightPants = new PhoneView(worldView, -0.6, -1, 0, Math.PI / 2, 0.3, 0.5f);
//
//		scanner.nextLine();
//
//		phoneViewTop.replay("rtm_mag_top.csv");
////		
//		
//		phoneViewLeftHand.replay("rtm_mag_lefthand.csv");
//		
//		phoneViewRightHand.replay("rtm_mag_righthand.csv");
//
//		phoneViewLeftPants.replay("rtm_mag_leftpants.csv");
//		
//		phoneViewRightPants.replay("rtm_mag_rightpants.csv");
//
//		worldView.show();
//	}

	public BranchGroup getGroup() {
		return group;
	}

}
