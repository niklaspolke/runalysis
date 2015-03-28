package vu.de.npolke.runalysis.gui;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import vu.de.npolke.runalysis.BreakCorrectionLogic;
import vu.de.npolke.runalysis.Lap;
import vu.de.npolke.runalysis.TcxParser;
import vu.de.npolke.runalysis.Track;
import vu.de.npolke.runalysis.gui.cells.DistanceCell;
import vu.de.npolke.runalysis.gui.cells.DurationCell;
import vu.de.npolke.runalysis.gui.cells.PaceCell;
import vu.de.npolke.runalysis.gui.cells.TableCellRenderer;
import vu.de.npolke.runalysis.gui.cells.TimestampCell;

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
	private static final int WINDOW_WIDTH = 500;
	private static final int WINDOW_HEIGHT = 200;
	private static final int WINDOW_LOCATION_X = 400;
	private static final int WINDOW_LOCATION_Y = 100;

	private static final String[] TABLE_TRACK_COLUMN_NAMES = { "Start", "Distance", "Duration", "Pace" };
	private static final String[] TABLE_LAPS_COLUMN_NAMES = { "Runde", "Distance", "Duration", "Pace" };

	private JTable trackTable;
	private JTable lapsTable;
	private GridBagLayout gridBagLayout;

	public MainFrame(final Track track) {
		super(WINDOW_TITLE);

		gridBagLayout = new GridBagLayout();
		getContentPane().setLayout(gridBagLayout);

		setTrack(track);

		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setLocation(WINDOW_LOCATION_X, WINDOW_LOCATION_Y);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private static void addComponent(final Container container, final GridBagLayout gbl, final JComponent component, final int x,
			final int y, final int width, final int height) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = width;
		gbc.gridheight = height;
		gbc.weightx = width;
		gbc.weighty = height;
		gbl.setConstraints(component, gbc);
		container.add(component);
	}

	private void setTrack(final Track track) {
		Object[][] data = new Object[1][4];
		data[0][0] = new TimestampCell(track.getStartTime());
		data[0][1] = new DistanceCell(track.getDistanceMeters());
		data[0][2] = new DurationCell(track.getTotalTimeSeconds());
		data[0][3] = new PaceCell(track.getTotalTimeSeconds(), track.getDistanceMeters());
		trackTable = new JTable(data, TABLE_TRACK_COLUMN_NAMES);
		trackTable.setEnabled(false);
		trackTable.setDefaultRenderer(Object.class, new TableCellRenderer());

		JScrollPane scrollPane = new JScrollPane(trackTable, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));

		addComponent(getContentPane(), gridBagLayout, scrollPane, 0, 0, 1, 1);

		setLaps(track.getLaps());
	}

	private void setLaps(final List<Lap> laps) {
		Object[][] data = new Object[laps.size()][4];
		int lapIndex = 0;
		for (Lap lap : laps) {
			data[lapIndex][0] = "" + (lapIndex + 1);
			data[lapIndex][1] = new DistanceCell(lap.getDistanceMeters());
			data[lapIndex][2] = new DurationCell(lap.getTotalTimeSeconds());
			data[lapIndex][3] = new PaceCell(lap.getTotalTimeSeconds(), lap.getDistanceMeters());
			lapIndex++;
		}
		lapsTable = new JTable(data, TABLE_LAPS_COLUMN_NAMES);
		lapsTable.setEnabled(false);
		lapsTable.setDefaultRenderer(Object.class, new TableCellRenderer());

		JScrollPane scrollPane = new JScrollPane(lapsTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));

		addComponent(getContentPane(), gridBagLayout, scrollPane, 0, 2, 1, 2);
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println(TcxParser.USAGE);
		} else {
			TcxParser parser = new TcxParser(args[0]);
			parser.readFile();
			BreakCorrectionLogic.removeBreaksFromTrack(parser.getTrack());
			MainFrame frame = new MainFrame(parser.getTrack());
			frame.setVisible(true);
		}
	}
}
