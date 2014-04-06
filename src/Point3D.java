import java.awt.Graphics;

/**
 * Point3D.java Version 1.0 Copyright 5/10/2013 by John Schram
 * 
 * Objects of the <Point3D> class store all necessary information to represent a
 * point in 3D space, even if this point is translated, scaled, or rotated
 * around any number of axes.
 * 
 * Based on programs written by Robby Slaughter for the book Student Friendly
 * Advanced VGA Graphics for C++ (c) 1997 Authored by Leon Schram
 * 
 * These programs were translated into Java by Zachary Mathewson and Max Kirby
 * in 2012. From these translated programs, and several of my own ideas, I
 * created a set of programs that are more in line with
 * "Object Oriented Programming". This required the creation of the <Point3D>
 * and <Graphics3D> classes.
 * 
 * This code is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation. This code is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 */

class Point3D {
	private static int maxWidth = 1000;
	private static int maxHeight = 650;
	private static int midWidth = maxWidth / 2;
	private static int midHeight = maxHeight / 2;
	private static int focalLength = maxHeight;
	private static int zOffset = 0;
	private static int zDistance = focalLength + zOffset;

	public void setSize(int width, int height) {
		maxWidth = width;
		maxHeight = height;
		midWidth = maxWidth / 2;
		midHeight = maxHeight / 2;
		focalLength = maxHeight;
		zOffset = 0;
		zDistance = focalLength + zOffset;
	}
	
	private int userX, userY, userZ;
	private int trueX, trueY;

	private double angleX, angleY, angleZ;
	private double radiusX, radiusY, radiusZ;
	private double resultantX, resultantY, resultantZ;
	private double scaleX, scaleY, scaleZ;
	private double translateX, translateY, translateZ;

	private boolean perspectiveOn;

	public Point3D() {
		userX = userY = userZ = 0;
		resultantX = resultantY = resultantZ = 0;
		angleX = angleY = angleZ = 0;
		radiusX = radiusY = radiusZ = 0;
		scaleX = scaleY = scaleZ = 1;
		translateX = translateY = translateZ = 0;
		perspectiveOn = true;
	}

	public Point3D(int x, int y) {
		resultantX = userX = x;
		resultantY = userY = y;
		resultantZ = userZ = 0;
		angleX = angleY = angleZ = 0;
		scaleX = scaleY = scaleZ = 1;
		translateX = translateY = translateZ = 0;
		setAngleRadiusX(userX, userY, userZ);
		setAngleRadiusY(userX, userY, userZ);
		setAngleRadiusZ(userX, userY, userZ);
		perspectiveOn = true;
	}

	public Point3D(int x, int y, int z) {
		resultantX = userX = x;
		resultantY = userY = y;
		resultantZ = userZ = z;
		angleX = angleY = angleZ = 0;
		scaleX = scaleY = scaleZ = 1;
		translateX = translateY = translateZ = 0;
		setAngleRadiusX(userX, userY, userZ);
		setAngleRadiusY(userX, userY, userZ);
		setAngleRadiusZ(userX, userY, userZ);
		perspectiveOn = true;
	}

	public static int getOriginX() {
		return midWidth;
	}

	public static int getOriginY() {
		return midHeight;
	}

	public String toString() {
		return "[" + resultantX + ", " + resultantY + ", " + resultantZ + "]\n";
	}

	public static void setDimensions(int w, int h) {
		maxWidth = w;
		maxHeight = h;
		midWidth = maxWidth / 2;
		midHeight = maxHeight / 2;
		zDistance = focalLength = maxHeight;
		zOffset = 0;
	}

	public static void setDimensions(int w, int h, int zOff) {
		maxWidth = w;
		maxHeight = h;
		midWidth = maxWidth / 2;
		midHeight = maxHeight / 2;
		focalLength = maxHeight;
		zOffset = zOff;
		zDistance = focalLength + zOffset;
	}

	public void turnPerspectiveOn() {
		perspectiveOn = true;
	}

	public void turnPerspectiveOff() {
		perspectiveOn = false;
	}

	public int getUserX() {
		return userX;
	}

	public int getUserY() {
		return userY;
	}

	public int getUserZ() {
		return userZ;
	}

	public double getResultantX() {
		return resultantX;
	}

	public double getResultantY() {
		return resultantY;
	}

	public double getResultantZ() {
		return resultantZ;
	}

	public static int getFocalLength() {
		return focalLength;
	}

	public int getTrueX() {
		calculateTrueX();
		return trueX;
	}

	public int getTrueY() {
		calculateTrueY();
		return -trueY; // The negative sign compensates for the fact that the Y
						// value on a graphics screen increase as they go down
						// instead of up.
	}

	private void calculateTrueX() {
		if (perspectiveOn)
			trueX = (int) (((resultantX + translateX) * focalLength) / (zDistance
					- resultantZ - translateZ));
		else
			trueX = (int) (resultantX + translateX);
	}

	private void calculateTrueY() {
		if (perspectiveOn)
			trueY = (int) (((resultantY + translateY) * focalLength) / (zDistance
					- resultantZ - translateZ));
		else
			trueY = (int) (resultantY + translateY);
	}

	public boolean equals(Point3D that) {
		return this.userX == that.userX && this.userY == that.userY
				&& this.userZ == that.userZ;
	}

	private void setAngleRadiusX(double x, double y, double z) {
		if (z != 0)
			angleX = Math.atan(y / z);
		else if (y > 0)
			angleX = Math.PI * 0.5;
		else
			angleX = Math.PI * 1.5;

		if (z < 0)
			angleX += Math.PI;

		radiusX = Math.sqrt(y * y + z * z);
	}

	private void setAngleRadiusY(double x, double y, double z) {
		if (z != 0)
			angleY = Math.atan(x / z);
		else if (x > 0)
			angleY = Math.PI * 0.5;
		else
			angleY = Math.PI * 1.5;

		if (z < 0)
			angleY += Math.PI;

		radiusY = Math.sqrt(x * x + z * z);
	}

	private void setAngleRadiusZ(double x, double y, double z) {
		if (x != 0)
			angleZ = Math.atan(y / x);
		else if (y > 0)
			angleZ = Math.PI * 0.5;
		else
			angleZ = Math.PI * 1.5;

		if (x < 0)
			angleZ += Math.PI;

		radiusZ = Math.sqrt(x * x + y * y);
	}

	private void scaleAnglesAndRadii() {
		resultantX = userX * scaleX;
		resultantY = userY * scaleY;
		resultantZ = userZ * scaleZ;
		setAngleRadiusX(resultantX, resultantY, resultantZ);
		setAngleRadiusY(resultantX, resultantY, resultantZ);
		setAngleRadiusZ(resultantX, resultantY, resultantZ);
	}

	public void scale(double s) {
		scaleX = s;
		scaleY = s;
		scaleZ = s;
		scaleAnglesAndRadii();
	}

	public void scale(double sx, double sy, double sz) {
		scaleX = sx;
		scaleY = sy;
		scaleZ = sz;
		scaleAnglesAndRadii();
	}

	public void scaleX(double s) {
		scaleX = s;
		scaleAnglesAndRadii();
	}

	public void scaleY(double s) {
		scaleY = s;
		scaleAnglesAndRadii();
	}

	public void scaleZ(double s) {
		scaleZ = s;
		scaleAnglesAndRadii();
	}

	public void translate(double tx, double ty, double tz) {
		translateX = tx;
		translateY = ty;
		translateZ = tz;
	}

	public void translateX(double t) {
		translateX = t;
	}

	public void translateY(double t) {
		translateY = t;
	}

	public void translateZ(double t) {
		translateZ = t;
	}

	public void rotateX(double deltaX) {
		resultantY = Math.round(Math.sin(angleX + deltaX) * radiusX);
		resultantZ = Math.round(Math.cos(angleX + deltaX) * radiusX);
	}

	public void rotateY(double deltaY) {
		resultantX = Math.round(Math.sin(angleY + deltaY) * radiusY);
		resultantZ = Math.round(Math.cos(angleY + deltaY) * radiusY);
	}

	public void rotateZ(double deltaZ) {
		resultantX = Math.round(Math.cos(angleZ + deltaZ) * radiusZ);
		resultantY = Math.round(Math.sin(angleZ + deltaZ) * radiusZ);
	}

	public void rotateXY(double deltaX, double deltaY) {
		setAngleRadiusX(userX * scaleX, userY * scaleY, userZ * scaleX);
		rotateX(deltaX);
		setAngleRadiusY(userX * scaleX, resultantY, resultantZ);
		rotateY(deltaY);
	}

	public void rotateYZ(double deltaY, double deltaZ) {
		setAngleRadiusY(userX * scaleX, userY * scaleY, userZ * scaleZ);
		rotateY(deltaY);
		setAngleRadiusZ(resultantX, userY * scaleY, resultantZ);
		rotateZ(deltaZ);
	}

	public void rotateXZ(double deltaX, double deltaZ) {
		setAngleRadiusX(userX * scaleX, userY * scaleY, userZ * scaleZ);
		rotateX(deltaX);
		setAngleRadiusZ(userX * scaleX, resultantY, resultantZ);
		rotateZ(deltaZ);
	}

	public void rotateXYZ(double deltaX, double deltaY, double deltaZ) {
		setAngleRadiusX(userX * scaleX, userY * scaleY, userZ * scaleZ);
		rotateX(deltaX);
		setAngleRadiusY(userX * scaleX, resultantY, resultantZ);
		rotateY(deltaY);
		setAngleRadiusZ(resultantX, resultantY, resultantZ);
		rotateZ(deltaZ);
	}
}