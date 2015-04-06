package vu.de.npolke.runalysis.gui;

import java.awt.Container;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import vu.de.npolke.runalysis.calculation.DistanceInterval;
import vu.de.npolke.runalysis.calculation.Interval;
import vu.de.npolke.runalysis.calculation.TimeInterval;

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
public class EditLapBordersDialog extends JDialog implements ActionListener, ItemListener {

	private static final String TITLE = "Edit Lap Borders";

	private static final String RB_TIMELAPS = "Time Laps";
	private static final int TIMELAPS_DEFAULT = 5;
	private static final int TIMELAPS_MINIMUM = 1;
	private static final int TIMELAPS_MAXIMUM = 10;
	private static final int TIMELAPS_STEPSIZE = 1;
	private static final String TIMELAPS_LABEL = "time per lap (min):";

	private static final String RB_DISTANCELAPS = "Distance Laps";
	private static final double DISTANCELAPS_DEFAULT = 1;
	private static final double DISTANCELAPS_MINIMUM = 0.5;
	private static final double DISTANCELAPS_MAXIMUM = 3;
	private static final double DISTANCELAPS_STEPSIZE = 0.5;
	private static final String DISTANCELAPS_LABEL = "distance per lap (km):";

	private static final int INTERVALLAPS_SETS_DEFAULT = 5;
	private static final int INTERVALLAPS_SETS_MINIMUM = 1;
	private static final int INTERVALLAPS_SETS_MAXIMUM = 15;
	private static final int INTERVALLAPS_SETS_STEPSIZE = 1;
	private static final String INTERVALLAPS_SETS_AMOUNT = "#";

	private static final String RB_INTERVALLAPS = "Interval Laps";
	private static final String INTERVALLAPS_WARMUP = "Warm Up";
	private static final String INTERVALLAPS_RUN = "Run";
	private static final String INTERVALLAPS_REST = "Rest";
	private static final String INTERVALLAPS_SETS = "Sets";
	private static final String INTERVALLAPS_COOLDOWN = "Cool Down";
	private static final String INTERVALLAPS_TIME_LABEL = "time (min)";
	private static final String INTERVALLAPS_DISTANCE_LABEL = "distance (km)";

	private static final String BUTTON_OK = "Ok";
	private static final String BUTTON_CANCEL = "Cancel";

	private static final int DIALOG_WIDTH = 600;
	private static final int DIALOG_HEIGHT = 200;
	private static final int DIALOG_LOCATION_X = 500;
	private static final int DIALOG_LOCATION_Y = 200;

	private GridBagLayout gridBagLayout;
	private JRadioButton rbTimeLaps;
	private JRadioButton rbDistanceLaps;
	private JRadioButton rbIntervalLaps;

	private JRadioButton intervalWarmupTime;
	private JRadioButton intervalWarmupDistance;
	private JSpinner intervalWarmupTimeSpinner;
	private JSpinner intervalWarmupDistanceSpinner;

	private JRadioButton intervalRunTime;
	private JRadioButton intervalRunDistance;
	private JSpinner intervalRunTimeSpinner;
	private JSpinner intervalRunDistanceSpinner;

	private JRadioButton intervalRestTime;
	private JRadioButton intervalRestDistance;
	private JSpinner intervalRestTimeSpinner;
	private JSpinner intervalRestDistanceSpinner;

	private JSpinner intervalSetsSpinner;

	private JRadioButton intervalCooldownTime;
	private JRadioButton intervalCooldownDistance;
	private JSpinner intervalCooldownTimeSpinner;
	private JSpinner intervalCooldownDistanceSpinner;

	private JSpinner timeLapsSpinner;
	private JSpinner distanceLapsSpinner;

	JButton okButton;
	JButton cancelButton;

	private boolean isClosedByOkButton = false;

	public EditLapBordersDialog(final Frame owner) {
		super(owner, TITLE, true);
		setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
		setLocation(DIALOG_LOCATION_X, DIALOG_LOCATION_Y);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);

		rbTimeLaps = createAndLayoutRadioButton(RB_TIMELAPS, this, SwingConstants.LEFT, getContentPane(), gridBagLayout, 0, 0, 2, 1);
		rbDistanceLaps = createAndLayoutRadioButton(RB_DISTANCELAPS, this, SwingConstants.LEFT, getContentPane(), gridBagLayout, 0, 1, 2, 1);
		rbIntervalLaps = createAndLayoutRadioButton(RB_INTERVALLAPS, this, SwingConstants.LEFT, getContentPane(), gridBagLayout, 0, 2, 2, 1);

		ButtonGroup typesOflapBorders = new ButtonGroup();
		typesOflapBorders.add(rbTimeLaps);
		typesOflapBorders.add(rbDistanceLaps);
		typesOflapBorders.add(rbIntervalLaps);

		createAndLayoutLabel(TIMELAPS_LABEL, SwingConstants.RIGHT, getContentPane(), gridBagLayout, 2, 0, 2, 1);
		timeLapsSpinner = createAndLayoutDisabledTimeSpinner(getContentPane(), gridBagLayout, 4, 0, 1, 1);

		createAndLayoutLabel(DISTANCELAPS_LABEL, SwingConstants.RIGHT, getContentPane(), gridBagLayout, 2, 1, 2, 1);
		distanceLapsSpinner = createAndLayoutDisabledDistanceSpinner(getContentPane(), gridBagLayout, 4, 1, 1, 1);

		createAndLayoutLabel(INTERVALLAPS_WARMUP, SwingConstants.CENTER, getContentPane(), gridBagLayout, 2, 2, 2, 1);
		intervalWarmupTime = createAndLayoutRadioButton(INTERVALLAPS_TIME_LABEL, this, SwingConstants.RIGHT, getContentPane(),
				gridBagLayout, 4, 2, 2, 1);
		intervalWarmupTimeSpinner = createAndLayoutDisabledTimeSpinner(getContentPane(), gridBagLayout, 6, 2, 1, 1);
		intervalWarmupDistance = createAndLayoutRadioButton(INTERVALLAPS_DISTANCE_LABEL, this, SwingConstants.RIGHT, getContentPane(),
				gridBagLayout, 7, 2, 2, 1);
		intervalWarmupDistanceSpinner = createAndLayoutDisabledDistanceSpinner(getContentPane(), gridBagLayout, 9, 2, 1, 1);
		ButtonGroup typesOfWarmup = new ButtonGroup();
		typesOfWarmup.add(intervalWarmupTime);
		typesOfWarmup.add(intervalWarmupDistance);

		createAndLayoutLabel(INTERVALLAPS_RUN, SwingConstants.CENTER, getContentPane(), gridBagLayout, 2, 3, 2, 1);
		intervalRunTime = createAndLayoutRadioButton(INTERVALLAPS_TIME_LABEL, this, SwingConstants.RIGHT, getContentPane(), gridBagLayout,
				4, 3, 2, 1);
		intervalRunTimeSpinner = createAndLayoutDisabledTimeSpinner(getContentPane(), gridBagLayout, 6, 3, 1, 1);
		intervalRunDistance = createAndLayoutRadioButton(INTERVALLAPS_DISTANCE_LABEL, this, SwingConstants.RIGHT, getContentPane(),
				gridBagLayout, 7, 3, 2, 1);
		intervalRunDistanceSpinner = createAndLayoutDisabledDistanceSpinner(getContentPane(), gridBagLayout, 9, 3, 1, 1);
		ButtonGroup typesOfRun = new ButtonGroup();
		typesOfRun.add(intervalRunTime);
		typesOfRun.add(intervalRunDistance);

		createAndLayoutLabel(INTERVALLAPS_REST, SwingConstants.CENTER, getContentPane(), gridBagLayout, 2, 4, 2, 1);
		intervalRestTime = createAndLayoutRadioButton(INTERVALLAPS_TIME_LABEL, this, SwingConstants.RIGHT, getContentPane(), gridBagLayout,
				4, 4, 2, 1);
		intervalRestTimeSpinner = createAndLayoutDisabledTimeSpinner(getContentPane(), gridBagLayout, 6, 4, 1, 1);
		intervalRestDistance = createAndLayoutRadioButton(INTERVALLAPS_DISTANCE_LABEL, this, SwingConstants.RIGHT, getContentPane(),
				gridBagLayout, 7, 4, 2, 1);
		intervalRestDistanceSpinner = createAndLayoutDisabledDistanceSpinner(getContentPane(), gridBagLayout, 9, 4, 1, 1);
		ButtonGroup typesOfRest = new ButtonGroup();
		typesOfRest.add(intervalRestTime);
		typesOfRest.add(intervalRestDistance);

		createAndLayoutLabel(INTERVALLAPS_SETS, SwingConstants.CENTER, getContentPane(), gridBagLayout, 2, 5, 2, 1);
		createAndLayoutLabel(INTERVALLAPS_SETS_AMOUNT, SwingConstants.RIGHT, getContentPane(), gridBagLayout, 4, 5, 2, 1);
		SpinnerNumberModel spinnerSetsModel = new SpinnerNumberModel(INTERVALLAPS_SETS_DEFAULT, INTERVALLAPS_SETS_MINIMUM,
				INTERVALLAPS_SETS_MAXIMUM, INTERVALLAPS_SETS_STEPSIZE);
		intervalSetsSpinner = new JSpinner(spinnerSetsModel);
		intervalSetsSpinner.setEnabled(false);
		addComponent(getContentPane(), gridBagLayout, intervalSetsSpinner, 6, 5, 1, 1);

		createAndLayoutLabel(INTERVALLAPS_COOLDOWN, SwingConstants.CENTER, getContentPane(), gridBagLayout, 2, 6, 2, 1);
		intervalCooldownTime = createAndLayoutRadioButton(INTERVALLAPS_TIME_LABEL, this, SwingConstants.RIGHT, getContentPane(),
				gridBagLayout, 4, 6, 2, 1);
		intervalCooldownTimeSpinner = createAndLayoutDisabledTimeSpinner(getContentPane(), gridBagLayout, 6, 6, 1, 1);
		intervalCooldownDistance = createAndLayoutRadioButton(INTERVALLAPS_DISTANCE_LABEL, this, SwingConstants.RIGHT, getContentPane(),
				gridBagLayout, 7, 6, 2, 1);
		intervalCooldownDistanceSpinner = createAndLayoutDisabledDistanceSpinner(getContentPane(), gridBagLayout, 9, 6, 1, 1);
		ButtonGroup typesOfCooldown = new ButtonGroup();
		typesOfCooldown.add(intervalCooldownTime);
		typesOfCooldown.add(intervalCooldownDistance);

		setIntervalRadioButtions(false);

		okButton = new JButton(BUTTON_OK);
		okButton.addActionListener(this);
		okButton.setEnabled(rbTimeLaps.isSelected() || rbDistanceLaps.isSelected() || rbIntervalLaps.isSelected());
		addComponent(getContentPane(), gridBagLayout, okButton, 0, 7, 5, 1);
		cancelButton = new JButton(BUTTON_CANCEL);
		cancelButton.addActionListener(this);
		cancelButton.setEnabled(true);
		addComponent(getContentPane(), gridBagLayout, cancelButton, 5, 7, 5, 1);
	}

	private static JLabel createAndLayoutLabel(final String text, int alignment, final Container container, final GridBagLayout gbl,
			final int x, final int y, final int width, final int height) {
		JLabel label = new JLabel(text);
		label.setHorizontalAlignment(alignment);
		addComponent(container, gbl, label, x, y, width, height);
		return label;
	}

	private static JRadioButton createAndLayoutRadioButton(final String text, ItemListener listener, int alignment,
			final Container container, final GridBagLayout gbl, final int x, final int y, final int width, final int height) {
		JRadioButton radioButton = new JRadioButton(text);
		radioButton.setHorizontalAlignment(alignment);
		radioButton.addItemListener(listener);
		addComponent(container, gbl, radioButton, x, y, width, height);
		return radioButton;
	}

	private static JSpinner createAndLayoutDisabledTimeSpinner(final Container container, final GridBagLayout gbl, final int x,
			final int y, final int width, final int height) {
		SpinnerNumberModel timeSpinnerModel = new SpinnerNumberModel(TIMELAPS_DEFAULT, TIMELAPS_MINIMUM, TIMELAPS_MAXIMUM,
				TIMELAPS_STEPSIZE);
		JSpinner spinner = new JSpinner(timeSpinnerModel);
		spinner.setEnabled(false);
		addComponent(container, gbl, spinner, x, y, width, height);
		return spinner;
	}

	private static JSpinner createAndLayoutDisabledDistanceSpinner(final Container container, final GridBagLayout gbl, final int x,
			final int y, final int width, final int height) {
		SpinnerNumberModel distanceSpinnerModel = new SpinnerNumberModel(DISTANCELAPS_DEFAULT, DISTANCELAPS_MINIMUM, DISTANCELAPS_MAXIMUM,
				DISTANCELAPS_STEPSIZE);
		JSpinner spinner = new JSpinner(distanceSpinnerModel);
		spinner.setEnabled(false);
		addComponent(container, gbl, spinner, x, y, width, height);
		return spinner;
	}

	private static void addComponent(final Container container, final GridBagLayout gbl, final JComponent component, final int x,
			final int y, final int width, final int height) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = width;
		gbc.gridheight = height;
		gbc.weightx = width;
		gbc.weighty = height;
		gbl.setConstraints(component, gbc);
		container.add(component);
	}

	private void setIntervalRadioButtions(final boolean value) {
		intervalWarmupTime.setEnabled(value);
		intervalWarmupDistance.setEnabled(value);
		intervalRunTime.setEnabled(value);
		intervalRunDistance.setEnabled(value);
		intervalRestTime.setEnabled(value);
		intervalRestDistance.setEnabled(value);
		intervalCooldownTime.setEnabled(value);
		intervalCooldownDistance.setEnabled(value);
	}

	private void disableIntervalSection() {
		setIntervalRadioButtions(false);
		intervalWarmupTimeSpinner.setEnabled(false);
		intervalWarmupDistanceSpinner.setEnabled(false);
		intervalRunTimeSpinner.setEnabled(false);
		intervalRunDistanceSpinner.setEnabled(false);
		intervalRestTimeSpinner.setEnabled(false);
		intervalRestDistanceSpinner.setEnabled(false);
		intervalCooldownTimeSpinner.setEnabled(false);
		intervalCooldownDistanceSpinner.setEnabled(false);

		intervalSetsSpinner.setEnabled(false);
	}

	private void enableIntervalSection() {
		setIntervalRadioButtions(true);
		intervalWarmupTimeSpinner.setEnabled(intervalWarmupTime.isSelected());
		intervalWarmupDistanceSpinner.setEnabled(intervalWarmupDistance.isSelected());
		intervalRunTimeSpinner.setEnabled(intervalRunTime.isSelected());
		intervalRunDistanceSpinner.setEnabled(intervalRunDistance.isSelected());
		intervalRestTimeSpinner.setEnabled(intervalRestTime.isSelected());
		intervalRestDistanceSpinner.setEnabled(intervalRestDistance.isSelected());
		intervalCooldownTimeSpinner.setEnabled(intervalCooldownTime.isSelected());
		intervalCooldownDistanceSpinner.setEnabled(intervalCooldownDistance.isSelected());

		intervalSetsSpinner.setEnabled(true);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (rbTimeLaps.equals(e.getItem()) && rbTimeLaps.isSelected()) {
			timeLapsSpinner.setEnabled(true);
			distanceLapsSpinner.setEnabled(false);
			disableIntervalSection();
		} else if (rbDistanceLaps.equals(e.getItem()) && rbDistanceLaps.isSelected()) {
			timeLapsSpinner.setEnabled(false);
			distanceLapsSpinner.setEnabled(true);
			disableIntervalSection();
		} else if (rbIntervalLaps.equals(e.getItem()) && rbIntervalLaps.isSelected()) {
			timeLapsSpinner.setEnabled(false);
			distanceLapsSpinner.setEnabled(false);
			enableIntervalSection();
		} else if (intervalWarmupTime.equals(e.getItem()) && intervalWarmupTime.isSelected()) {
			intervalWarmupTimeSpinner.setEnabled(true);
			intervalWarmupDistanceSpinner.setEnabled(false);
		} else if (intervalWarmupDistance.equals(e.getItem()) && intervalWarmupDistance.isSelected()) {
			intervalWarmupTimeSpinner.setEnabled(false);
			intervalWarmupDistanceSpinner.setEnabled(true);
		} else if (intervalRunTime.equals(e.getItem()) && intervalRunTime.isSelected()) {
			intervalRunTimeSpinner.setEnabled(true);
			intervalRunDistanceSpinner.setEnabled(false);
		} else if (intervalRunDistance.equals(e.getItem()) && intervalRunDistance.isSelected()) {
			intervalRunTimeSpinner.setEnabled(false);
			intervalRunDistanceSpinner.setEnabled(true);
		} else if (intervalRestTime.equals(e.getItem()) && intervalRestTime.isSelected()) {
			intervalRestTimeSpinner.setEnabled(true);
			intervalRestDistanceSpinner.setEnabled(false);
		} else if (intervalRestDistance.equals(e.getItem()) && intervalRestDistance.isSelected()) {
			intervalRestTimeSpinner.setEnabled(false);
			intervalRestDistanceSpinner.setEnabled(true);
		} else if (intervalCooldownTime.equals(e.getItem()) && intervalCooldownTime.isSelected()) {
			intervalCooldownTimeSpinner.setEnabled(true);
			intervalCooldownDistanceSpinner.setEnabled(false);
		} else if (intervalCooldownDistance.equals(e.getItem()) && intervalCooldownDistance.isSelected()) {
			intervalCooldownTimeSpinner.setEnabled(false);
			intervalCooldownDistanceSpinner.setEnabled(true);
		}

		if (rbTimeLaps.isSelected()
				|| rbDistanceLaps.isSelected()
				|| (rbIntervalLaps.isSelected() && (intervalWarmupTimeSpinner.isEnabled() || intervalWarmupDistanceSpinner.isEnabled())
						&& (intervalRunTimeSpinner.isEnabled() || intervalRunDistanceSpinner.isEnabled())
						&& (intervalRestTimeSpinner.isEnabled() || intervalRestDistanceSpinner.isEnabled()) && (intervalCooldownTimeSpinner
								.isEnabled() || intervalCooldownDistanceSpinner.isEnabled()))) {
			okButton.setEnabled(true);
		} else {
			okButton.setEnabled(false);
		}
	}

	public boolean hasChosenTimeLaps() {
		return isClosedByOkButton && rbTimeLaps.isSelected();
	}

	public int getRoundMin() {
		return (Integer) timeLapsSpinner.getValue();
	}

	public boolean hasChosenDistanceLaps() {
		return isClosedByOkButton && rbDistanceLaps.isSelected();
	}

	public double getRoundKm() {
		return (Double) distanceLapsSpinner.getValue();
	}

	public boolean hasChosenIntervalLaps() {
		return isClosedByOkButton && rbIntervalLaps.isSelected();
	}

	public List<Interval> getIntervals() {
		List<Interval> intervals = new LinkedList<Interval>();

		if (intervalWarmupTimeSpinner.isEnabled()) {
			intervals.add(new TimeInterval(new Double((Integer) intervalWarmupTimeSpinner.getValue())));
		} else {
			intervals.add(new DistanceInterval((Double) intervalWarmupDistanceSpinner.getValue()));
		}

		for (int indexSet = 1; indexSet <= (Integer) intervalSetsSpinner.getValue(); indexSet++) {
			if (intervalRunTimeSpinner.isEnabled()) {
				intervals.add(new TimeInterval(new Double((Integer) intervalRunTimeSpinner.getValue())));
			} else {
				intervals.add(new DistanceInterval((Double) intervalRunDistanceSpinner.getValue()));
			}
			if (intervalRestTimeSpinner.isEnabled()) {
				intervals.add(new TimeInterval(new Double((Integer) intervalRestTimeSpinner.getValue())));
			} else {
				intervals.add(new DistanceInterval((Double) intervalRestDistanceSpinner.getValue()));
			}
		}

		if (intervalCooldownTimeSpinner.isEnabled()) {
			intervals.add(new TimeInterval(new Double((Integer) intervalCooldownTimeSpinner.getValue())));
		} else {
			intervals.add(new DistanceInterval((Double) intervalCooldownDistanceSpinner.getValue()));
		}
		return intervals;
	}

	public boolean isClosedByOkButton() {
		return isClosedByOkButton;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (BUTTON_OK.equals(e.getActionCommand())) {
			isClosedByOkButton = true;
		} else if (BUTTON_CANCEL.equals(e.getActionCommand())) {
			isClosedByOkButton = false;
		}
		setVisible(false);
	}
}
