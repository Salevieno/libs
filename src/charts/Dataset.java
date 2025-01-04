package charts;

import java.util.ArrayList;
import java.util.List;

public class Dataset
{
	private final List<Double> xData ;
	private final List<Double> yData ;
	
	public Dataset()
	{
		xData = new ArrayList<>() ;
		yData = new ArrayList<>() ;
	}
	
	public void addPoint(double x, double y)
	{		
		xData.add(x) ;
		yData.add(y) ;
	}
	
	public void addPoint(int x, int y) { addPoint((double) x, (double) y) ;}
	
	public void addPointUpToSize(int x, int y, int maxSize)
	{
		if (xData.isEmpty() | yData.isEmpty()) { return ;}

		if (maxSize <= xData.size())
		{
			xData.remove(0) ;
			yData.remove(0) ;
		}

		addPoint(x, y) ;
	}
	
	public void addYData(double y) { addPoint((double) xData.size(), y) ;}
	
	public void addYDataUpToSize(double y, int maxSize)
	{
		if (maxSize <= xData.size())
		{
			xData.remove(0) ;
			yData.remove(0) ;
		}

		addYData(y) ;
	}
	
	public void addYData(List<Double> y)
	{
		if (y == null) { return ;}
		if (y.isEmpty()) { return ;}

		for (int i = 0 ; i <= y.size() - 1 ; i += 1)
		{
			addPoint((double) i, y.get(i)) ;
		}
	}

	public List<Double> getX() { return xData ;}
	public List<Double> getY() { return yData ;}
}
