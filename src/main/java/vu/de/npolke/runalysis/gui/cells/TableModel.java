package vu.de.npolke.runalysis.gui.cells;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import vu.de.npolke.runalysis.calculation.CalculationLap;
import vu.de.npolke.runalysis.calculation.CalculationTrack;

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
@SuppressWarnings("serial")
public class TableModel extends AbstractTableModel {

	private static final String[] TABLE_TRACK_COLUMN_NAMES = { "Start", "Distance", "Duration", "Pace" };
	private static final String[] TABLE_LAPS_COLUMN_NAMES = { "Runde", "Distance", "Duration", "Pace" };

	private Mode mode;
	private Object[][] cells;

	public TableModel(final CalculationTrack track) {
		setTrack(track);
	}

	public TableModel(final List<CalculationLap> laps) {
		setLaps(laps);
	}

	public void setTrack(final CalculationTrack track) {
		mode = Mode.TRACK;
		cells = new Object[1][TABLE_TRACK_COLUMN_NAMES.length];
		cells[0][0] = new TimestampCell(track.getStartTimestamp());
		cells[0][1] = new DistanceCell(track.getRunDistanceInMeters());
		cells[0][2] = new DurationCell(track.getRunDurationInSeconds());
		cells[0][3] = new PaceCell(track.getRunDurationInSeconds(), track.getRunDistanceInMeters());
		fireTableDataChanged();
	}

	public void setLaps(final List<CalculationLap> laps) {
		mode = Mode.LAPS;
		cells = new Object[laps.size()][TABLE_LAPS_COLUMN_NAMES.length];

		int lapIndex = 0;
		for (CalculationLap lap : laps) {
			cells[lapIndex][0] = "" + (lapIndex + 1);
			cells[lapIndex][1] = new DistanceCell(lap.getRunDistanceInMeters());
			cells[lapIndex][2] = new DurationCell(lap.getRunDurationInSeconds());
			cells[lapIndex][3] = new PaceCell(lap.getRunDurationInSeconds(), lap.getRunDistanceInMeters());
			lapIndex++;
		}
		fireTableDataChanged();
	}

	@Override
	public int getRowCount() {
		return cells.length;
	}

	@Override
	public int getColumnCount() {
		return Mode.LAPS.equals(mode) ? TABLE_LAPS_COLUMN_NAMES.length : TABLE_TRACK_COLUMN_NAMES.length;
	}

	@Override
	public String getColumnName(final int column) {
		return Mode.LAPS.equals(mode) ? TABLE_LAPS_COLUMN_NAMES[column] : TABLE_TRACK_COLUMN_NAMES[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return cells[rowIndex][columnIndex];
	}

	enum Mode {
		TRACK, LAPS;
	}
}
