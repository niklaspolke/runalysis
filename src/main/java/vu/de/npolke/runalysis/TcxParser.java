package vu.de.npolke.runalysis;

import static java.util.concurrent.TimeUnit.*;
import static javax.xml.stream.XMLStreamConstants.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

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

	private final String filename;

	private ParserState state;

	private List<Lap> laps;

	public TcxParser(final String filename) {
		this.filename = filename;
		laps = new LinkedList<Lap>();
	}

	public String getFilename() {
		return filename;
	}

	public void changeState(final ParserState newState) {
		state = newState;
	}

	public List<Lap> getLaps() {
		return laps;
	}

	public void setLaps(final List<Lap> laps) {
		this.laps = laps;
	}

	public void addLap(final Lap newLap) {
		laps.add(newLap);
	}

	public double getDistanceInMeters() {
		double distanceInMeters = 0;
		for (Lap lap : laps) {
			distanceInMeters += lap.getDistanceMeters();
		}
		return distanceInMeters;
	}

	public double getDurationInSeconds() {
		double durationInSeconds = 0;
		for (Lap lap : laps) {
			durationInSeconds += lap.getTotalTimeSeconds();
		}
		return durationInSeconds;
	}

	public int getAmountOfLaps() {
		return laps.size();
	}

	public int getAmountOfPoints() {
		int amountOfPoints = 0;
		for (Lap lap : laps) {
			amountOfPoints += lap.getPoints().size();
		}
		return amountOfPoints;
	}

	public void readFile() {
		try {
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLStreamReader xmlReader = factory.createXMLStreamReader(new FileInputStream(filename));

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
		}
	}

	public static Date extractTimestamp(final String timestampText) {
		Date timestamp = null;
		try {
			timestamp = TIMESTAMP_FORMATTER.parse(timestampText);
		} catch (ParseException e) {
			e.printStackTrace();
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
			System.out.println("track duration: " + formatAsDuration(parser.getDurationInSeconds()) + " h");
			System.out.println("track distance: " + String.format("%7.0f", parser.getDistanceInMeters()) + " m");
			System.out.println("        # laps: " + String.format("%7d", parser.getAmountOfLaps()));
			System.out.println(" # trackpoints: " + String.format("%7d", parser.getAmountOfPoints()));
			System.out.println();
			System.out.println("Correcting breaks...");
			BreakCorrectionLogic.removeBreaksFromTrack(parser.getLaps());
			System.out.println("Correcting breaks... done.");
			System.out.println();
			System.out.println("track duration: " + formatAsDuration(parser.getDurationInSeconds()) + " h");
			System.out.println("track distance: " + String.format("%7.0f", parser.getDistanceInMeters()) + " m");
			System.out.println("        # laps: " + String.format("%7d", parser.getAmountOfLaps()));
			System.out.println(" # trackpoints: " + String.format("%7d", parser.getAmountOfPoints()));
		}
	}
}
