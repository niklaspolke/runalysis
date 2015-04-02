package vu.de.npolke.runalysis;

import java.util.Deque;
import java.util.LinkedList;
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
public class BreakCorrectionLogic {

	private static boolean isStartOfBreak(final CalculationTrackpointDecorator trackpoint, final Deque<Lap> breakLapsStack) {
		boolean isStartOfBreak = false;
		if (breakLapsStack.isEmpty() == false) {
			isStartOfBreak = trackpoint.getTimestampMillis() > breakLapsStack.peek().getStartTimestampMillis();
		}
		return isStartOfBreak;
	}

	private static void addBreak(final BreakDifference currentBreakDifference, final CalculationTrackpointDecorator startOfBreak,
			final Trackpoint endOfBreak) {
		currentBreakDifference.distanceInMeters += endOfBreak.getRecordedDistanceMeters() - startOfBreak.getRecordedDistanceMeters();
		currentBreakDifference.durationInMilliseconds += endOfBreak.getTimestampMillis() - startOfBreak.getTimestampMillis();
	}

	private static CalculationTrackpointDecorator calcTrackpoint(final Trackpoint originalPoint, final Trackpoint startPoint,
			final BreakDifference breakDiff) {
		// decorate
		CalculationTrackpointDecorator newTrackpoint = new CalculationTrackpointDecorator(originalPoint);
		// calculate
		newTrackpoint.setRunDurationInMilliseconds(originalPoint.getTimestampMillis() - startPoint.getTimestampMillis()
				- breakDiff.durationInMilliseconds);
		newTrackpoint.setRunDistanceInMeters(originalPoint.getRecordedDistanceMeters() - breakDiff.distanceInMeters);
		return newTrackpoint;
	}

	private static CalculationLap calcLap(final CalculationTrackpointDecorator currentPoint,
			final CalculationTrackpointDecorator endPointOfLastLap) {
		long durationInMillis = currentPoint.getRunDurationInMilliseconds();
		double distanceInMeters = currentPoint.getRunDistanceInMeters();
		if (endPointOfLastLap != null) {
			durationInMillis -= endPointOfLastLap.getRunDurationInMilliseconds();
			distanceInMeters -= endPointOfLastLap.getRunDistanceInMeters();
		}
		return new CalculationLap(durationInMillis / 1000, distanceInMeters);
	}

	public static CalculationTrack removeBreaksFromTrack(final Track track) {
		// search for break laps
		List<Lap> runningLapList = new LinkedList<Lap>();
		Deque<Lap> breakLapStack = new LinkedList<Lap>();
		for (Lap lap : track.getLaps()) {
			if (LapIntensity.RESTING.equals(lap.getIntensity())) {
				breakLapStack.addLast(lap);
			} else {
				runningLapList.add(lap);
			}
		}

		CalculationTrack calcTrack = new CalculationTrack();
		final Trackpoint trackStartPoint = runningLapList.get(0).getPoints().get(0);
		Deque<Trackpoint> pointOfLapStack;
		CalculationTrackpointDecorator endPointOfLastLap = null;
		CalculationTrackpointDecorator currentPoint = null;
		final BreakDifference currentBreakDifference = new BreakDifference();

		for (Lap lap : runningLapList) {
			pointOfLapStack = new LinkedList<Trackpoint>(lap.getPoints());
			while (pointOfLapStack.isEmpty() == false) {
				currentPoint = calcTrackpoint(pointOfLapStack.pop(), trackStartPoint, currentBreakDifference);
				calcTrack.getTrackpoints().add(currentPoint);
				if (isStartOfBreak(currentPoint, breakLapStack)) {
					breakLapStack.pop(); // no need for further details of breakLap
					currentPoint.setBreakMarker(BreakMarker.LAST_POINT_BEFORE_BREAK);
					// start: TomTom TCX-FILE MAGIC
					pointOfLapStack.pop();
					pointOfLapStack.pop();
					addBreak(currentBreakDifference, currentPoint, pointOfLapStack.peek());
					// end: TomTom TCX-FILE MAGIC
					currentPoint = calcTrackpoint(pointOfLapStack.pop(), trackStartPoint, currentBreakDifference);
					currentPoint.setBreakMarker(BreakMarker.FIRST_POINT_AFTER_BREAK);
					calcTrack.getTrackpoints().add(currentPoint);
				}
			}

			calcTrack.getLaps().add(calcLap(currentPoint, endPointOfLastLap));
			endPointOfLastLap = currentPoint;
		}

		return calcTrack;
	}

	static class BreakDifference {
		long durationInMilliseconds;
		double distanceInMeters;
	}
}
