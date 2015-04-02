package vu.de.npolke.runalysis;

import static java.util.concurrent.TimeUnit.*;
import static javax.xml.stream.XMLStreamConstants.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipInputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import vu.de.npolke.runalysis.calculation.CalculationTrack;
import vu.de.npolke.runalysis.states.ParserState;
import vu.de.npolke.runalysis.states.ParserStateDefault;

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
public class TcxParser {

	public static final String USAGE = "usage: " + TcxParser.class.getSimpleName() + " <filename>";

	public static final String FILE_ENDING_ZIP = ".ZIP";

	public static final String ELEMENT_ID = "Id";
	public static final String ELEMENT_LAP = "Lap";
	public static final String ELEMENT_LAP_PROPERTY_STARTTIME = "StartTime";
	public static final String ELEMENT_LAP_DISTANCE = "DistanceMeters";
	public static final String ELEMENT_LAP_DURATION = "TotalTimeSeconds";
	public static final String ELEMENT_LAP_INTENSITY = "Intensity";
	public static final String ELEMENT_POINT = "Trackpoint";
	public static final String ELEMENT_POINT_DISTANCE = "DistanceMeters";
	public static final String ELEMENT_POINT_TIME = "Time";

	private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	private static final SimpleDateFormat TIMESTAMP_FORMATTER = new SimpleDateFormat(TIMESTAMP_FORMAT);

	private static final String TIMESTAMP_FORMAT_SHORT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	private static final SimpleDateFormat TIMESTAMP_FORMATTER_SHORT = new SimpleDateFormat(TIMESTAMP_FORMAT_SHORT);

	private final String filename;
	private InputStream inputStream;

	private ParserState state;

	private Track track;

	public TcxParser(final String filename) {
		this.filename = filename;
		track = new Track();
	}

	public TcxParser(final String filename, final InputStream inputStream) {
		this(filename);
		this.inputStream = inputStream;
	}

	public String getFilename() {
		return filename;
	}

	public void changeState(final ParserState newState) {
		state = newState;
	}

	public Track getTrack() {
		return track;
	}

	public int getAmountOfPoints() {
		int amountOfPoints = 0;
		for (Lap lap : track.getLaps()) {
			amountOfPoints += lap.getPoints().size();
		}
		return amountOfPoints;
	}

	public void readFile() {
		try {
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLStreamReader xmlReader;
			InputStream inputStream;

			if (this.inputStream != null) {
				inputStream = this.inputStream;
			} else {
				inputStream = new FileInputStream(filename);
			}

			if (filename.toUpperCase().endsWith(FILE_ENDING_ZIP)) {
				ZipInputStream zipInputStream = new ZipInputStream(inputStream);
				// read the first entry within the zip file
				zipInputStream.getNextEntry();
				xmlReader = factory.createXMLStreamReader(zipInputStream);
			} else {
				xmlReader = factory.createXMLStreamReader(inputStream);
			}

			state = new ParserStateDefault(this);

			while (xmlReader.hasNext()) {
				switch (xmlReader.getEventType()) {
				case END_DOCUMENT:
					xmlReader.close();
					break;

				case START_ELEMENT:
					state.handleStartElement(xmlReader);
					break;

				case CHARACTERS:
					state.handleCharacters(xmlReader);
					break;

				case END_ELEMENT:
					state.handleEndElement(xmlReader);
					break;
				}
				xmlReader.next();
			}
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound: " + filename);
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// TODO: correct to timestampMilliseconds/long ?
	public Date extractTimestamp(final String timestampText) {
		Date timestamp = null;
		try {
			timestamp = TIMESTAMP_FORMATTER.parse(timestampText);
		} catch (ParseException e) {
		}
		if (timestamp == null) {
			try {
				timestamp = TIMESTAMP_FORMATTER_SHORT.parse(timestampText);
			} catch (ParseException e) {
			}
		}
		return timestamp;
	}

	public static String formatAsDuration(final double secondsAsDouble) {
		long seconds = (long) secondsAsDouble;
		long hours = SECONDS.toHours(seconds);
		long minutes = SECONDS.toMinutes(seconds) - HOURS.toMinutes(hours);
		seconds = seconds - HOURS.toSeconds(hours) - MINUTES.toSeconds(minutes);
		return String.format("%1d:%02d:%02d", hours, minutes, seconds);
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println(USAGE);
		} else {
			TcxParser parser = new TcxParser(args[0]);
			System.out.println("Reading file \"" + args[0] + "\" ...");
			parser.readFile();
			System.out.println("Reading file \"" + args[0] + "\" ... done.");
			System.out.println();
			System.out.println("track duration: " + formatAsDuration(parser.getTrack().getTotalTimeSeconds()) + " h");
			System.out.println("track distance: " + String.format("%7.0f", parser.getTrack().getDistanceMeters()) + " m");
			System.out.println("        # laps: " + String.format("%7d", parser.getTrack().getLaps().size()));
			System.out.println(" # trackpoints: " + String.format("%7d", parser.getAmountOfPoints()));
			System.out.println();
			System.out.println("Correcting breaks...");
			CalculationTrack track = BreakCorrectionLogic.removeBreaksFromTrack(parser.getTrack());
			System.out.println("Correcting breaks... done.");
			System.out.println();
			System.out.println("track duration: " + formatAsDuration(track.getRunDurationInSeconds()) + " h");
			System.out.println("track distance: " + String.format("%7.0f", track.getRunDistanceInMeters()) + " m");
			System.out.println("        # laps: " + String.format("%7d", track.getLaps().size()));
			System.out.println(" # trackpoints: " + String.format("%7d", track.getTrackpoints().size()));
		}
	}
}
