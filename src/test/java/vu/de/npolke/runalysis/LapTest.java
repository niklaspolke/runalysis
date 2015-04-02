package vu.de.npolke.runalysis;

import static org.junit.Assert.*;

import java.util.LinkedList;

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
public class LapTest {

	private static final double DELTA_ACCEPTED = 0.001;

	// 2015-03-24 0:17:49 GMT
	private static final long TEST1_STARTTIMESTAMPMILLIS = 1427152669472l;
	private static final double TEST1_TOTALTIMESECONDS = 123.4;
	private static final double TEST1_DISTANCEMETERS = 34.5;
	private static final LapIntensity TEST1_INTENSITY = LapIntensity.ACTIVE;

	// 1min * 60sec/min * 1000ms/sec = 60000ms
	// 1h * 60min/h * 60sec/min * 1000ms/sec = 3600000ms
	private static final long TEST2_STARTTIMESTAMPMILLIS = 1427152669472l + 60000l + 3600000l;
	private static final double TEST2_TOTALTIMESECONDS = 1654.4;
	private static final double TEST2_DISTANCEMETERS = 8888.5;
	private static final LapIntensity TEST2_INTENSITY = LapIntensity.RESTING;
	private static final Trackpoint TEST2_POINT1 = new Trackpoint(123, 5);
	private static final Trackpoint TEST2_POINT2 = new Trackpoint(133, 10);

	private static final Trackpoint POINT3 = new Trackpoint(155, 15);

	private Lap testLap1;
	private Lap testLap2;

	@Before
	public void setup() {
		testLap1 = new Lap(TEST1_STARTTIMESTAMPMILLIS, TEST1_TOTALTIMESECONDS, TEST1_DISTANCEMETERS, TEST1_INTENSITY, null);
		LinkedList<Trackpoint> points = new LinkedList<Trackpoint>();
		points.add(TEST2_POINT1);
		points.add(TEST2_POINT2);
		testLap2 = new Lap(TEST2_STARTTIMESTAMPMILLIS, TEST2_TOTALTIMESECONDS, TEST2_DISTANCEMETERS, TEST2_INTENSITY, points);
	}

	@Test
	public void startTime() {
		assertEquals(TEST1_STARTTIMESTAMPMILLIS, testLap1.getStartTimestampMillis());

		assertEquals(TEST2_STARTTIMESTAMPMILLIS, testLap2.getStartTimestampMillis());
	}

	@Test
	public void recordedTotalTimeSeconds() {
		assertEquals(TEST1_TOTALTIMESECONDS, testLap1.getRecordedTotalTimeSeconds(), DELTA_ACCEPTED);

		assertEquals(TEST2_TOTALTIMESECONDS, testLap2.getRecordedTotalTimeSeconds(), DELTA_ACCEPTED);
	}

	@Test
	public void recordedDistanceMeters() {
		assertEquals(TEST1_DISTANCEMETERS, testLap1.getRecordedDistanceMeters(), DELTA_ACCEPTED);

		assertEquals(TEST2_DISTANCEMETERS, testLap2.getRecordedDistanceMeters(), DELTA_ACCEPTED);
	}

	@Test
	public void intensity() {
		assertEquals(TEST1_INTENSITY, testLap1.getIntensity());

		assertEquals(TEST2_INTENSITY, testLap2.getIntensity());
	}

	@Test
	public void points() {
		assertTrue(testLap1.getPoints().isEmpty());

		assertEquals(2, testLap2.getPoints().size());
		assertTrue(testLap2.getPoints().contains(TEST2_POINT1));
		assertTrue(testLap2.getPoints().contains(TEST2_POINT2));
		assertFalse(testLap2.getPoints().contains(POINT3));

		testLap1.getPoints().add(POINT3);
		testLap2.getPoints().add(POINT3);

		assertTrue(testLap1.getPoints().isEmpty());

		assertEquals(2, testLap2.getPoints().size());
		assertTrue(testLap2.getPoints().contains(TEST2_POINT1));
		assertTrue(testLap2.getPoints().contains(TEST2_POINT2));
		assertFalse(testLap2.getPoints().contains(POINT3));
	}
}
