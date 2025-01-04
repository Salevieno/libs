package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Main extends JFrame
{
	private static final long serialVersionUID = 1L;

	private static final String windowTitle = "Titulo da janela" ;
	private static final Dimension windowSize = new Dimension(800, 600) ;
	private static final MainPanel mainPanel = new MainPanel(new Dimension(windowSize.width, 500)) ;
	private static final HeaderPanel headerPanel = new HeaderPanel(new Dimension(windowSize.width, 50)) ;
	private static final Color bgColor = new Color(255, 255, 255) ;

	
	public Main()
	{
		this.setTitle(windowTitle);
		this.setSize(windowSize);
		this.setLayout(new BorderLayout());
		this.getContentPane().setBackground(bgColor);
		this.add(headerPanel, BorderLayout.NORTH);
		this.add(mainPanel, BorderLayout.CENTER);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public static void switchTheme()
	{
		headerPanel.switchTheme() ;
		mainPanel.switchTheme() ;
	}
	
	public static void main(String[] args)
	{
		new Main() ;
	}
}
