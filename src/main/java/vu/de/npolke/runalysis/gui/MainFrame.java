package vu.de.npolke.runalysis.gui;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import vu.de.npolke.runalysis.LapCreationLogic;
import vu.de.npolke.runalysis.PaceCalculator;
import vu.de.npolke.runalysis.TcxParser;
import vu.de.npolke.runalysis.calculation.CalculationLap;
import vu.de.npolke.runalysis.calculation.CalculationTrack;
import vu.de.npolke.runalysis.gui.cells.DistanceCell;
import vu.de.npolke.runalysis.gui.cells.DurationCell;
import vu.de.npolke.runalysis.gui.cells.PaceCell;
import vu.de.npolke.runalysis.gui.cells.TableCellRenderer;
import vu.de.npolke.runalysis.gui.cells.TimestampCell;

import com.xeiam.xchart.Chart;
import com.xeiam.xchart.XChartPanel;

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
public class MainFrame extends JFrame implements ActionListener {

	private static final String WINDOW_TITLE = "Runalysis by Niklas Polke";
	private static final int WINDOW_WIDTH = 1200;
	private static final int WINDOW_HEIGHT = 400;
	private static final int WINDOW_LOCATION_X = 400;
	private static final int WINDOW_LOCATION_Y = 100;
	private static final String CHART_SERIES_TITLE = "Pace (min/km)";
	private static final int CHART_WIDTH = 800;
	private static final int CHART_HEIGHT = 200;

	private static final String[] TABLE_TRACK_COLUMN_NAMES = { "Start", "Distance", "Duration", "Pace" };
	private static final String[] TABLE_LAPS_COLUMN_NAMES = { "Runde", "Distance", "Duration", "Pace" };

	private static final String BUTTON_EDITLAPS_TEXT = "Edit Lap Borders";

	private JTable trackTable;
	private JTable lapsTable;
	private GridBagLayout gridBagLayout;

	private JButton buttonEditLapBorders;
	private EditLapBordersDialog dialogEditLapBorders;

	JScrollPane pane;

	private CalculationTrack track;

	public MainFrame(final CalculationTrack track) {
		super(WINDOW_TITLE);

		gridBagLayout = new GridBagLayout();
		getContentPane().setLayout(gridBagLayout);

		this.track = track;
		setTrack(track);
		setLaps(track.getLaps());
		setPaces(PaceCalculator.calculatePace(track, 30));
		editLapBorders();
		dialogEditLapBorders = new EditLapBordersDialog(this);

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

	private void setTrack(final CalculationTrack track) {
		Object[][] data = new Object[1][4];
		data[0][0] = new TimestampCell(track.getStartTimestamp());
		data[0][1] = new DistanceCell(track.getRunDistanceInMeters());
		data[0][2] = new DurationCell(track.getRunDurationInSeconds());
		data[0][3] = new PaceCell(track.getRunDurationInSeconds(), track.getRunDistanceInMeters());
		trackTable = new JTable(data, TABLE_TRACK_COLUMN_NAMES);
		trackTable.setEnabled(false);
		trackTable.setDefaultRenderer(Object.class, new TableCellRenderer());

		JScrollPane scrollPane = new JScrollPane(trackTable, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));

		addComponent(getContentPane(), gridBagLayout, scrollPane, 0, 0, 1, 1);
	}

	private void setLaps(final List<CalculationLap> laps) {
		Object[][] data = new Object[laps.size()][4];
		int lapIndex = 0;
		for (CalculationLap lap : laps) {
			data[lapIndex][0] = "" + (lapIndex + 1);
			data[lapIndex][1] = new DistanceCell(lap.getRunDistanceInMeters());
			data[lapIndex][2] = new DurationCell(lap.getRunDurationInSeconds());
			data[lapIndex][3] = new PaceCell(lap.getRunDurationInSeconds(), lap.getRunDistanceInMeters());
			lapIndex++;
		}

		lapsTable = new JTable(data, TABLE_LAPS_COLUMN_NAMES);
		lapsTable.setEnabled(false);
		lapsTable.setDefaultRenderer(Object.class, new TableCellRenderer());

		JScrollPane scrollPane = new JScrollPane(lapsTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		pane = scrollPane;

		addComponent(getContentPane(), gridBagLayout, scrollPane, 0, 2, 1, 2);
	}

	private void updateLaps(final List<CalculationLap> laps) {
		Object[][] data = new Object[laps.size()][4];
		int lapIndex = 0;
		for (CalculationLap lap : laps) {
			data[lapIndex][0] = "" + (lapIndex + 1);
			data[lapIndex][1] = new DistanceCell(lap.getRunDistanceInMeters());
			data[lapIndex][2] = new DurationCell(lap.getRunDurationInSeconds());
			data[lapIndex][3] = new PaceCell(lap.getRunDurationInSeconds(), lap.getRunDistanceInMeters());
			lapIndex++;
		}

		GridBagConstraints gbc = gridBagLayout.getConstraints(pane);
		getContentPane().remove(pane);

		lapsTable = new JTable(data, TABLE_LAPS_COLUMN_NAMES);
		lapsTable.setEnabled(false);
		lapsTable.setDefaultRenderer(Object.class, new TableCellRenderer());

		JScrollPane scrollPane = new JScrollPane(lapsTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		pane = scrollPane;

		gridBagLayout.setConstraints(scrollPane, gbc);
		getContentPane().add(scrollPane);
		getContentPane().revalidate();
		getContentPane().repaint();
	}

	private void setPaces(final ChartPoints chartPoints) {

		Chart chart = new Chart(CHART_WIDTH, CHART_HEIGHT);
		chart.addSeries(CHART_SERIES_TITLE, chartPoints.getTimestamps(), chartPoints.getValues());
		chart.getStyleManager().setYAxisMin(2);
		chart.getStyleManager().setYAxisMax(9);
		JPanel chartPanel = new XChartPanel(chart);

		addComponent(getContentPane(), gridBagLayout, chartPanel, 0, 4, 1, 2);
	}

	private void editLapBorders() {
		buttonEditLapBorders = new JButton(BUTTON_EDITLAPS_TEXT);
		buttonEditLapBorders.addActionListener(this);

		addComponent(getContentPane(), gridBagLayout, buttonEditLapBorders, 0, 6, 1, 1);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(BUTTON_EDITLAPS_TEXT)) {
			dialogEditLapBorders.setVisible(true);
			if (dialogEditLapBorders.isClosedByOkButton()) {
				List<CalculationLap> calculatedLaps;
				if (dialogEditLapBorders.hasChosenDistanceLaps()) {
					double kmPerRound = dialogEditLapBorders.getRoundKm();
					calculatedLaps = LapCreationLogic.createLapsByDistanceRounds(track, kmPerRound);
				} else {
					int minPerRound = dialogEditLapBorders.getRoundMin();
					calculatedLaps = LapCreationLogic.createLapsByTimeRounds(track, minPerRound);
				}
				updateLaps(calculatedLaps);
			}
		}
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println(TcxParser.USAGE);
		} else {
			TcxParser parser = new TcxParser(args[0]);
			parser.readFile();
			CalculationTrack track = parser.getTrack();
			MainFrame frame = new MainFrame(track);
			frame.setVisible(true);
		}
	}
}
