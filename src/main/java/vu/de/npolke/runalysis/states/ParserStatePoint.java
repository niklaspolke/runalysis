package vu.de.npolke.runalysis.states;

import javax.xml.stream.XMLStreamReader;

import vu.de.npolke.runalysis.TcxParser;
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
public class ParserStatePoint implements ParserState {

	private final TcxParser parser;

	private final ParserStateLap parentStateLap;
	private final ParserStatePointDistance statePointDistance;
	private final ParserStatePointTime statePointTime;

	private long timestampMillis;

	private double recordedDistanceMeters;

	public ParserStatePoint(final TcxParser parser, final ParserStateLap parent) {
		this.parser = parser;
		parentStateLap = parent;
		statePointDistance = new ParserStatePointDistance(parser, this);
		statePointTime = new ParserStatePointTime(parser, this);
	}

	public void setTimestampMillis(long timestampMillis) {
		this.timestampMillis = timestampMillis;
	}

	public void setRecorededDistanceMeters(double recorededDistanceMeters) {
		recordedDistanceMeters = recorededDistanceMeters;
	}

	private void reset() {
		timestampMillis = 0;
		recordedDistanceMeters = 0;
	}

	@Override
	public void handleStartElement(final XMLStreamReader xmlReader) {
		String startElement = xmlReader.getLocalName();
		if (TcxParser.ELEMENT_POINT_DISTANCE.equalsIgnoreCase(startElement)) {
			parser.changeState(statePointDistance);
		} else if (TcxParser.ELEMENT_POINT_TIME.equalsIgnoreCase(startElement)) {
			parser.changeState(statePointTime);
		}
	}

	@Override
	public void handleEndElement(final XMLStreamReader xmlReader) {
		String endElement = xmlReader.getLocalName();
		if (TcxParser.ELEMENT_POINT.equalsIgnoreCase(endElement)) {
			Trackpoint newPoint = new Trackpoint(timestampMillis, recordedDistanceMeters);
			reset();
			parentStateLap.addTrackpoint(newPoint);
			parser.changeState(parentStateLap);
		}
	}

	@Override
	public void handleCharacters(final XMLStreamReader xmlReader) {
	}
}
