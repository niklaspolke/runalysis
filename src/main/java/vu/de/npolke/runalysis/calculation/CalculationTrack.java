package vu.de.npolke.runalysis.calculation;

import java.util.ArrayList;
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
public class CalculationTrack {

	private List<CalculationTrackpointDecorator> trackpoints;

	private List<CalculationLap> laps;

	public CalculationTrack() {
		trackpoints = new ArrayList<CalculationTrackpointDecorator>();
		laps = new ArrayList<CalculationLap>();
	}

	public List<CalculationTrackpointDecorator> getTrackpoints() {
		return trackpoints;
	}

	public List<CalculationLap> getLaps() {
		return laps;
	}

	public long getStartTimestamp() {
		if (trackpoints.size() > 0) {
			return trackpoints.get(0).getTimestampMillis();
		} else {
			return 0;
		}
	}

	public long getRunDurationInSeconds() {
		long runDurationInSeconds = 0;
		for (CalculationLap lap : laps) {
			runDurationInSeconds += lap.getRunDurationInSeconds();
		}
		return runDurationInSeconds;
	}

	public double getRunDistanceInMeters() {
		double runDistanceInMeters = 0;
		for (CalculationLap lap : laps) {
			runDistanceInMeters += lap.getRunDistanceInMeters();
		}
		return runDistanceInMeters;
	}
}
