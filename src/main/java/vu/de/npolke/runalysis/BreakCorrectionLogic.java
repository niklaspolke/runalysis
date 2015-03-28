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

	private static Lap findRunningLapWithBreak(final List<Lap> runningLaps, final Date timestampOfBreak) {
		// search for first lap from behind that starts before the break
		int indexOfLap = lastIndexOfList(runningLaps);
		while (runningLaps.get(indexOfLap).getStartTime().after(timestampOfBreak)) {
			indexOfLap--;
		}
		return runningLaps.get(indexOfLap);
	}

	private static void correctLap(final Lap lapToCorrect, final Date breakStartTime, final double breakDuration) {
		// break starts one trackpoint after the break's start time
		int pointIndex = 0;
		while (lapToCorrect.getPoints().get(pointIndex).getTime().compareTo(breakStartTime) <= 0) {
			pointIndex++;
		}
		Trackpoint breakStartPoint = lapToCorrect.getPoints().get(pointIndex);
		// break ends three trackpoints later, no matter of the break duration - TomTom magic?
		Trackpoint breakEndPoint = lapToCorrect.getPoints().get(pointIndex + 3);

		// calculate distance gone within break
		double distanceDuringBreak = breakEndPoint.getDistanceMeters() - breakStartPoint.getDistanceMeters();

		// correct lap
		lapToCorrect.setTotalTimeSeconds(lapToCorrect.getTotalTimeSeconds() - breakDuration);
		lapToCorrect.setDistanceMeters(lapToCorrect.getDistanceMeters() - distanceDuringBreak);
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
			Lap runningLapWithBreak = findRunningLapWithBreak(runningLaps, breakLap.getStartTime());

			// correct time and distance
			correctLap(runningLapWithBreak, breakLap.getStartTime(), breakLap.getTotalTimeSeconds());
		}
	}
}
