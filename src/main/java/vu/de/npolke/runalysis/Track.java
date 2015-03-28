package vu.de.npolke.runalysis;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

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
public class Track {

	// NOT thread-safe
	private static SimpleDateFormat TIME_FORMAT;
	private static SimpleDateFormat DURATION_FORMAT;

	static {
		TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		TIME_FORMAT.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
		DURATION_FORMAT = new SimpleDateFormat("H:mm:ss");
		DURATION_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	private Date startTime;

	private List<Lap> laps;

	public Track() {
		laps = new ArrayList<Lap>();
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(final Date startTime) {
		this.startTime = startTime;
	}

	public double getTotalTimeSeconds() {
		double durationInSeconds = 0;
		for (Lap lap : getLaps()) {
			durationInSeconds += lap.getTotalTimeSeconds();
		}
		return durationInSeconds;
	}

	public double getDistanceMeters() {
		double distanceInMeters = 0;
		for (Lap lap : getLaps()) {
			distanceInMeters += lap.getDistanceMeters();
		}
		return distanceInMeters;
	}

	public List<Lap> getLaps() {
		return laps;
	}

	public void addLap(final Lap lap) {
		laps.add(lap);
	}

	@Override
	public String toString() {
		return Track.class.getSimpleName() + " (" + TIME_FORMAT.format(getStartTime()) + "): "
				+ DURATION_FORMAT.format(new Date((long) (getTotalTimeSeconds() * 1000))) + " h - "
				+ String.format(Locale.ENGLISH, "%5.3f", getDistanceMeters() / 1000) + " km - " + getLaps().size() + " lap(s)";
	}
}
