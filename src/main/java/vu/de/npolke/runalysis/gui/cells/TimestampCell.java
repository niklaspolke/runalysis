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
public class TimestampCell {

	private final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	private final Date timestamp;

	public TimestampCell(final Date timestamp) {
		this.timestamp = (Date) timestamp.clone();

		TIMESTAMP_FORMAT.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
	}

	@Override
	public String toString() {
		return TIMESTAMP_FORMAT.format(timestamp);
	}
}
