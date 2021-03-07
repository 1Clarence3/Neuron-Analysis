import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        StdDraw.setCanvasSize(600, 600);
        StdDraw.setXscale(100, 400);
        StdDraw.setYscale(400, 100);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.enableDoubleBuffering();
        ArrayList<String> lines = new ArrayList<>();
        File file = new File("2019 10 19_2-1_Cl\\Data.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null)
        {
            lines.add(line);
        }
        for(int i = lines.size()-1; i >= 0; i--)
        {
            if(!(isNumeric(lines.get(i).substring(0,1))))
            {
                lines.remove(i);
            }
        }
        double originx = 0, originy = 0;
        ArrayList<Double> x = new ArrayList<Double>();
        ArrayList<Double> y = new ArrayList<Double>();
        ArrayList<Double> identifier = new ArrayList<Double>();
        ArrayList<Double> radius = new ArrayList<Double>();
        ArrayList<Integer> parents = new ArrayList<Integer>();
        ArrayList<Node> nodes = new ArrayList<Node>();
        for(int i = 0; i < lines.size(); i++)
        {
            String[] temp = lines.get(i).split("\\s+");
            nodes.add(new Node(Double.parseDouble(temp[2]),Double.parseDouble(temp[3]),i+1));
            identifier.add(Double.parseDouble(temp[1]));
            x.add(Double.parseDouble(temp[2]));
            y.add(Double.parseDouble(temp[3]));
            radius.add(Double.parseDouble(temp[5]));
            parents.add(Integer.parseInt(temp[6]));
        }
        for(int i = 1; i < nodes.size(); i++)
        {
            nodes.get(i).setParent(nodes.get(parents.get(i)-1));
        }
        for(int i = identifier.size()-1; i >= 0; i--)
        {
            if(identifier.get(i) == 3)
            {
                identifier.remove(i);
            }
        }
        if(identifier.size()%2 == 0)
        {
            originx = (x.get(identifier.size()/2-1)+x.get(identifier.size()/2))/2;
            originy = (y.get(identifier.size()/2-1)+y.get(identifier.size()/2))/2;
        }
        /*
        for(int i = 0; i < x.size(); i++)
        {
            StdDraw.filledCircle(x.get(i),y.get(i) ,0.6);
        }
        */
        for(int i = 1; i < nodes.size(); i++)
        {
            double slope = (nodes.get(i).getY()-nodes.get(i).getParent().getY())/(nodes.get(i).getX()-nodes.get(i).getParent().getX());
            if(slope == 0.0)
            {
                slope = 0.00001;
            }
            double perpendicularSlope = -1/slope;
            double x1 = nodes.get(i).getParent().getX()+radius.get(i)*Math.sqrt(1/(1+Math.pow(perpendicularSlope,2)));
            double x2 = nodes.get(i).getParent().getX()-radius.get(i)*Math.sqrt(1/(1+Math.pow(perpendicularSlope,2)));
            double x3 = nodes.get(i).getX()-radius.get(i)*Math.sqrt(1/(1+Math.pow(perpendicularSlope,2)));
            double x4 = nodes.get(i).getX()+radius.get(i)*Math.sqrt(1/(1+Math.pow(perpendicularSlope,2)));
            double[] xCoordinates = {x1,x2,x3,x4};
            double y1 = nodes.get(i).getParent().getY()+radius.get(i)*perpendicularSlope*Math.sqrt(1/(1+Math.pow(perpendicularSlope,2)));
            double y2 = nodes.get(i).getParent().getY()-radius.get(i)*perpendicularSlope*Math.sqrt(1/(1+Math.pow(perpendicularSlope,2)));
            double y3 = nodes.get(i).getY()-radius.get(i)*perpendicularSlope*Math.sqrt(1/(1+Math.pow(perpendicularSlope,2)));
            double y4 = nodes.get(i).getY()+radius.get(i)*perpendicularSlope*Math.sqrt(1/(1+Math.pow(perpendicularSlope,2)));
            double[] yCoordinates = {y1,y2,y3,y4};
            StdDraw.polygon(xCoordinates,yCoordinates);

            //StdDraw.line(nodes.get(i).getX(),nodes.get(i).getY(),nodes.get(i).getParent().getX(),nodes.get(i).getParent().getY());
            //StdDraw.show(3);
        }
        StdDraw.show();
        //Get layers for each node -> intersections
        for(int i = 0; i < nodes.size(); i++)
        {
            double distance = Math.sqrt(Math.pow(nodes.get(i).getX()-originx,2)+Math.pow(nodes.get(i).getY()-originy,2));
            nodes.get(i).setDist(distance);
        }
        for(int i = 0; i < nodes.size(); i++)
        {
            if(nodes.get(i).getDist() > 0 && nodes.get(i).getDist() < 50)
            {
                nodes.get(i).setLayer(0);
            }
            else if(nodes.get(i).getDist() > 50 && nodes.get(i).getDist() < 100)
            {
                nodes.get(i).setLayer(1);
            }
            else if(nodes.get(i).getDist() > 100 && nodes.get(i).getDist() < 150)
            {
                nodes.get(i).setLayer(2);
            }
            else if(nodes.get(i).getDist() > 150 && nodes.get(i).getDist() < 200)
            {
                nodes.get(i).setLayer(3);
            }
            else
            {
                nodes.get(i).setLayer(4);
            }
        }
        ArrayList<Node> endpointNodes = new ArrayList<>();
        for(int i = 0; i < nodes.size()-1; i++)
        {
            if(nodes.get(i).getLayer() > nodes.get(i+1).getLayer())
            {
                nodes.get(i).setEndpoint(true);
                endpointNodes.add(nodes.get(i));
            }
        }
        double tempMax = 0;
        int index = 0;
        for(int i = 0; i < endpointNodes.size(); i++)
        {
            if(endpointNodes.get(i).getDist() > tempMax)
            {
                tempMax = endpointNodes.get(i).getDist();
                index = i;
            }
        }
        StdDraw.line(originx,originy,endpointNodes.get(index).getX(),endpointNodes.get(index).getY());
        StdDraw.show();
        double tempMax2 = 0;
        double tempMax3 = 0;
        int rightIndex = 0;
        int leftIndex = 0;
        double rightX = 0;
        double rightY = 0;
        double leftX = 0;
        double leftY = 0;
        double slope = (endpointNodes.get(index).getY()-originy)/(endpointNodes.get(index).getX()-originx);
        double perpendicularSlope = -1/slope;
        double midpointX = (endpointNodes.get(index).getX()+originx)/2;
        double midpointY = (endpointNodes.get(index).getY()+originy)/2;
        double b2 = midpointY-(perpendicularSlope*midpointX);
        //StdDraw.line(-100,perpendicularSlope*(-100)+b2,800,perpendicularSlope*800+b2 );
        for(int i = 0; i < nodes.size(); i++)
        {
            //d=(x-x1)(y2-y1) - (y-y1)(x2-x1)
            if((((nodes.get(i).getX()-originx)*(endpointNodes.get(index).getY()-originy))-((nodes.get(i).getY()-originy)*(endpointNodes.get(index).getX()-originx))) > 0)
            {
                double tempB = nodes.get(i).getY()-(slope*nodes.get(i).getX());
                if(slope > perpendicularSlope)
                {
                    if(tempB*b2 > 0)
                    {
                        if(tempB < 0)
                        {
                            rightX = (tempB+b2)/(slope+perpendicularSlope);
                        }
                        else
                        {
                            rightX = (b2-tempB)/(slope+perpendicularSlope);
                        }
                    }
                    else
                    {
                        if(tempB < 0)
                        {
                            rightX = (tempB+b2)/(slope+perpendicularSlope);
                        }
                        else
                        {
                            rightX = (b2-tempB)/(slope+perpendicularSlope);
                        }
                    }
                }
                else
                {
                    if(tempB*b2 > 0)
                    {
                        if(tempB < 0)
                        {
                            rightX = (tempB+b2)/(slope-perpendicularSlope);
                        }
                        else
                        {
                            rightX = (b2-tempB)/(slope-perpendicularSlope);
                        }
                    }
                    else
                    {
                        if(tempB < 0)
                        {
                            rightX = (tempB+b2)/(slope-perpendicularSlope);
                        }
                        else
                        {
                            rightX = (b2-tempB)/(slope-perpendicularSlope);
                        }
                    }
                }
                rightY = slope*rightX+tempB;
                nodes.get(i).setMidpointDist(Math.sqrt(Math.pow(rightX-midpointX,2)+Math.pow(rightY-midpointY,2)));
                if(nodes.get(i).getMidpointDist() > tempMax2)
                {
                    tempMax2 = nodes.get(i).getMidpointDist();
                    rightIndex = i;
                }
            }
            else if((((nodes.get(i).getX()-originx)*(endpointNodes.get(index).getY()-originy))-((nodes.get(i).getY()-originy)*(endpointNodes.get(index).getX()-originx))) < 0)
            {
                double tempB = nodes.get(i).getY()-(slope*nodes.get(i).getX());
                if(slope > perpendicularSlope)
                {
                    if(tempB*b2 > 0)
                    {
                        if(tempB < 0)
                        {
                            leftX = (tempB+b2)/(slope+perpendicularSlope);
                        }
                        else
                        {
                            leftX = (b2-tempB)/(slope+perpendicularSlope);
                        }
                    }
                    else
                    {
                        if(tempB < 0)
                        {
                            leftX = (tempB+b2)/(slope+perpendicularSlope);
                        }
                        else
                        {
                            leftX = (b2-tempB)/(slope+perpendicularSlope);
                        }
                    }
                }
                else
                {
                    if(tempB*b2 > 0)
                    {
                        if(tempB < 0)
                        {
                            leftX = (tempB+b2)/(slope-perpendicularSlope);
                        }
                        else
                        {
                            leftX = (b2-tempB)/(slope-perpendicularSlope);
                        }
                    }
                    else
                    {
                        if(tempB < 0)
                        {
                            leftX = (tempB+b2)/(slope-perpendicularSlope);
                        }
                        else
                        {
                            leftX = (b2-tempB)/(slope-perpendicularSlope);
                        }
                    }
                }
                leftY = slope*leftX+tempB;
                nodes.get(i).setMidpointDist(Math.sqrt(Math.pow(leftX-midpointX,2)+Math.pow(leftY-midpointY,2)));
                if(nodes.get(i).getMidpointDist() > tempMax3)
                {
                    tempMax3 = nodes.get(i).getMidpointDist();
                    leftIndex = i;
                }
            }
        }

        double yInt1 = nodes.get(rightIndex).getY()-slope*nodes.get(rightIndex).getX();
        double yInt2 = nodes.get(leftIndex).getY()-slope*nodes.get(leftIndex).getX();
        //StdDraw.line(-100,slope*(-100)+yInt1 ,1000 ,slope*1000+yInt1 );
        //StdDraw.line(-100,slope*(-100)+yInt2 ,1000 ,slope*1000+yInt2 );
        double correctX1 = (b2-yInt1)/(slope-perpendicularSlope);
        double correctY1 = perpendicularSlope*correctX1+b2;
        double correctX2 = (b2-yInt2)/(slope-perpendicularSlope);
        double correctY2 = perpendicularSlope*correctX2+b2;
        StdDraw.line(midpointX,midpointY,correctX1,correctY1);
        StdDraw.line(midpointX,midpointY,correctX2,correctY2);
        double yValue = Math.sqrt(Math.pow(correctX1-midpointX,2)+Math.pow(correctY1-midpointY,2))+Math.sqrt(Math.pow(correctX2-midpointX,2)+Math.pow(correctY2-midpointY,2));
        double xValue = Math.sqrt(Math.pow(endpointNodes.get(index).getX()-originx,2)+Math.pow(endpointNodes.get(index).getY()-originy,2));
        yValue = Math.round(yValue*100.00)/100.000;
        xValue = Math.round(xValue*100.00)/100.000;
        Font font = new Font("Arial", Font.BOLD, 16);
        StdDraw.setFont(font);
        StdDraw.text(endpointNodes.get(index).getX()+30,endpointNodes.get(index).getY()+10,xValue+"μm");
        StdDraw.text(correctX2,correctY2,yValue+"μm");
        StdDraw.show();
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
