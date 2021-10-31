package com.impact.client.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;

public class GuiIntegerBox extends GuiTextField {
	private final int maxValue;
	
	public GuiIntegerBox(FontRenderer fontRenderer, int x, int y, int width, int height) {
		this(fontRenderer, x, y, width, height, Integer.MAX_VALUE);
	}
	
	public GuiIntegerBox(FontRenderer fontRenderer, int x, int y, int width, int height, int maxValue) {
		super(fontRenderer, x, y, width, height);
		this.maxValue = maxValue;
	}
	
	public void writeText(String selectedText) {
		String original = getText();
		super.writeText(selectedText);
		try {
			int i = Integer.parseInt(getText());
			if (i > maxValue) {
				setText(String.valueOf(maxValue));
			} else if (i < 0) {
				setText("0");
			}
		} catch (NumberFormatException e) {
			setText(original);
		}
	}
	
	public void setText(String s) {
		try {
			int i = Integer.parseInt(s);
			if (i > maxValue) {
				s = String.valueOf(maxValue);
			} else if (i < 0) {
				s = "0";
			}
		} catch (NumberFormatException e) {
			s = String.valueOf(maxValue);
		}
		super.setText(s);
	}
}