package vu.de.npolke.runalysis;

import vu.de.npolke.runalysis.gui.ChartPoints;

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
public class PaceCalculator {

	public static ChartPoints calculatePace(final Track track, final int timeIntervalInSeconds) {
		ChartPoints chartPoints = new ChartPoints();

		Trackpoint predecessorPoint = null;
		long timeDifferenceInMilliseconds;
		double distanceDifferenceInMeters;
		double paceInMinPerKilometer;

		for (Lap lap : track.getLaps()) {
			for (Trackpoint actualPoint : lap.getPoints()) {
				if (predecessorPoint == null || BreakMarker.FIRST_POINT_AFTER_BREAK.equals(actualPoint.getBreakMarker())) {
					predecessorPoint = actualPoint;
				} else {
					timeDifferenceInMilliseconds = actualPoint.getTime().getTime() - predecessorPoint.getTime().getTime();

					// do not take every Trackpoint to get a less chaotic diagram
					if (timeDifferenceInMilliseconds >= timeIntervalInSeconds * 1000
							|| BreakMarker.LAST_POINT_BEFORE_BREAK.equals(actualPoint.getBreakMarker())) {
						distanceDifferenceInMeters = actualPoint.getDistanceMeters() - predecessorPoint.getDistanceMeters();

						paceInMinPerKilometer = timeDifferenceInMilliseconds / (distanceDifferenceInMeters / 1000d) / 60000d;

						chartPoints.addChartPoint(actualPoint.getTime(), paceInMinPerKilometer);

						predecessorPoint = actualPoint;
					}
				}
			}
		}

		return chartPoints;
	}
}
