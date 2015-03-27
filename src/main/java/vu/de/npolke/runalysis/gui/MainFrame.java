package vu.de.npolke.runalysis.gui;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import vu.de.npolke.runalysis.BreakCorrectionLogic;
import vu.de.npolke.runalysis.Lap;
import vu.de.npolke.runalysis.TcxParser;
import vu.de.npolke.runalysis.gui.cells.DistanceCell;
import vu.de.npolke.runalysis.gui.cells.DurationCell;
import vu.de.npolke.runalysis.gui.cells.PaceCell;
import vu.de.npolke.runalysis.gui.cells.TableCellRenderer;

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
public class MainFrame extends JFrame {

	private static final String WINDOW_TITLE = "Runalysis by Niklas Polke";
	private static final int WINDOW_WIDTH = 400;
	private static final int WINDOW_HEIGHT = 600;
	private static final int WINDOW_LOCATION_X = 400;
	private static final int WINDOW_LOCATION_Y = 100;

	private static final String[] TABLE_LAPS_COLUMN_NAMES = { "Runde", "Distance", "Duration", "Pace" };

	private JTable table;

	public MainFrame() {
		super(WINDOW_TITLE);

		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setLocation(WINDOW_LOCATION_X, WINDOW_LOCATION_Y);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void setLaps(final List<Lap> laps) {
		Object[][] data = new Object[laps.size()][4];
		int lapIndex = 0;
		for (Lap lap : laps) {
			data[lapIndex][0] = "" + (lapIndex + 1);
			data[lapIndex][1] = new DistanceCell(lap.getDistanceMeters());
			data[lapIndex][2] = new DurationCell(lap.getTotalTimeSeconds());
			data[lapIndex][3] = new PaceCell(lap.getTotalTimeSeconds(), lap.getDistanceMeters());
			lapIndex++;
		}
		table = new JTable(data, TABLE_LAPS_COLUMN_NAMES);
		table.setEnabled(false);
		table.setDefaultRenderer(Object.class, new TableCellRenderer());
		add(new JScrollPane(table));
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println(TcxParser.USAGE);
		} else {
			TcxParser parser = new TcxParser(args[0]);
			parser.readFile();
			BreakCorrectionLogic.removeBreaksFromTrack(parser.getLaps());
			MainFrame frame = new MainFrame();
			frame.setLaps(parser.getLaps());
			frame.setVisible(true);
		}
	}
}
