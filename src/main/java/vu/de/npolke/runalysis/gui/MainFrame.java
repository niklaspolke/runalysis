package vu.de.npolke.runalysis.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import vu.de.npolke.runalysis.LapCreationLogic;
import vu.de.npolke.runalysis.PaceCalculator;
import vu.de.npolke.runalysis.TcxParser;
import vu.de.npolke.runalysis.calculation.CalculationLap;
import vu.de.npolke.runalysis.calculation.CalculationTrack;
import vu.de.npolke.runalysis.calculation.Interval;
import vu.de.npolke.runalysis.gui.cells.TableCellRenderer;
import vu.de.npolke.runalysis.gui.cells.TableModel;

import com.xeiam.xchart.Chart;
import com.xeiam.xchart.Series;
import com.xeiam.xchart.SeriesMarker;
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
	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 600;
	private static final int WINDOW_LOCATION_X = 400;
	private static final int WINDOW_LOCATION_Y = 100;
	private static final String CHART_SERIES_TITLE = "Pace (min/km)";
	private static final int CHART_WIDTH = 800;
	private static final int CHART_HEIGHT = 200;

	private static final String MENU_FILE = "File";
	private static final String MENU_FILE_OPEN = "Open File...";
	private static final String MENU_FILE_OPEN_ICON = "/open.png";

	private static final String BUTTON_EDITLAPS_TEXT = "Edit Lap Borders";

	private final TcxParser parser;

	private TableModel trackTableModel;
	private TableModel lapsTableModel;
	private GridBagLayout gridBagLayout;

	private JButton buttonEditLapBorders;
	private EditLapBordersDialog dialogEditLapBorders;

	private CalculationTrack track;
	private Chart chart;
	private JPanel chartPanel;

	private ImageIcon openIcon;
	private JFileChooser fileChooser;

	public MainFrame() {
		super(WINDOW_TITLE);

		gridBagLayout = new GridBagLayout();
		getContentPane().setLayout(gridBagLayout);

		initializeTrackTable(null);
		initializeLapsTable(null);
		initializePaceDiagram();
		editLapBorders();
		dialogEditLapBorders = new EditLapBordersDialog(this);

		setJMenuBar(createMenuBar());

		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setLocation(WINDOW_LOCATION_X, WINDOW_LOCATION_Y);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		parser = new TcxParser();
	}

	public JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		try {
			openIcon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream(MENU_FILE_OPEN_ICON)));
		} catch (IOException e) {
		}

		JMenu menu = new JMenu(MENU_FILE);
		menu.setMnemonic(KeyEvent.VK_F);
		JMenuItem menuItem = new JMenuItem(MENU_FILE_OPEN, openIcon);
		menuItem.setMnemonic(KeyEvent.VK_O);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuBar.add(menu);
		return menuBar;
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

	public void openFile(final String filename) {
		parser.setFilename(filename);
		parser.readFile();
		track = parser.getTrack();
		updateTrack(track);
		updateLaps(track.getLaps());
		updatePaceDiagram(track);
	}

	private void initializeTrackTable(final CalculationTrack track) {
		trackTableModel = new TableModel(track);
		JTable trackTable = new JTable(trackTableModel);
		trackTable.setEnabled(false);
		trackTable.setDefaultRenderer(Object.class, new TableCellRenderer());

		JScrollPane scrollPane = new JScrollPane(trackTable, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));

		addComponent(getContentPane(), gridBagLayout, scrollPane, 0, 0, 1, 1);
	}

	private void initializeLapsTable(final List<CalculationLap> laps) {
		lapsTableModel = new TableModel(laps);
		JTable lapsTable = new JTable(lapsTableModel);
		lapsTable.setEnabled(false);
		lapsTable.setDefaultRenderer(Object.class, new TableCellRenderer());

		JScrollPane scrollPane = new JScrollPane(lapsTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));

		addComponent(getContentPane(), gridBagLayout, scrollPane, 0, 2, 1, 2);
	}

	private void initializePaceDiagram() {
		chart = new Chart(CHART_WIDTH, CHART_HEIGHT);
		chart.getStyleManager().setYAxisMin(2);
		chart.getStyleManager().setYAxisMax(9);
		chartPanel = new XChartPanel(chart);
		chartPanel.setVisible(false);

		addComponent(getContentPane(), gridBagLayout, chartPanel, 0, 4, 1, 2);
	}

	private void updateLaps(final List<CalculationLap> laps) {
		lapsTableModel.setLaps(laps);
	}

	private void updateTrack(final CalculationTrack track) {
		trackTableModel.setTrack(track);
	}

	private void updatePaceDiagram(final CalculationTrack track) {
		boolean oldStyleExists = false;
		BasicStroke stroke = null;
		Color strokeColor = null, fillColor = null, markerColor = null;
		if (chart.getSeriesMap().size() > 0) {
			oldStyleExists = true;
			Series serie = chart.getSeriesMap().values().iterator().next();
			stroke = serie.getStroke();
			strokeColor = serie.getStrokeColor();
			fillColor = serie.getFillColor();
			markerColor = serie.getMarkerColor();
		}
		ChartPoints points = PaceCalculator.calculatePace(track, 30);
		chart.getSeriesMap().clear();
		chart.addSeries(CHART_SERIES_TITLE, points.getTimestamps(), points.getValues());
		if (oldStyleExists) {
			Series serie = chart.getSeriesMap().values().iterator().next();
			serie.setLineStyle(stroke);
			serie.setLineColor(strokeColor);
			serie.setFillColor(fillColor);
			serie.setMarkerColor(markerColor);
			serie.setMarker(SeriesMarker.CIRCLE);
		}
		chartPanel.setVisible(true);
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
				} else if (dialogEditLapBorders.hasChosenTimeLaps()) {
					int minPerRound = dialogEditLapBorders.getRoundMin();
					calculatedLaps = LapCreationLogic.createLapsByTimeRounds(track, minPerRound);
				} else {
					List<Interval> intervals = dialogEditLapBorders.getIntervals();
					calculatedLaps = LapCreationLogic.createLapsByIntervals(track, intervals);
				}
				updateLaps(calculatedLaps);
			}
		} else if (e.getActionCommand().equals(MENU_FILE_OPEN)) {
			if (fileChooser == null) {
				fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new TcxFileFilter());
			}
			int returnVal = fileChooser.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				openFile(file.getAbsolutePath());
			}
		}
	}

	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
		if (args.length == 1) {
			frame.openFile(args[0]);
		}
		frame.setVisible(true);
	}
}
