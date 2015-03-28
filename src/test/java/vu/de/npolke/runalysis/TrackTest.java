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
public class TrackTest {

	private static final double DELTA_ACCEPTED = 0.001;

	// 2015-03-24 0:17:49 GMT
	private static final Date TEST1_STARTTIME = new Date(1427152669472l);
	private static final double TEST1_LAP1_TOTALTIMESECONDS = 123.4;
	private static final double TEST1_LAP1_DISTANCEMETERS = 34.5;
	private static final String TEST1_STRING = "Track (2015-03-24 00:17): 0:02:03 h - 0.035 km - 1 lap(s)";

	// 1min * 60sec/min * 1000ms/sec = 60000ms
	// 1h * 60min/h * 60sec/min * 1000ms/sec = 3600000ms
	private static final Date TEST2_STARTTIME = new Date(1427152669472l + 60000 + 3600000);
	// 1h * 60min/h * 60sec/min + 15min * 60sec/min + 23sec - 2min * 60sec/min - 3sec
	private static final double TEST2_LAP2_TOTALTIMESECONDS = 1 * 60 * 60 + 15 * 60 + 23 - TEST1_LAP1_TOTALTIMESECONDS;
	private static final double TEST2_LAP2_DISTANCEMETERS = 15122.5 - TEST1_LAP1_DISTANCEMETERS;
	private static final String TEST2_STRING = "Track (2015-03-24 01:18): 1:15:23 h - 15.123 km - 2 lap(s)";

	private Track testTrack;
	private Track testTrack2;

	@Before
	public void setup() {
		testTrack = new Track();
		testTrack.setStartTime(TEST1_STARTTIME);
		Lap lap = new Lap();
		lap.setDistanceMeters(TEST1_LAP1_DISTANCEMETERS);
		lap.setTotalTimeSeconds(TEST1_LAP1_TOTALTIMESECONDS);
		testTrack.addLap(lap);

		testTrack2 = new Track();
		testTrack2.setStartTime(TEST2_STARTTIME);
		Lap lap2 = new Lap();
		lap2.setDistanceMeters(TEST2_LAP2_DISTANCEMETERS);
		lap2.setTotalTimeSeconds(TEST2_LAP2_TOTALTIMESECONDS);
		testTrack2.addLap(lap);
		testTrack2.addLap(lap2);
	}

	@Test
	public void startTime() {
		assertEquals(TEST1_STARTTIME, testTrack.getStartTime());

		assertEquals(TEST2_STARTTIME, testTrack2.getStartTime());
	}

	@Test
	public void totalTimeSeconds() {
		assertEquals(TEST1_LAP1_TOTALTIMESECONDS, testTrack.getTotalTimeSeconds(), DELTA_ACCEPTED);

		assertEquals(TEST1_LAP1_TOTALTIMESECONDS + TEST2_LAP2_TOTALTIMESECONDS, testTrack2.getTotalTimeSeconds(), DELTA_ACCEPTED);
	}

	@Test
	public void distanceMeters() {
		assertEquals(TEST1_LAP1_DISTANCEMETERS, testTrack.getDistanceMeters(), DELTA_ACCEPTED);

		assertEquals(TEST1_LAP1_DISTANCEMETERS + TEST2_LAP2_DISTANCEMETERS, testTrack2.getDistanceMeters(), DELTA_ACCEPTED);
	}

	@Test
	public void string() {
		assertEquals(TEST1_STRING, testTrack.toString());

		assertEquals(TEST2_STRING, testTrack2.toString());
	}
}
