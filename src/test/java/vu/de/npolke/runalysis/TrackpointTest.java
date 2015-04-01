package vu.de.npolke.runalysis;

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
public class TrackpointTest {

	private static final double DELTA_ACCEPTED = 0.001;

	// 2015-03-24 0:17:49 GMT
	private static final long TIMESTAMP_MILLIS = 123456789123l;
	private static final double DISTANCE_METERS = 150.531;

	private static Trackpoint testPoint;

	@BeforeClass
	public static void setup() {
		testPoint = new Trackpoint(TIMESTAMP_MILLIS, DISTANCE_METERS);
	}

	@Test
	public void timestamp() {
		assertEquals(TIMESTAMP_MILLIS, testPoint.getTimestampMillis());
	}

	@Test
	public void distance() {
		assertEquals(DISTANCE_METERS, testPoint.getRecordedDistanceMeters(), DELTA_ACCEPTED);
	}
}
