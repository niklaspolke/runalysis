package vu.de.npolke.runalysis.calculation;

import vu.de.npolke.runalysis.BreakMarker;
import vu.de.npolke.runalysis.Trackpoint;

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
public class CalculationTrackpointDecorator {

	private final Trackpoint trackpoint;

	public CalculationTrackpointDecorator(final Trackpoint trackpoint) {
		this.trackpoint = trackpoint;
	}

	public long getTimestampMillis() {
		return trackpoint.getTimestampMillis();
	}

	public double getRecordedDistanceMeters() {
		return trackpoint.getRecordedDistanceMeters();
	}

	// added responsibilities of the decorator:

	private long runDurationInMilliseconds;

	private double runDistanceInMeters;

	private BreakMarker breakMarker = null;

	public long getRunDurationInMilliseconds() {
		return runDurationInMilliseconds;
	}

	public void setRunDurationInMilliseconds(long runDurationInMilliseconds) {
		this.runDurationInMilliseconds = runDurationInMilliseconds;
	}

	public double getRunDistanceInMeters() {
		return runDistanceInMeters;
	}

	public void setRunDistanceInMeters(double runDistanceInMeters) {
		this.runDistanceInMeters = runDistanceInMeters;
	}

	public BreakMarker getBreakMarker() {
		return breakMarker;
	}

	public void setBreakMarker(BreakMarker breakMarker) {
		this.breakMarker = breakMarker;
	}
}
