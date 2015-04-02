package vu.de.npolke.runalysis;

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

	private final long startTimestampMillis;

	private final double recordedTotalTimeSeconds;

	private final double recordedDistanceMeters;

	private final LapIntensity intensity;

	private final List<Trackpoint> points;

	public Lap(final long startTimestampMillis, final double recordedTotalTimeSeconds, final double recordedDistanceMeters,
			final LapIntensity intensity, final List<Trackpoint> points) {
		this.startTimestampMillis = startTimestampMillis;
		this.recordedTotalTimeSeconds = recordedTotalTimeSeconds;
		this.recordedDistanceMeters = recordedDistanceMeters;
		this.intensity = intensity;
		if (points != null) {
			this.points = new LinkedList<Trackpoint>(points);
		} else {
			this.points = new LinkedList<Trackpoint>();
		}
	}

	public long getStartTimestampMillis() {
		return startTimestampMillis;
	}

	public double getRecordedTotalTimeSeconds() {
		return recordedTotalTimeSeconds;
	}

	public double getRecordedDistanceMeters() {
		return recordedDistanceMeters;
	}

	public LapIntensity getIntensity() {
		return intensity;
	}

	public List<Trackpoint> getPoints() {
		return new LinkedList<Trackpoint>(points);
	}
}
