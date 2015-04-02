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
public class Track {

	private final long startTimestampMillis;

	private final List<Lap> laps;

	public Track(final long startTimestampMillis, final List<Lap> laps) {
		this.startTimestampMillis = startTimestampMillis;
		this.laps = new LinkedList<Lap>(laps);
	}

	public long getStartTimestampMillis() {
		return startTimestampMillis;
	}

	public List<Lap> getLaps() {
		return new LinkedList<Lap>(laps);
	}

	public double getTotalTimeSeconds() {
		double durationInSeconds = 0;
		for (Lap lap : laps) {
			durationInSeconds += lap.getRecordedTotalTimeSeconds();
		}
		return durationInSeconds;
	}

	public double getDistanceMeters() {
		double distanceInMeters = 0;
		for (Lap lap : laps) {
			distanceInMeters += lap.getRecordedDistanceMeters();
		}
		return distanceInMeters;
	}
}
