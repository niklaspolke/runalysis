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

	private static final long TIMESTAMP = new Date().getTime();
	private static final Double VALUE = 3.5d;

	private ChartPoints testPoints;

	@Before
	public void setup() {
		testPoints = new ChartPoints();
	}

	@Test
	public void noPoints() {
		assertEquals(0, testPoints.getTimestamps().size());
		assertEquals(0, testPoints.getValues().size());
	}

	@Test
	public void onePoint() {
		testPoints.addChartPoint(TIMESTAMP, VALUE);

		assertEquals(1, testPoints.getTimestamps().size());
		assertEquals(1, testPoints.getValues().size());

		assertTrue(testPoints.getTimestamps().contains(new Date(TIMESTAMP)));
		assertTrue(testPoints.getValues().contains(VALUE));
	}
}
