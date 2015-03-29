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
public class TrackpointTest {

	private static final double DELTA_ACCEPTED = 0.001;

	// 2015-03-24 0:17:49 GMT
	private static final Date TEST1_TIME = new Date(1427152669472l);
	private static final double TEST1_DISTANCEMETERS = 150.531;
	private static final String TEST1_STRING = "Trackpoint: (00:17:49):   151 m";

	// 1min * 60sec/min * 1000ms/sec = 60000ms
	// 1h * 60min/h * 60sec/min * 1000ms/sec = 3600000ms
	private static final Date TEST2_TIME = new Date(1427152669472l + 60000 + 3600000);
	private static final double TEST2_DISTANCEMETERS = 1190.123;
	private static final String TEST2_STRING = "Trackpoint: (01:18:49):  1190 m";
	private static final BreakMarker TEST2_MARKER = BreakMarker.LAST_POINT_BEFORE_BREAK;

	private Trackpoint testPoint;

	@Before
	public void setup() {
		testPoint = new Trackpoint();
		testPoint.setTime(TEST1_TIME);
		testPoint.setDistanceMeters(TEST1_DISTANCEMETERS);
	}

	@Test
	public void time() {
		assertEquals(TEST1_TIME, testPoint.getTime());

		testPoint.setTime(TEST2_TIME);
		assertEquals(TEST2_TIME, testPoint.getTime());
	}

	@Test
	public void distanceMeters() {
		assertEquals(TEST1_DISTANCEMETERS, testPoint.getDistanceMeters(), DELTA_ACCEPTED);

		testPoint.setDistanceMeters(TEST2_DISTANCEMETERS);
		assertEquals(TEST2_DISTANCEMETERS, testPoint.getDistanceMeters(), DELTA_ACCEPTED);
	}

	@Test
	public void breakMarker() {
		assertNull(testPoint.getBreakMarker());

		testPoint.setBreakMarker(TEST2_MARKER);
		assertEquals(TEST2_MARKER, testPoint.getBreakMarker());
	}

	@Test
	public void string() {
		assertEquals(TEST1_STRING, testPoint.toString());

		testPoint.setTime(TEST2_TIME);
		testPoint.setDistanceMeters(TEST2_DISTANCEMETERS);
		assertEquals(TEST2_STRING, testPoint.toString());
	}
}
