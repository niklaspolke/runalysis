package vu.de.npolke.runalysis;

import static org.junit.Assert.*;

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
public class TcxParserTest {

	private static final double DELTA_ACCEPTED = 0.5;

	private static final String FILE_NORMAL = "/Running_20150321_153454_Normal.zip";
	private static final int FILE_NORMAL_AMOUNT_OF_LAPS = 1;
	private static final int FILE_NORMAL_AMOUNT_OF_TRACKPOINTS = 69;
	private static final double FILE_NORMAL_DURATION_IN_SECS = 62;
	private static final double FILE_NORMAL_DISTANCE_IN_METERS = 165;

	private static final String FILE_LAPS = "/Running_20150321_153653_Laps.zip";
	private static final int FILE_LAPS_AMOUNT_OF_LAPS = 3;
	private static final int FILE_LAPS_AMOUNT_OF_TRACKPOINTS = 70;
	private static final double FILE_LAPS_DURATION_IN_SECS = 63;
	private static final double FILE_LAPS_DISTANCE_IN_METERS = 155;

	@Test
	public void testZipFileWithNormalTcxFile() {
		TcxParser parser = new TcxParser(FILE_NORMAL, getClass().getResourceAsStream(FILE_NORMAL));

		parser.readFile();
		BreakCorrectionLogic.removeBreaksFromTrack(parser.getLaps());

		assertEquals(FILE_NORMAL_AMOUNT_OF_LAPS, parser.getAmountOfLaps());
		assertEquals(FILE_NORMAL_AMOUNT_OF_TRACKPOINTS, parser.getAmountOfPoints());
		assertEquals(FILE_NORMAL_DURATION_IN_SECS, parser.getDurationInSeconds(), DELTA_ACCEPTED);
		assertEquals(FILE_NORMAL_DISTANCE_IN_METERS, parser.getDistanceInMeters(), DELTA_ACCEPTED);
	}

	@Test
	public void testZipFileWithLapsTcxFile() {
		TcxParser parser = new TcxParser(FILE_LAPS, getClass().getResourceAsStream(FILE_LAPS));

		parser.readFile();
		BreakCorrectionLogic.removeBreaksFromTrack(parser.getLaps());

		assertEquals(FILE_LAPS_AMOUNT_OF_LAPS, parser.getAmountOfLaps());
		assertEquals(FILE_LAPS_AMOUNT_OF_TRACKPOINTS, parser.getAmountOfPoints());
		assertEquals(FILE_LAPS_DURATION_IN_SECS, parser.getDurationInSeconds(), DELTA_ACCEPTED);
		assertEquals(FILE_LAPS_DISTANCE_IN_METERS, parser.getDistanceInMeters(), DELTA_ACCEPTED);
	}

}
