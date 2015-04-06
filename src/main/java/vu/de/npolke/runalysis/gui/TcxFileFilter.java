package vu.de.npolke.runalysis.gui;

import java.io.File;

import javax.swing.filechooser.FileFilter;

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
public class TcxFileFilter extends FileFilter {

	private static final String DESCRIPTION = "Tcx-Files (*.tcx, *.zip)";
	private static final String FILEEXTENSION_TCX = "tcx";
	private static final String FILEEXTENSION_ZIP = "zip";

	@Override
	public boolean accept(File file) {
		if (file.isDirectory()) {
			return true;
		}

		String extension = getExtension(file);
		if (extension != null) {
			return extension.equalsIgnoreCase(FILEEXTENSION_TCX) || extension.equalsIgnoreCase(FILEEXTENSION_ZIP);
		}
		return false;
	}

	@Override
	public String getDescription() {
		return DESCRIPTION;
	}

	public static String getExtension(File file) {
		String extension = null;
		String filename = file.getName();
		int indexOfLastDot = filename.lastIndexOf('.');

		if (indexOfLastDot > 0 && indexOfLastDot < filename.length() - 1) {
			extension = filename.substring(indexOfLastDot + 1).toLowerCase();
		}
		return extension;
	}
}
