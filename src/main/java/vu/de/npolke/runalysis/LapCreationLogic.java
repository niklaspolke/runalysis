package vu.de.npolke.runalysis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import vu.de.npolke.runalysis.calculation.CalculationLap;
import vu.de.npolke.runalysis.calculation.CalculationTrack;
import vu.de.npolke.runalysis.calculation.CalculationTrackpointDecorator;
import vu.de.npolke.runalysis.calculation.DistanceInterval;
import vu.de.npolke.runalysis.calculation.Interval;
import vu.de.npolke.runalysis.calculation.TimeInterval;

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
		PointerToLastEndpoint lastLapEndPoint = new PointerToLastEndpoint();
		CalculationLap newLap;

		do {
			newLap = createLapByTime(minPerRound, track, lastLapEndPoint);
			if (newLap != null) {
				timeRoundLaps.add(newLap);
			}
		} while (newLap != null);

		return timeRoundLaps;
	}

	public static List<CalculationLap> createLapsByDistanceRounds(final CalculationTrack track, final double kmPerRound) {
		List<CalculationLap> distanceRoundLaps = new ArrayList<CalculationLap>();
		PointerToLastEndpoint lastLapEndPoint = new PointerToLastEndpoint();
		CalculationLap newLap;

		do {
			newLap = createLapByDistance(kmPerRound, track, lastLapEndPoint);
			if (newLap != null) {
				distanceRoundLaps.add(newLap);
			}
		} while (newLap != null);

		return distanceRoundLaps;
	}

	public static List<CalculationLap> createLapsByIntervals(final CalculationTrack track, final List<Interval> intervals) {
		List<CalculationLap> intervalLaps = new ArrayList<CalculationLap>();
		PointerToLastEndpoint lastLapEndPoint = new PointerToLastEndpoint();
		CalculationLap newLap;

		for (Interval interval : intervals) {
			if (interval instanceof TimeInterval) {
				TimeInterval timeInterval = (TimeInterval) interval;
				newLap = createLapByTime(timeInterval.getTimeInMinutes(), track, lastLapEndPoint);
			} else {
				DistanceInterval distanceInterval = (DistanceInterval) interval;
				newLap = createLapByDistance(distanceInterval.getDistanceInKilometers(), track, lastLapEndPoint);
			}
			if (newLap != null) {
				intervalLaps.add(newLap);
			}
		}

		// if possible, create last lap
		newLap = createLapByDistance(1000, track, lastLapEndPoint);
		if (newLap != null) {
			intervalLaps.add(newLap);
		}

		return intervalLaps;
	}

	private static CalculationLap createLapByTime(final double timeInMinutes, final CalculationTrack track,
			final PointerToLastEndpoint lastLapEndPoint) {
		CalculationLap newLap = null;
		CalculationTrackpointDecorator currentPoint = null;
		long timeDifferenceInMilliseconds = 0;

		// go to current point
		Iterator<CalculationTrackpointDecorator> pointIterator = track.getTrackpoints().iterator();
		do {
			currentPoint = pointIterator.next();
		} while (currentPoint != lastLapEndPoint.point && lastLapEndPoint.point != null);

		while (pointIterator.hasNext() && timeDifferenceInMilliseconds < timeInMinutes * 60 * 1000) {
			currentPoint = pointIterator.next();
			timeDifferenceInMilliseconds = currentPoint.getRunDurationInMilliseconds();
			if (lastLapEndPoint.point != null) {
				timeDifferenceInMilliseconds -= lastLapEndPoint.point.getRunDurationInMilliseconds();
			}
		}

		if (currentPoint != lastLapEndPoint.point) {
			newLap = createLap(currentPoint, lastLapEndPoint.point);
			lastLapEndPoint.point = currentPoint;
		}

		return newLap;
	}

	private static CalculationLap createLapByDistance(final double distanceInKilometers, final CalculationTrack track,
			final PointerToLastEndpoint lastLapEndPoint) {
		CalculationLap newLap = null;
		CalculationTrackpointDecorator currentPoint = null;
		double distanceDifferenceInMeters = 0;

		// go to current point
		Iterator<CalculationTrackpointDecorator> pointIterator = track.getTrackpoints().iterator();
		do {
			currentPoint = pointIterator.next();
		} while (currentPoint != lastLapEndPoint.point && lastLapEndPoint.point != null);

		while (pointIterator.hasNext() && distanceDifferenceInMeters < distanceInKilometers * 1000) {
			currentPoint = pointIterator.next();
			distanceDifferenceInMeters = currentPoint.getRunDistanceInMeters();
			if (lastLapEndPoint.point != null) {
				distanceDifferenceInMeters -= lastLapEndPoint.point.getRunDistanceInMeters();
			}
		}

		if (currentPoint != lastLapEndPoint.point) {
			newLap = createLap(currentPoint, lastLapEndPoint.point);
			lastLapEndPoint.point = currentPoint;
		}

		return newLap;
	}

	static class PointerToLastEndpoint {
		CalculationTrackpointDecorator point;
	}
}
