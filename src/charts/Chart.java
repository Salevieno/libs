package charts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import graphics.Align;
import graphics.DrawPrimitives;
import utilities.Util;

public class Chart
{
	private Point pos ;
	private String title ;
	private int size ;
	private Color titleColor ;
	private Color lineColor ;
	private List<Dataset> data ;
	private double maxYEver ;
	private List<Color> dataSetColor ;
	public static final List<Color> stdColors ;
	private static final Font textFont ;
	
	static
	{
		stdColors = Arrays.asList(Color.cyan, Color.green) ;
		textFont = new Font("SansSerif", Font.BOLD, 11) ;
	}
	
	public Chart(Point pos, String title, int size, Color titleColor, Color lineColor, List<Color> dataSetColor)
	{
		this.pos = pos;
		this.title = title;
		this.size = size;
		this.titleColor = titleColor;
		this.lineColor = lineColor;
		this.dataSetColor = dataSetColor;
		data = new ArrayList<>() ;
	}
	
	public Chart(Point pos, String title, int size, List<Color> dataSetColor)
	{
		this(pos, title, size, Color.white, Color.black, dataSetColor) ;
	}
	
	public Chart(Point pos, String title, int size, Color dataSetColor)
	{
		this(pos, title, size, Color.white, Color.black, Arrays.asList(dataSetColor)) ;
	}
	
	public Chart(Point pos, String title, int size)
	{
		this(pos, title, size, Color.white, Color.black, stdColors) ;
	}

	public void addDataset(Dataset newDataset)
	{
		if (newDataset == null) { return ;}
		
		data.add(newDataset) ;
		
		if (newDataset.getY().isEmpty()) { return ;}
		
		maxYEver = Collections.max(newDataset.getY()) ;
	}
	
	public void addDatasets(List<Dataset> newDatasets)
	{
		if (newDatasets == null) { return ;}
		if (newDatasets.isEmpty()) { return ;}
		
		newDatasets.forEach(dataset -> addDataset(dataset)) ;
	}

	public void updateDataset(Dataset newDataset)
	{
		if (newDataset == null) { return ;}
		
		data.set(data.indexOf(newDataset), newDataset) ;
		
		maxYEver = Collections.max(newDataset.getY()) ;
	}
	
	public void updateMaxYEver()
	{
		if (data == null) { return ;}
		if (data.isEmpty()) { return ;}
		
		maxYEver = 0 ;
		data.forEach(dataset -> {
			if (!dataset.getY().isEmpty())
			{
				double newMax = Collections.max(dataset.getY()) ;
				if (maxYEver < newMax)
				{
					maxYEver = newMax ;
				}
			}
		}) ;
	}
	
	public void displayData(DrawPrimitives DP)
	{
		if (data == null) { return ;}
		if (data.isEmpty()) { return ;}
		
		Point titlePos = new Point (pos.x - 5, pos.y - size) ;
		DP.drawText(titlePos, Align.centerRight, 0, String.valueOf(Util.Round(maxYEver, 2)), textFont, Color.white);
		
		for (int i = 0 ; i <= data.size() - 1 ; i += 1)
		{
			Color color = i <= dataSetColor.size() - 1 ? dataSetColor.get(i) : Color.white ;
			displayDataset(data.get(i), color, DP) ;
		}
	}
	
	public void displayDataset(Dataset dataset, Color color, DrawPrimitives DP)
	{
		List<Integer> xPos = new ArrayList<>() ;
		List<Integer> yPos = new ArrayList<>() ;
		for (int i = 0; i <= dataset.getY().size() - 1; i += 1)
		{
			if (2 <= dataset.getY().size())
			{
				xPos.add(pos.x + size * i / (dataset.getY().size() - 1)) ;
			}
			else
			{
				xPos.add(pos.x + size * i) ;
			}
			yPos.add((pos.y - (int) (size * dataset.getY().get(i) / maxYEver))) ;
		}
		DP.drawPolyLine(xPos, yPos, 2, color);
	}


	public void drawGrid(Point initPos, Point finalPos, int NumSpacing, Color lineColor, DrawPrimitives DP)
	{
		int stroke = 1;
		Dimension length = new Dimension(finalPos.x - initPos.x, initPos.y - finalPos.y);
		for (int i = 0; i <= NumSpacing - 1; i += 1)
		{
			// horizontal lines
			Point hPoint1 = new Point(initPos.x, initPos.y - (i + 1) * length.height / NumSpacing);
			Point hPoint2 = new Point(initPos.x + length.width, initPos.y - (i + 1) * length.height / NumSpacing);
			DP.drawLine(hPoint1, hPoint2, stroke, lineColor);

			// vertical lines
			Point vPoint1 = new Point(initPos.x + (i + 1) * length.width / NumSpacing, initPos.y);
			Point vPoint2 = new Point(initPos.x + (i + 1) * length.width / NumSpacing, initPos.y - length.height);
			DP.drawLine(vPoint1, vPoint2, stroke, lineColor);
		}
	}

	public void drawArrow(Point Pos, int size, double theta, boolean fill, double tipSize, Color color, DrawPrimitives DP)
	{
		double open = 0.8;
		int ax1 = (int) (Pos.x - open * size * Math.cos(theta) - tipSize / 3.5 * Math.sin(theta));
		int ay1 = (int) (Pos.y + open * size * Math.sin(theta) - tipSize / 3.5 * Math.cos(theta));
		int ax2 = Pos.x;
		int ay2 = Pos.y;
		int ax3 = (int) (Pos.x - open * size * Math.cos(theta) + tipSize / 3.5 * Math.sin(theta));
		int ay3 = (int) (Pos.y + open * size * Math.sin(theta) + tipSize / 3.5 * Math.cos(theta));
		DP.drawPolygon(new int[] { ax1, ax2, ax3 }, new int[] { ay1, ay2, ay3 }, 1, color);
	}
	
	public void display(DrawPrimitives DP)
	{
		int arrowSize = 8 * size / 100;
		drawGrid(pos, new Point (pos.x + size, pos.y - size), 10, new Color(250, 250, 250, 50), DP);
		DP.drawText(new Point (pos.x, (int) (pos.y - size - 13)), Align.centerLeft, 0, title, textFont, titleColor);
		DP.drawLine(pos, new Point (pos.x, (int) (pos.y - size - arrowSize)), 2, lineColor);
		DP.drawLine(pos, new Point ((int) (pos.x + size + arrowSize), pos.y), 2, lineColor);
		drawArrow(new Point (pos.x + size + arrowSize, pos.y), arrowSize, 0, false, 0.4 * arrowSize, lineColor, DP);
		drawArrow(new Point (pos.x, pos.y - size - arrowSize), arrowSize, Math.PI / 2, false, 0.4 * arrowSize, lineColor, DP);
		displayData(DP) ;
		//DrawPolyLine(new int[] {pos.x - asize, pos.x, pos.x + asize}, new int[] {(int) (pos.y - 1.1*size) + asize, (int) (pos.y - 1.1*size), (int) (pos.y - 1.1*size) + asize}, 2, ColorPalette[4]);
		//DrawPolyLine(new int[] {(int) (pos.x + 1.1*size - asize), (int) (pos.x + 1.1*size), (int) (pos.x + 1.1*size - asize)}, new int[] {pos.y - asize, pos.y, pos.y + asize}, 2, ColorPalette[4]);
	}
	
}
