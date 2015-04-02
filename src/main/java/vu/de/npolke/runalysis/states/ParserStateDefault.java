package vu.de.npolke.runalysis.states;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamReader;

import vu.de.npolke.runalysis.Lap;
import vu.de.npolke.runalysis.TcxParser;
import vu.de.npolke.runalysis.Track;

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

	private long startTimestampMillis;
	private List<Lap> laps;

	public ParserStateDefault(final TcxParser parser) {
		this.parser = parser;
		stateLap = new ParserStateLap(parser, this);
		stateId = new ParserStateId(parser, this);
		laps = new ArrayList<Lap>();
	}

	public void setStartTimestampMillis(final long startTimestampMillis) {
		this.startTimestampMillis = startTimestampMillis;
	}

	public void addLap(final Lap newLap) {
		laps.add(newLap);
	}

	@Override
	public void handleStartElement(final XMLStreamReader xmlReader) {
		String startElement = xmlReader.getLocalName();
		if (TcxParser.ELEMENT_LAP.equalsIgnoreCase(startElement)) {
			stateLap.setStartTimestampMillis(parser.extractTimestampMillis(xmlReader.getAttributeValue(null,
					TcxParser.ELEMENT_LAP_PROPERTY_STARTTIME)));
			parser.changeState(stateLap);
		} else if (TcxParser.ELEMENT_ID.equalsIgnoreCase(startElement)) {
			parser.changeState(stateId);
		}
	}

	@Override
	public void handleEndElement(final XMLStreamReader xmlReader) {
		String endElement = xmlReader.getLocalName();
		if (TcxParser.ELEMENT_ACTIVITY.equalsIgnoreCase(endElement)) {
			Track track = new Track(startTimestampMillis, laps);
			parser.setTrack(track);
		}
	}

	@Override
	public void handleCharacters(final XMLStreamReader xmlReader) {
	}
}
