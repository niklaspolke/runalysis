package vu.de.npolke.runalysis.gui;

import java.awt.Container;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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

	private static final String BUTTON_OK = "Ok";
	private static final String BUTTON_CANCEL = "Cancel";

	private static final int DIALOG_WIDTH = 400;
	private static final int DIALOG_HEIGHT = 200;
	private static final int DIALOG_LOCATION_X = 500;
	private static final int DIALOG_LOCATION_Y = 200;

	private GridBagLayout gridBagLayout;
	private JRadioButton rbTimeLaps;
	private JRadioButton rbDistanceLaps;

	private JLabel timeLapsLabel;
	private JSpinner timeLapsSpinner;
	private JLabel distanceLapsLabel;
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

		rbTimeLaps = new JRadioButton(RB_TIMELAPS);
		rbTimeLaps.addItemListener(this);
		addComponent(getContentPane(), gridBagLayout, rbTimeLaps, 0, 0, 1, 1);
		rbDistanceLaps = new JRadioButton(RB_DISTANCELAPS);
		rbDistanceLaps.addItemListener(this);
		addComponent(getContentPane(), gridBagLayout, rbDistanceLaps, 0, 1, 1, 1);

		ButtonGroup typesOflapBorders = new ButtonGroup();
		typesOflapBorders.add(rbTimeLaps);
		typesOflapBorders.add(rbDistanceLaps);

		timeLapsLabel = new JLabel(TIMELAPS_LABEL);
		timeLapsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		addComponent(getContentPane(), gridBagLayout, timeLapsLabel, 1, 0, 1, 1);
		SpinnerNumberModel spinnerTimeLapsModel = new SpinnerNumberModel(TIMELAPS_DEFAULT, TIMELAPS_MINIMUM, TIMELAPS_MAXIMUM,
				TIMELAPS_STEPSIZE);
		timeLapsSpinner = new JSpinner(spinnerTimeLapsModel);
		timeLapsSpinner.setEnabled(false);
		addComponent(getContentPane(), gridBagLayout, timeLapsSpinner, 2, 0, 1, 1);

		distanceLapsLabel = new JLabel(DISTANCELAPS_LABEL);
		distanceLapsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		addComponent(getContentPane(), gridBagLayout, distanceLapsLabel, 1, 1, 1, 1);
		SpinnerNumberModel spinnerDistanceLapsModel = new SpinnerNumberModel(DISTANCELAPS_DEFAULT, DISTANCELAPS_MINIMUM,
				DISTANCELAPS_MAXIMUM, DISTANCELAPS_STEPSIZE);
		distanceLapsSpinner = new JSpinner(spinnerDistanceLapsModel);
		distanceLapsSpinner.setEnabled(false);
		addComponent(getContentPane(), gridBagLayout, distanceLapsSpinner, 2, 1, 1, 1);

		okButton = new JButton(BUTTON_OK);
		okButton.addActionListener(this);
		okButton.setEnabled(rbTimeLaps.isSelected() || rbDistanceLaps.isSelected());
		addComponent(getContentPane(), gridBagLayout, okButton, 0, 2, 1, 1);
		cancelButton = new JButton(BUTTON_CANCEL);
		cancelButton.addActionListener(this);
		addComponent(getContentPane(), gridBagLayout, cancelButton, 1, 2, 1, 1);
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

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (rbTimeLaps.isSelected()) {
			timeLapsSpinner.setEnabled(true);
			distanceLapsSpinner.setEnabled(false);
			okButton.setEnabled(true);
		} else if (rbDistanceLaps.isSelected()) {
			timeLapsSpinner.setEnabled(false);
			distanceLapsSpinner.setEnabled(true);
			okButton.setEnabled(true);
		}
	}

	public boolean hasChosenTimeLaps() {
		return isClosedByOkButton && timeLapsSpinner.isEnabled();
	}

	public int getRoundMin() {
		return (Integer) timeLapsSpinner.getValue();
	}

	public boolean hasChosenDistanceLaps() {
		return isClosedByOkButton && distanceLapsSpinner.isEnabled();
	}

	public double getRoundKm() {
		return (Double) distanceLapsSpinner.getValue();
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
