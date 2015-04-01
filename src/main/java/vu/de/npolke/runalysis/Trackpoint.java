package vu.de.npolke.runalysis;

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

	private final long timestampMillis;

	private final double recordedDistanceMeters;

	public Trackpoint(final long timestampMillis, final double recordedDistanceMeters) {
		this.timestampMillis = timestampMillis;
		this.recordedDistanceMeters = recordedDistanceMeters;
	}

	public long getTimestampMillis() {
		return timestampMillis;
	}

	public double getRecordedDistanceMeters() {
		return recordedDistanceMeters;
	}

	// TODO: Refactoring: remove breakMarker

	private BreakMarker breakMarker;

	public BreakMarker getBreakMarker() {
		return breakMarker;
	}

	public void setBreakMarker(BreakMarker breakMarker) {
		this.breakMarker = breakMarker;
	}
}
