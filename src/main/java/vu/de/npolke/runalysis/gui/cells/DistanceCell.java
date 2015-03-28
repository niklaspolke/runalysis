package vu.de.npolke.runalysis.gui.cells;

import java.util.Locale;

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
public class DistanceCell {

	private final String DISTANCE_FORMAT = "%.3f km";

	private final double distanceInKilometers;

	public DistanceCell(final double distanceInMeters) {
		distanceInKilometers = distanceInMeters / 1000;
	}

	@Override
	public String toString() {
		return String.format(Locale.ENGLISH, DISTANCE_FORMAT, distanceInKilometers);
	}
}
