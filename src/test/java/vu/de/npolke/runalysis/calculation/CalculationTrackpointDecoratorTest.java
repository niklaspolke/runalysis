package vu.de.npolke.runalysis.calculation;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import vu.de.npolke.runalysis.BreakMarker;
import vu.de.npolke.runalysis.Trackpoint;

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
public class CalculationTrackpointDecoratorTest {

	private static final double DELTA_ACCEPTED = 0.001;

	// 2015-03-24 0:17:49 GMT
	private static final long TIMESTAMP_MILLIS = 123456789123l;
	private static final double DISTANCE_METERS = 150.531;

	private static final long TEST1_RUNDURATION = 456456456l;
	private static final double TEST1_RUNDISTANCE = 123.456;

	private static final long TEST2_RUNDURATION = 789789789l;
	private static final double TEST2_RUNDISTANCE = 654.321;
	private static final BreakMarker TEST2_BREAKMARKER = BreakMarker.FIRST_POINT_AFTER_BREAK;

	private CalculationTrackpointDecorator testPoint;

	@Before
	public void setup() {
		testPoint = new CalculationTrackpointDecorator(new Trackpoint(TIMESTAMP_MILLIS, DISTANCE_METERS));
		testPoint.setRunDurationInMilliseconds(TEST1_RUNDURATION);
		testPoint.setRunDistanceInMeters(TEST1_RUNDISTANCE);
	}

	@Test
	public void timestamp() {
		assertEquals(TIMESTAMP_MILLIS, testPoint.getTimestampMillis());
	}

	@Test
	public void distance() {
		assertEquals(DISTANCE_METERS, testPoint.getRecordedDistanceMeters(), DELTA_ACCEPTED);
	}

	@Test
	public void runDuration() {
		assertEquals(TEST1_RUNDURATION, testPoint.getRunDurationInMilliseconds());

		testPoint.setRunDurationInMilliseconds(TEST2_RUNDURATION);
		assertEquals(TEST2_RUNDURATION, testPoint.getRunDurationInMilliseconds());
	}

	@Test
	public void runDistance() {
		assertEquals(TEST1_RUNDISTANCE, testPoint.getRunDistanceInMeters(), DELTA_ACCEPTED);

		testPoint.setRunDistanceInMeters(TEST2_RUNDISTANCE);
		assertEquals(TEST2_RUNDISTANCE, testPoint.getRunDistanceInMeters(), DELTA_ACCEPTED);
	}

	@Test
	public void breakMarker() {
		assertEquals(null, testPoint.getBreakMarker());

		testPoint.setBreakMarker(TEST2_BREAKMARKER);
		assertEquals(TEST2_BREAKMARKER, testPoint.getBreakMarker());
	}
}
