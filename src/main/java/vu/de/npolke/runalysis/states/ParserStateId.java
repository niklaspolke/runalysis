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
public class ParserStateId implements ParserState {

	private final TcxParser parser;

	private final ParserStateDefault parent;

	public ParserStateId(final TcxParser parser, final ParserStateDefault parent) {
		this.parser = parser;
		this.parent = parent;
	}

	@Override
	public void handleStartElement(final XMLStreamReader xmlReader) {
	}

	@Override
	public void handleEndElement(final XMLStreamReader xmlReader) {
		String endElement = xmlReader.getLocalName();
		if (TcxParser.ELEMENT_ID.equalsIgnoreCase(endElement)) {
			parser.changeState(parent);
		}
	}

	@Override
	public void handleCharacters(final XMLStreamReader xmlReader) {
		String text = xmlReader.getText();
		parent.setStartTimestampMillis(parser.extractTimestampMillis(text));
	}
}
