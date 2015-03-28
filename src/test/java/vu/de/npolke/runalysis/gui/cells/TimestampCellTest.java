package vu.de.npolke.runalysis.gui.cells;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;

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
public class TimestampCellTest {

	private static final String TIMESTAMP1 = "2015-03-24 15:15";

	@Test
	public void testTimestamp() throws ParseException {
		SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		timestampFormat.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
		Date timestamp = timestampFormat.parse(TIMESTAMP1);
		assertEquals(TIMESTAMP1, new TimestampCell(timestamp).toString());
	}
}
