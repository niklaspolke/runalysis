package vu.de.npolke.runalysis;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

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
public class Trackpoint {

	private static SimpleDateFormat TIME_FORMAT;

	static {
		TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
		TIME_FORMAT.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
	}

	private Date time;

	private double distanceMeters;

	public Trackpoint() {
	}

	public Date getTime() {
		return time;
	}

	public void setTime(final Date time) {
		this.time = time;
	}

	public double getDistanceMeters() {
		return distanceMeters;
	}

	public void setDistanceMeters(final double distanceMeters) {
		this.distanceMeters = distanceMeters;
	}

	@Override
	public String toString() {
		return Trackpoint.class.getSimpleName() + ": (" + TIME_FORMAT.format(time) + "): " + String.format("%5.0f", getDistanceMeters())
				+ " m";
	}
}
