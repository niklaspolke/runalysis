package vu.de.npolke.runalysis;

import java.util.ArrayList;
import java.util.Date;
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
public class BreakCorrectionLogic {

	private static int lastIndexOfList(List<?> list) {
		return list.size() - 1;
	}

	private static int findRunningLapWithBreak(final List<Lap> runningLaps, final Date timestampOfBreak) {
		// search for first lap from behind that starts before the break
		int indexOfLap = lastIndexOfList(runningLaps);
		while (runningLaps.get(indexOfLap).getStartTime().after(timestampOfBreak)) {
			indexOfLap--;
		}
		return indexOfLap;
	}

	private static void correctLaps(final List<Lap> runningLaps, final int indexOfLapWithBreak, final Date breakStartTime,
			final double breakDuration) {
		// 1. correct Lap with break
		Lap lapWithBreak = runningLaps.get(indexOfLapWithBreak);
		List<Trackpoint> pointsOfLapWithBreak = lapWithBreak.getPoints();

		// break starts one trackpoint after the break's start time
		int pointIndex = 0;
		while (pointsOfLapWithBreak.get(pointIndex).getTime().compareTo(breakStartTime) <= 0) {
			pointIndex++;
		}
		Trackpoint breakStartPoint = pointsOfLapWithBreak.get(pointIndex);
		// break ends three trackpoints later, no matter of the break duration - TomTom magic?
		Trackpoint breakEndPoint = pointsOfLapWithBreak.get(pointIndex + 3);

		// 1a set BreakMarkers before and after break
		breakStartPoint.setBreakMarker(BreakMarker.LAST_POINT_BEFORE_BREAK);
		breakEndPoint.setBreakMarker(BreakMarker.FIRST_POINT_AFTER_BREAK);

		// 1b remove Trackpoints within break
		pointsOfLapWithBreak.remove(pointIndex + 2);
		pointsOfLapWithBreak.remove(pointIndex + 1);

		// calculate distance gone within break
		double distanceDuringBreak = breakEndPoint.getDistanceMeters() - breakStartPoint.getDistanceMeters();

		// 1c correct break following Trackpoints
		for (Trackpoint point : pointsOfLapWithBreak.subList(pointIndex + 1, pointsOfLapWithBreak.size())) {
			correctDistanceOfPoint(point, distanceDuringBreak);
		}

		// 1d correct Lap's distance and duration
		lapWithBreak.setTotalTimeSeconds(lapWithBreak.getTotalTimeSeconds() - breakDuration);
		lapWithBreak.setDistanceMeters(lapWithBreak.getDistanceMeters() - distanceDuringBreak);

		// 2. correct Trackpoints of following laps
		if (indexOfLapWithBreak + 1 < runningLaps.size()) {
			for (Lap breakFollowingLap : runningLaps.subList(indexOfLapWithBreak + 1, runningLaps.size())) {
				// 2a correct all Trackpoints
				for (Trackpoint pointOfBreakFollowingLap : breakFollowingLap.getPoints()) {
					correctDistanceOfPoint(pointOfBreakFollowingLap, distanceDuringBreak);
				}
			}
		}
	}

	private static void correctDistanceOfPoint(final Trackpoint point, final double distanceToReduce) {
		point.setDistanceMeters(point.getDistanceMeters() - distanceToReduce);
	}

	public static void removeBreaksFromTrack(final Track track) {
		List<Lap> runningLaps = new ArrayList<Lap>();
		List<Lap> breakLaps = new LinkedList<Lap>();

		// divide laps into running and break laps
		for (Lap lap : track.getLaps()) {
			if (LapIntensity.RESTING.equals(lap.getIntensity())) {
				breakLaps.add(lap);
			} else {
				runningLaps.add(lap);
			}
		}

		// remove restingLaps
		for (Lap breaklap : breakLaps) {
			track.getLaps().remove(breaklap);
		}

		// correct time and distance in running laps with break(s)
		for (Lap breakLap : breakLaps) {
			// find lap with break
			int indexRunningLapWithBreak = findRunningLapWithBreak(runningLaps, breakLap.getStartTime());

			// correct time and distance
			correctLaps(runningLaps, indexRunningLapWithBreak, breakLap.getStartTime(), breakLap.getTotalTimeSeconds());
		}
	}
}
