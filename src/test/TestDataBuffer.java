package test;

import java.util.Arrays;

import utils.ArrayUtils;
import utils.MyDataBuffer;

public class TestDataBuffer {

	public static void main(String[] args) {
		MyDataBuffer myDataBuffer = new MyDataBuffer(4);
		myDataBuffer.add(new double[] { 0, 1, 2 });
		myDataBuffer.add(new double[] { 2, 1, 2 });
		myDataBuffer.add(new double[] { 3, 1, 2 });
		ArrayUtils.print(myDataBuffer.get());
		System.out.println();
		myDataBuffer.add(new double[] { 4, 1, 2 });
		myDataBuffer.add(new double[] { 5, 1, 2 });
		myDataBuffer.add(new double[] { 6, 1, 2 });
		ArrayUtils.print(myDataBuffer.get());
		System.out.println();
		myDataBuffer.add(new double[] { 7, 1, 2 });
		myDataBuffer.add(new double[] { 8, 1, 2 });
		myDataBuffer.add(new double[] { 9, 1, 2 });
		ArrayUtils.print(myDataBuffer.get());
		System.out.println();
		myDataBuffer.add(new double[] { 10, 7, 2 });
		myDataBuffer.add(new double[] { 11, 1, 2 });
		myDataBuffer.add(new double[] { 12, 1, 2 });
		ArrayUtils.print(myDataBuffer.get());
	}

}
