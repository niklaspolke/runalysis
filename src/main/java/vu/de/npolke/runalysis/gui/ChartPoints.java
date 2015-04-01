package vu.de.npolke.runalysis.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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
public class ChartPoints {

	private Collection<Date> timestamps = new ArrayList<Date>();

	private Collection<Double> values = new ArrayList<Double>();

	public void addChartPoint(final long timestampMillis, final Double value) {
		timestamps.add(new Date(timestampMillis));
		values.add(value);
	}

	public Collection<Date> getTimestamps() {
		return timestamps;
	}

	public Collection<Double> getValues() {
		return values;
	}
}
