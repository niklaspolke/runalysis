package vu.de.npolke.runalysis.calculation;

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
public class CalculationLap {

	private final long runDurationInMilliseconds;

	private final double runDistanceInMeters;

	public CalculationLap(final long runDurationInMilliseconds, final double runDistanceInMeters) {
		this.runDurationInMilliseconds = runDurationInMilliseconds;
		this.runDistanceInMeters = runDistanceInMeters;
	}

	public long getRunDurationInMilliseconds() {
		return runDurationInMilliseconds;
	}

	public double getRunDistanceInMeters() {
		return runDistanceInMeters;
	}
}
