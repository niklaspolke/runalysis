package vu.de.npolke.runalysis.gui;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
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
public class ChartPointsTest {

	private static final Date TIMESTAMP = new Date();
	private static final Double VALUE = 3.5d;

	private ChartPoints points;

	@Before
	public void setup() {
		points = new ChartPoints();
	}

	@Test
	public void noPoints() {
		assertEquals(0, points.getTimestamps().size());
		assertEquals(0, points.getValues().size());
	}

	@Test
	public void onePoint() {
		points.addChartPoint(TIMESTAMP, VALUE);

		assertEquals(1, points.getTimestamps().size());
		assertEquals(1, points.getValues().size());

		assertTrue(points.getTimestamps().contains(TIMESTAMP));
		assertTrue(points.getValues().contains(VALUE));
	}
}
