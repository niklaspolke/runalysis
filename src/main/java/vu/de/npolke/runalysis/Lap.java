package vu.de.npolke.runalysis;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Copyright (C) 2015 Niklas Polke<br/>
 * <br/>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.<br/>
 * <br/>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * @author Niklas Polke
 */
public class Lap {

	private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");

	private Date startTime;

	private double totalTimeSeconds;

	private double distanceMeters;

	private LapIntensity intensity;

	private List<Trackpoint> points;

	public Lap() {
		points = new LinkedList<Trackpoint>();
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(final Date startTime) {
		this.startTime = startTime;
	}

	public double getTotalTimeSeconds() {
		return totalTimeSeconds;
	}

	public void setTotalTimeSeconds(final double totalTimeSeconds) {
		this.totalTimeSeconds = totalTimeSeconds;
	}

	public double getDistanceMeters() {
		return distanceMeters;
	}

	public void setDistanceMeters(final double distanceMeters) {
		this.distanceMeters = distanceMeters;
	}

	public LapIntensity getIntensity() {
		return intensity;
	}

	public void setIntensity(final LapIntensity intensity) {
		this.intensity = intensity;
	}

	public List<Trackpoint> getPoints() {
		return points;
	}

	public void addPoint(final Trackpoint newPoint) {
		points.add(newPoint);
	}

	@Override
	public String toString() {
		return Lap.class.getSimpleName() + " (" + TIME_FORMAT.format(getStartTime()) + ", " + getIntensity() + "): "
				+ String.format("%5.0f", getTotalTimeSeconds()) + " secs " + String.format("%5.0f", getDistanceMeters()) + " m";
	}
}
