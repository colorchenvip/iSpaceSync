package com.dislab.leocai.spacesync.test;
import com.sun.j3d.utils.universe.SimpleUniverse;

import com.sun.j3d.utils.geometry.ColorCube;

import javax.media.j3d.BranchGroup;

import javax.media.j3d.Canvas3D;
import javax.swing.JFrame;

import java.awt.GraphicsConfiguration;

import java.awt.BorderLayout;

import java.awt.Label;

import java.applet.Applet;

import com.dislab.leocai.spacesync.ui.WorldView;
import com.sun.j3d.utils.applet.MainFrame;

public class CanvasDemo extends JFrame {

/**
	 * 
	 */
	private static final long serialVersionUID = -658178501782021828L;

public CanvasDemo() {

   setLayout(new BorderLayout());

   GraphicsConfiguration config =

   SimpleUniverse.getPreferredConfiguration();

   Canvas3D canvas = new Canvas3D(config);

   add("North",new Label("This is the top"));

   add("Center", canvas);

   add("South",new Label("This is the bottom"));

   BranchGroup contents = new BranchGroup();

   contents.addChild(new ColorCube(0.3));

   SimpleUniverse universe = new SimpleUniverse(canvas);

   universe.getViewingPlatform().setNominalViewingTransform();

   universe.addBranchGraph(contents);

}

public static void main( String[] args ) {

//   CanvasDemo demo = new CanvasDemo();
//   demo.setSize(100, 100);
//   demo.setVisible(true);
   WorldView worldView = new WorldView();
   


}

}