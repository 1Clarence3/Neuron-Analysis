public class Node {
    private Node parent;
    private double x;
    private double y;
    private int num;
    private int layer;
    private double dist;
    private double midpointDist;
    private boolean endpoint;

    public Node(double x1, double y1, int num1)
    {
        x = x1;
        y = y1;
        num = num1;
    }

    public void setParent(Node parent1)
    {
        parent = parent1;
    }

    public void setDist(double dist1)
    {
        dist = dist1;
    }

    public int getNum()
    {
        return num;
    }

    public double getDist()
    {
        return dist;
    }

    public Node getParent()
    {
        return parent;
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    public void setLayer(int layer1)
    {
        layer = layer1;
    }

    public int getLayer()
    {
        return layer;
    }

    public void setEndpoint(boolean endpoint1)
    {
        endpoint = endpoint1;
    }

    public boolean getEndpoint()
    {
        return endpoint;
    }

    public void setMidpointDist(double midpointDist)
    {
        this.midpointDist = midpointDist;
    }

    public double getMidpointDist()
    {
        return midpointDist;
    }
}
