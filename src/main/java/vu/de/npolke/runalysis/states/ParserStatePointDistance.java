package vu.de.npolke.runalysis.states;

import javax.xml.stream.XMLStreamReader;

import vu.de.npolke.runalysis.TcxParser;

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
public class ParserStatePointDistance implements ParserState {

	private final TcxParser parser;

	private final ParserStatePoint parentStatePoint;

	public ParserStatePointDistance(final TcxParser parser, final ParserStatePoint parent) {
		this.parser = parser;
		parentStatePoint = parent;
	}

	@Override
	public void handleStartElement(final XMLStreamReader xmlReader) {
	}

	@Override
	public void handleEndElement(final XMLStreamReader xmlReader) {
		String endElement = xmlReader.getLocalName();
		if (TcxParser.ELEMENT_POINT_DISTANCE.equalsIgnoreCase(endElement)) {
			parser.changeState(parentStatePoint);
		}
	}

	@Override
	public void handleCharacters(final XMLStreamReader xmlReader) {
		String text = xmlReader.getText();
		parentStatePoint.setRecorededDistanceMeters(Double.parseDouble(text));
	}
}
