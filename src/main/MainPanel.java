package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JPanel;

import graphics.DrawPrimitives;

public class MainPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private static DrawPrimitives DP = new DrawPrimitives();
	private static final Color darkTheme = new Color(10, 10, 20) ;
	private static final Color lightTheme = new Color(230, 230, 250) ;
	private static Color bgColor = darkTheme ;
	
	public MainPanel(Dimension size)
	{
		this.setPreferredSize(size);
		this.setBackground(bgColor);
	}
	
	public void run()
	{
		DP.drawCircle(new Point(this.getWidth() / 2, this.getHeight() / 2), 15, Color.red, Color.red) ;
	}

	public void switchTheme()
	{
		bgColor = bgColor == darkTheme ? lightTheme : darkTheme ;
		this.setBackground(bgColor);
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		DP.setGraphics((Graphics2D) g) ;
		run() ;
	}

}
