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
public class DistanceCellTest {

	// 0.123
	private static final double DISTANCE1_METERS = 123;
	// 1.234
	private static final double DISTANCE2_METERS = 1234;
	// 12.345
	private static final double DISTANCE3_METERS = 12345;

	@Test
	public void testMeters() {
		assertEquals("0.123 km", new DistanceCell(DISTANCE1_METERS).toString());
	}

	@Test
	public void testShortKilometers() {
		assertEquals("1.234 km", new DistanceCell(DISTANCE2_METERS).toString());
	}

	@Test
	public void testLongKilometers() {
		assertEquals("12.345 km", new DistanceCell(DISTANCE3_METERS).toString());
	}
}
