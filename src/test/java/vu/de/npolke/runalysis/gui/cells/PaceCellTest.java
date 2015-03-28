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
public class PaceCellTest {

	// Pace 5:30
	private static final double TIME1 = 5 * 60 + 30;
	private static final double DISTANCE1 = 1000;

	// Pace 6:00
	private static final double TIME2 = 9 * 60;
	private static final double DISTANCE2 = 1500;

	// Pace 4:15
	private static final double TIME3 = 6 * 60 + 22.5;
	private static final double DISTANCE3 = 1500;

	@Test
	public void testPace1() {
		assertEquals("5:30 min/km", new PaceCell(TIME1, DISTANCE1).toString());
	}

	@Test
	public void testPace2() {
		assertEquals("6:00 min/km", new PaceCell(TIME2, DISTANCE2).toString());
	}

	@Test
	public void testPace3() {
		assertEquals("4:15 min/km", new PaceCell(TIME3, DISTANCE3).toString());
	}
}
