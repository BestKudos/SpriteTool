package com.applers.tool;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.border.BevelBorder;

public class IconListRenderer extends DefaultListCellRenderer {

	/**
	 * @uml.property  name="icons"
	 * @uml.associationEnd  qualifier="value:java.lang.Object javax.swing.ImageIcon"
	 */
	private Map<Object, ImageIcon> icons = null;

	public IconListRenderer(Map<Object, ImageIcon> icons) {
		this.icons = icons;
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {

		// Get the renderer component from parent class

		JLabel label = (JLabel) super.getListCellRendererComponent(list, value,
				index, isSelected, cellHasFocus);

		// Get icon to use for the list item value

		ImageIcon icon = icons.get(value);
		Image img = icon.getImage();
		Image newimg = img.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
		ImageIcon newIcon = new ImageIcon(newimg);

		// Set icon to display for value

		label.setIcon(newIcon);
		label.setBorder(new BevelBorder(BevelBorder.RAISED));
		return label;

	}
}