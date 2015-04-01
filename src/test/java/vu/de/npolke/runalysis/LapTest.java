package vu.de.npolke.runalysis;

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
public class LapTest {

	private static final double DELTA_ACCEPTED = 0.001;

	// 2015-03-24 0:17:49 GMT
	private static final Date TEST1_STARTTIME = new Date(1427152669472l);
	private static final double TEST1_TOTALTIMESECONDS = 123.4;
	private static final double TEST1_DISTANCEMETERS = 34.5;
	private static final LapIntensity TEST1_INTENSITY = LapIntensity.ACTIVE;
	private static final String TEST1_STRING = "Lap (00:17:49, ACTIVE):   123 secs    35 m";

	// 1min * 60sec/min * 1000ms/sec = 60000ms
	// 1h * 60min/h * 60sec/min * 1000ms/sec = 3600000ms
	private static final Date TEST2_STARTTIME = new Date(1427152669472l + 60000 + 3600000);
	private static final double TEST2_TOTALTIMESECONDS = 1654.4;
	private static final double TEST2_DISTANCEMETERS = 8888.5;
	private static final LapIntensity TEST2_INTENSITY = LapIntensity.RESTING;
	private static final String TEST2_STRING = "Lap (01:18:49, RESTING):  1654 secs  8889 m";

	private Lap testLap;

	@Before
	public void setup() {
		testLap = new Lap();
		testLap.setStartTime(TEST1_STARTTIME);
		testLap.setTotalTimeSeconds(TEST1_TOTALTIMESECONDS);
		testLap.setDistanceMeters(TEST1_DISTANCEMETERS);
		testLap.setIntensity(TEST1_INTENSITY);
	}

	@Test
	public void startTime() {
		assertEquals(TEST1_STARTTIME, testLap.getStartTime());

		testLap.setStartTime(TEST2_STARTTIME);
		assertEquals(TEST2_STARTTIME, testLap.getStartTime());
	}

	@Test
	public void totalTimeSeconds() {
		assertEquals(TEST1_TOTALTIMESECONDS, testLap.getTotalTimeSeconds(), DELTA_ACCEPTED);

		testLap.setTotalTimeSeconds(TEST2_TOTALTIMESECONDS);
		assertEquals(TEST2_TOTALTIMESECONDS, testLap.getTotalTimeSeconds(), DELTA_ACCEPTED);
	}

	@Test
	public void distanceMeters() {
		assertEquals(TEST1_DISTANCEMETERS, testLap.getDistanceMeters(), DELTA_ACCEPTED);

		testLap.setDistanceMeters(TEST2_DISTANCEMETERS);
		assertEquals(TEST2_DISTANCEMETERS, testLap.getDistanceMeters(), DELTA_ACCEPTED);
	}

	@Test
	public void intensity() {
		assertEquals(TEST1_INTENSITY, testLap.getIntensity());

		testLap.setIntensity(TEST2_INTENSITY);
		assertEquals(TEST2_INTENSITY, testLap.getIntensity());
	}

	@Test
	public void points() {
		assertTrue(testLap.getPoints().isEmpty());

		Trackpoint point1 = new Trackpoint(123, 10);
		Trackpoint point2 = new Trackpoint(234, 20);
		testLap.addPoint(point1);
		testLap.addPoint(point2);

		assertEquals(2, testLap.getPoints().size());
		assertEquals(point1, testLap.getPoints().get(0));
		assertEquals(point2, testLap.getPoints().get(1));
	}

	@Test
	public void string() {
		assertEquals(TEST1_STRING, testLap.toString());

		testLap.setStartTime(TEST2_STARTTIME);
		testLap.setTotalTimeSeconds(TEST2_TOTALTIMESECONDS);
		testLap.setDistanceMeters(TEST2_DISTANCEMETERS);
		testLap.setIntensity(TEST2_INTENSITY);
		assertEquals(TEST2_STRING, testLap.toString());
	}
}
