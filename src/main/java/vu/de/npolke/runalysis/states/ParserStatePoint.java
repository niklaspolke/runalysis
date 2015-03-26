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

	private final ParserState parent;
	private final ParserStatePointDistance statePointDistance;
	private final ParserStatePointTime statePointTime;

	public ParserStatePoint(final TcxParser parser, final ParserState parent) {
		this.parser = parser;
		this.parent = parent;
		statePointDistance = new ParserStatePointDistance(parser, this);
		statePointTime = new ParserStatePointTime(parser, this);
	}

	public void setPoint(final Trackpoint point) {
		statePointDistance.setPoint(point);
		statePointTime.setPoint(point);
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
			parser.changeState(parent);
		}
	}

	@Override
	public void handleCharacters(final XMLStreamReader xmlReader) {
	}
}
