package vu.de.npolke.runalysis;

import static java.util.concurrent.TimeUnit.*;
import static javax.xml.stream.XMLStreamConstants.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

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

	private static final String ELEMENT_DISTANCE = "DistanceMeters";
	private static final String ELEMENT_TIME = "TotalTimeSeconds";

	private final String filename;

	private double distanceInMeters;

	private double durationInSeconds;

	public TcxParser(final String filename) {
		this.filename = filename;
		distanceInMeters = 0.0;
		durationInSeconds = 0.0;
	}

	public String getFilename() {
		return filename;
	}

	public double getDistance() {
		return distanceInMeters;
	}

	public double getDuration() {
		return durationInSeconds;
	}

	public void readFile() {
		try {
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLStreamReader xmlReader = factory.createXMLStreamReader(new FileInputStream(filename));

			boolean withinDistance = false;
			boolean withinTotalTimeSeconds = false;

			String localname, text;

			while (xmlReader.hasNext()) {
				switch (xmlReader.getEventType()) {
				case END_DOCUMENT:
					xmlReader.close();
					break;

				case START_ELEMENT:
					localname = xmlReader.getLocalName();
					if (ELEMENT_DISTANCE.equalsIgnoreCase(localname)) {
						withinDistance = true;
					} else if (ELEMENT_TIME.equalsIgnoreCase(localname)) {
						withinTotalTimeSeconds = true;
					}
					break;

				case CHARACTERS:
					text = xmlReader.getText();
					if (withinDistance) {
						distanceInMeters = Double.parseDouble(text);
					} else if (withinTotalTimeSeconds) {
						durationInSeconds += Double.parseDouble(text);
					}
					break;

				case END_ELEMENT:
					localname = xmlReader.getLocalName();
					if (ELEMENT_DISTANCE.equalsIgnoreCase(localname)) {
						withinDistance = false;
					} else if (ELEMENT_TIME.equalsIgnoreCase(localname)) {
						withinTotalTimeSeconds = false;
					}
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
			System.out.println("track duration: " + formatAsDuration(parser.getDuration()) + " h");
			System.out.println("track distance: " + String.format("%7.0f", parser.getDistance()) + " m");
		}
	}
}
