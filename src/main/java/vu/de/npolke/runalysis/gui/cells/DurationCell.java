package vu.de.npolke.runalysis.gui.cells;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

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
public class DurationCell {

	private static final long ONE_HOUR_IN_MILLISECONDS = 1l * 60l * 60l * 1000l;

	private final SimpleDateFormat TIME_FORMAT_SHORT = new SimpleDateFormat("m:ss");
	private final SimpleDateFormat TIME_FORMAT_LONG = new SimpleDateFormat("H:mm:ss");

	private final long durationInMilliseconds;

	public DurationCell(final double durationInSeconds) {
		durationInMilliseconds = (long) (durationInSeconds * 1000);

		TIME_FORMAT_SHORT.setTimeZone(TimeZone.getTimeZone("UTC"));
		TIME_FORMAT_LONG.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	@Override
	public String toString() {
		String stringRepresentation;
		if (durationInMilliseconds < ONE_HOUR_IN_MILLISECONDS) {
			stringRepresentation = TIME_FORMAT_SHORT.format(new Date(durationInMilliseconds)) + " min";
		} else {
			stringRepresentation = TIME_FORMAT_LONG.format(new Date(durationInMilliseconds)) + " h";
		}
		return stringRepresentation;
	}
}
