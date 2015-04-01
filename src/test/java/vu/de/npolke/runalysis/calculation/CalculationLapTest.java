package vu.de.npolke.runalysis.calculation;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
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
public class CalculationLapTest {

	private static final double DELTA_ACCEPTED = 0.001;

	private static final long RUN_DURATION_IN_MILLISECONDS = 123456789123l;
	private static final double RUN_DISTANCE_IN_METERS = 150.531;

	private static CalculationLap testLap;

	@BeforeClass
	public static void setup() {
		testLap = new CalculationLap(RUN_DURATION_IN_MILLISECONDS, RUN_DISTANCE_IN_METERS);
	}

	@Test
	public void runDuration() {
		assertEquals(RUN_DURATION_IN_MILLISECONDS, testLap.getRunDurationInMilliseconds());
	}

	@Test
	public void runDdistance() {
		assertEquals(RUN_DISTANCE_IN_METERS, testLap.getRunDistanceInMeters(), DELTA_ACCEPTED);
	}
}
