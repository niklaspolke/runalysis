package vu.de.npolke.runalysis.calculation;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

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
public class CalculationTrackTest {

	private static final double DELTA_ACCEPTED = 0.001;

	private static final long START_TIME = 5555;
	private static final CalculationTrackpointDecorator TRACKPOINT_1 = new CalculationTrackpointDecorator(new Trackpoint(START_TIME, 123));
	private static final CalculationTrackpointDecorator TRACKPOINT_2 = new CalculationTrackpointDecorator(new Trackpoint(6666, 234));

	private static final long DURATION_1 = 112;
	private static final long DURATION_2 = 223;
	private static final long DURATION_3 = 334;
	private static final double DISTANCE_1 = 22.3;
	private static final double DISTANCE_2 = 33.4;
	private static final double DISTANCE_3 = 44.5;
	private static final CalculationLap LAP_1 = new CalculationLap(DURATION_1, DISTANCE_1);
	private static final CalculationLap LAP_2 = new CalculationLap(DURATION_2, DISTANCE_2);
	private static final CalculationLap LAP_3 = new CalculationLap(DURATION_3, DISTANCE_3);

	private static CalculationTrack emptyTestTrack, testTrack;

	@BeforeClass
	public static void setup() {
		emptyTestTrack = new CalculationTrack();

		testTrack = new CalculationTrack();
		testTrack.getTrackpoints().add(TRACKPOINT_1);
		testTrack.getTrackpoints().add(TRACKPOINT_2);
		testTrack.getLaps().add(LAP_1);
		testTrack.getLaps().add(LAP_2);
		testTrack.getLaps().add(LAP_3);
	}

	@Test
	public void trackpoints() {
		assertEquals(0, emptyTestTrack.getTrackpoints().size());

		assertEquals(2, testTrack.getTrackpoints().size());
		assertTrue(testTrack.getTrackpoints().contains(TRACKPOINT_1));
		assertTrue(testTrack.getTrackpoints().contains(TRACKPOINT_2));
	}

	@Test
	public void laps() {
		assertEquals(0, emptyTestTrack.getLaps().size());

		assertEquals(3, testTrack.getLaps().size());
		assertTrue(testTrack.getLaps().contains(LAP_1));
		assertTrue(testTrack.getLaps().contains(LAP_2));
		assertTrue(testTrack.getLaps().contains(LAP_3));
	}

	@Test
	public void startTimestamp() {
		assertEquals(0, emptyTestTrack.getStartTimestamp());

		assertEquals(START_TIME, testTrack.getStartTimestamp());
	}

	@Test
	public void runDuration() {
		assertEquals(0, emptyTestTrack.getRunDurationInMilliseconds());

		assertEquals(DURATION_1 + DURATION_2 + DURATION_3, testTrack.getRunDurationInMilliseconds());
	}

	@Test
	public void runDistance() {
		assertEquals(0, emptyTestTrack.getRunDistanceInMeters(), DELTA_ACCEPTED);

		assertEquals(DISTANCE_1 + DISTANCE_2 + DISTANCE_3, testTrack.getRunDistanceInMeters(), DELTA_ACCEPTED);
	}
}
