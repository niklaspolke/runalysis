package vu.de.npolke.runalysis;

import java.util.ArrayList;
import java.util.List;

import vu.de.npolke.runalysis.calculation.CalculationLap;
import vu.de.npolke.runalysis.calculation.CalculationTrack;
import vu.de.npolke.runalysis.calculation.CalculationTrackpointDecorator;

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
public class LapCreationLogic {

	private static CalculationLap createLap(final CalculationTrackpointDecorator currentPoint,
			final CalculationTrackpointDecorator lastLapEndPoint) {
		long timeDifferenceInMilliseconds = currentPoint.getRunDurationInMilliseconds();
		double distanceDifferenceInMeters = currentPoint.getRunDistanceInMeters();
		if (lastLapEndPoint != null) {
			timeDifferenceInMilliseconds -= lastLapEndPoint.getRunDurationInMilliseconds();
			distanceDifferenceInMeters -= lastLapEndPoint.getRunDistanceInMeters();
		}
		return new CalculationLap(timeDifferenceInMilliseconds / 1000, distanceDifferenceInMeters);
	}

	public static List<CalculationLap> createLapsByTimeRounds(final CalculationTrack track, final int minPerRound) {
		List<CalculationLap> timeRoundLaps = new ArrayList<CalculationLap>();

		CalculationTrackpointDecorator endPointOfLastLap = null;
		CalculationTrackpointDecorator currentPoint = null;
		long timeDifferenceInMilliseconds;

		for (CalculationTrackpointDecorator point : track.getTrackpoints()) {
			currentPoint = point;
			timeDifferenceInMilliseconds = currentPoint.getRunDurationInMilliseconds();
			if (endPointOfLastLap != null) {
				timeDifferenceInMilliseconds -= endPointOfLastLap.getRunDurationInMilliseconds();
			}

			// create lap after minPerRound minutes
			if (timeDifferenceInMilliseconds >= minPerRound * 60 * 1000) {
				timeRoundLaps.add(createLap(currentPoint, endPointOfLastLap));
				endPointOfLastLap = currentPoint;
			}
		}

		// create end lap
		if (currentPoint != endPointOfLastLap) {
			timeRoundLaps.add(createLap(currentPoint, endPointOfLastLap));
		}

		return timeRoundLaps;
	}

	public static List<CalculationLap> createLapsByDistanceRounds(final CalculationTrack track, final double kmPerRound) {
		List<CalculationLap> timeRoundLaps = new ArrayList<CalculationLap>();

		CalculationTrackpointDecorator endPointOfLastLap = null;
		CalculationTrackpointDecorator currentPoint = null;
		double distanceDifferenceInMeters;

		for (CalculationTrackpointDecorator point : track.getTrackpoints()) {
			currentPoint = point;
			distanceDifferenceInMeters = currentPoint.getRunDistanceInMeters();
			if (endPointOfLastLap != null) {
				distanceDifferenceInMeters -= endPointOfLastLap.getRunDistanceInMeters();
			}

			// create lap after kmPerRound kilometers
			if (distanceDifferenceInMeters >= kmPerRound * 1000) {
				timeRoundLaps.add(createLap(currentPoint, endPointOfLastLap));
				endPointOfLastLap = currentPoint;
			}
		}

		// create end lap
		if (currentPoint != endPointOfLastLap) {
			timeRoundLaps.add(createLap(currentPoint, endPointOfLastLap));
		}

		return timeRoundLaps;
	}
}
