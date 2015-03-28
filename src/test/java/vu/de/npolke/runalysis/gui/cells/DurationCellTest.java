package vu.de.npolke.runalysis.gui.cells;

import static org.junit.Assert.*;

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
public class DurationCellTest {

	// 1:15 min
	private static final double TIME1_SECONDS = 1 * 60 + 15;
	// 12:30 min
	private static final double TIME2_SECONDS = 12 * 60 + 30;
	// 1:30:45 h
	private static final double TIME3_SECONDS = 1 * 60 * 60 + 30 * 60 + 45;

	@Test
	public void testShortMinTime() {
		assertEquals("1:15 min", new DurationCell(TIME1_SECONDS).toString());
	}

	@Test
	public void testLongMinTime() {
		assertEquals("12:30 min", new DurationCell(TIME2_SECONDS).toString());
	}

	@Test
	public void testShortHourTime() {
		assertEquals("1:30:45 h", new DurationCell(TIME3_SECONDS).toString());
	}
}
