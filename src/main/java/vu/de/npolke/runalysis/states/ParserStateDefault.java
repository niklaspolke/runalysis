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
public class ParserStateDefault implements ParserState {

	private final TcxParser parser;

	private final ParserStateId stateId;
	private final ParserStateLap stateLap;

	public ParserStateDefault(final TcxParser parser) {
		this.parser = parser;
		stateLap = new ParserStateLap(parser, this);
		stateId = new ParserStateId(parser, this);
	}

	@Override
	public void handleStartElement(final XMLStreamReader xmlReader) {
		String startElement = xmlReader.getLocalName();
		if (TcxParser.ELEMENT_LAP.equalsIgnoreCase(startElement)) {
			stateLap.setStartTimestampMillis(parser.extractTimestamp(
					xmlReader.getAttributeValue(null, TcxParser.ELEMENT_LAP_PROPERTY_STARTTIME)).getTime());
			parser.changeState(stateLap);
		} else if (TcxParser.ELEMENT_ID.equalsIgnoreCase(startElement)) {
			parser.changeState(stateId);
		}
	}

	@Override
	public void handleEndElement(final XMLStreamReader xmlReader) {
	}

	@Override
	public void handleCharacters(final XMLStreamReader xmlReader) {
	}
}
