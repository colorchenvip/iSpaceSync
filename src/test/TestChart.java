package test;

import javax.swing.JFrame;

import org.math.plot.Plot2DPanel;


public class TestChart {
	static double[] x = new double[] { 1, 2, 3, 4 };
	static double[] y = new double[] { 2, 4, 9, 16 };
	static double[] y2 = new double[] { 3, 5, 7, 20 };

	public static void main(String args[]) {
		Plot2DPanel plot = new Plot2DPanel();
		plot.addLinePlot("my plot", x, y);
		plot.addLinePlot("my plot2", x, y2);

		// put the PlotPanel in a JFrame, as a JPanel
		JFrame frame = new JFrame("a plot panel");
		frame.setSize(1024, 768);
		frame.setContentPane(plot);
		frame.setVisible(true);
	}

}
