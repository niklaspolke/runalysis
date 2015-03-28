package vu.de.npolke.runalysis;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

	private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

	private static final String FILE_NORMAL = "/Running_20150321_153454_Normal.zip";
	private static final String FILE_NORMAL_TIMESTAMP_STRING = "2015-03-21T15:34:54.000Z";
	private static Date FILE_NORMAL_TIMESTAMP;
	private static final int FILE_NORMAL_AMOUNT_OF_LAPS = 1;
	private static final int FILE_NORMAL_AMOUNT_OF_TRACKPOINTS = 69;
	private static final double FILE_NORMAL_DURATION_IN_SECS = 62;
	private static final double FILE_NORMAL_DISTANCE_IN_METERS = 165;

	private static final String FILE_LAPS = "/Running_20150321_153653_Laps.zip";
	private static final String FILE_LAPS_TIMESTAMP_STRING = "2015-03-21T15:36:53.000Z";
	private static Date FILE_LAPS_TIMESTAMP;
	private static final int FILE_LAPS_AMOUNT_OF_LAPS = 3;
	private static final int FILE_LAPS_AMOUNT_OF_TRACKPOINTS = 70;
	private static final double FILE_LAPS_DURATION_IN_SECS = 63;
	private static final double FILE_LAPS_DISTANCE_IN_METERS = 155;

	static {
		try {
			FILE_NORMAL_TIMESTAMP = (new SimpleDateFormat(TIMESTAMP_FORMAT)).parse(FILE_NORMAL_TIMESTAMP_STRING);
			FILE_LAPS_TIMESTAMP = (new SimpleDateFormat(TIMESTAMP_FORMAT)).parse(FILE_LAPS_TIMESTAMP_STRING);
		} catch (ParseException e) {
			fail();
		}
	}

	@Test
	public void testZipFileWithNormalTcxFile() {
		TcxParser parser = new TcxParser(FILE_NORMAL, getClass().getResourceAsStream(FILE_NORMAL));

		parser.readFile();
		BreakCorrectionLogic.removeBreaksFromTrack(parser.getTrack());

		assertEquals(FILE_NORMAL_TIMESTAMP, parser.getTrack().getStartTime());
		assertEquals(FILE_NORMAL_AMOUNT_OF_LAPS, parser.getTrack().getLaps().size());
		assertEquals(FILE_NORMAL_AMOUNT_OF_TRACKPOINTS, parser.getAmountOfPoints());
		assertEquals(FILE_NORMAL_DURATION_IN_SECS, parser.getTrack().getTotalTimeSeconds(), DELTA_ACCEPTED);
		assertEquals(FILE_NORMAL_DISTANCE_IN_METERS, parser.getTrack().getDistanceMeters(), DELTA_ACCEPTED);
	}

	@Test
	public void testZipFileWithLapsTcxFile() {
		TcxParser parser = new TcxParser(FILE_LAPS, getClass().getResourceAsStream(FILE_LAPS));

		parser.readFile();
		BreakCorrectionLogic.removeBreaksFromTrack(parser.getTrack());

		assertEquals(FILE_LAPS_TIMESTAMP, parser.getTrack().getStartTime());
		assertEquals(FILE_LAPS_AMOUNT_OF_LAPS, parser.getTrack().getLaps().size());
		assertEquals(FILE_LAPS_AMOUNT_OF_TRACKPOINTS, parser.getAmountOfPoints());
		assertEquals(FILE_LAPS_DURATION_IN_SECS, parser.getTrack().getTotalTimeSeconds(), DELTA_ACCEPTED);
		assertEquals(FILE_LAPS_DISTANCE_IN_METERS, parser.getTrack().getDistanceMeters(), DELTA_ACCEPTED);
	}

}
