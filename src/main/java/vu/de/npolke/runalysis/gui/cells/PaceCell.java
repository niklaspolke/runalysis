package vu.de.npolke.runalysis.gui.cells;

import java.text.SimpleDateFormat;
import java.util.Date;

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
public class PaceCell {

	private final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("m:ss");

	private final long paceInMillisecondsPerKilometer;

	public PaceCell(final double durationInSeconds, final double distanceInMeters) {
		double paceInMinPerKilometer = durationInSeconds / 60 / (distanceInMeters / 1000);
		paceInMillisecondsPerKilometer = (long) (paceInMinPerKilometer * 60 * 1000);
	}

	@Override
	public String toString() {
		return TIME_FORMAT.format(new Date(paceInMillisecondsPerKilometer)) + " min/km";
	}
}
