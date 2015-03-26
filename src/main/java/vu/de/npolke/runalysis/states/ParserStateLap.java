package vu.de.npolke.runalysis.states;

import javax.xml.stream.XMLStreamReader;

import vu.de.npolke.runalysis.Lap;
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
public class ParserStateLap implements ParserState {

	private final TcxParser parser;

	private final ParserState parent;
	private final ParserStateLapDistance stateLapDistance;
	private final ParserStateLapDuration stateLapDuration;
	private final ParserStateLapIntensity stateLapIntensity;
	private final ParserStatePoint statePoint;

	private Lap lap;

	public ParserStateLap(final TcxParser parser, final ParserState parent) {
		this.parser = parser;
		this.parent = parent;
		stateLapDistance = new ParserStateLapDistance(parser, this);
		stateLapDuration = new ParserStateLapDuration(parser, this);
		stateLapIntensity = new ParserStateLapIntensity(parser, this);
		statePoint = new ParserStatePoint(parser, this);
	}

	public void setLap(final Lap lap) {
		this.lap = lap;
		stateLapDistance.setLap(lap);
		stateLapDuration.setLap(lap);
		stateLapIntensity.setLap(lap);
	}

	@Override
	public void handleStartElement(final XMLStreamReader xmlReader) {
		String startElement = xmlReader.getLocalName();
		if (TcxParser.ELEMENT_LAP_DISTANCE.equalsIgnoreCase(startElement)) {
			parser.changeState(stateLapDistance);
		} else if (TcxParser.ELEMENT_LAP_DURATION.equalsIgnoreCase(startElement)) {
			parser.changeState(stateLapDuration);
		} else if (TcxParser.ELEMENT_LAP_INTENSITY.equalsIgnoreCase(startElement)) {
			parser.changeState(stateLapIntensity);
		} else if (TcxParser.ELEMENT_POINT.equalsIgnoreCase(startElement)) {
			Trackpoint point = new Trackpoint();
			lap.addPoint(point);
			statePoint.setPoint(point);
			parser.changeState(statePoint);
		}
	}

	@Override
	public void handleEndElement(final XMLStreamReader xmlReader) {
		String endElement = xmlReader.getLocalName();
		if (TcxParser.ELEMENT_LAP.equalsIgnoreCase(endElement)) {
			parser.addLap(lap);
			parser.changeState(parent);
		}
	}

	@Override
	public void handleCharacters(final XMLStreamReader xmlReader) {
	}
}
