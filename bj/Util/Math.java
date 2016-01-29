package Util;

public class Math
{
    public static int Clamp(int min,int max,int in)
    {
        if(in > max)
        {
            return max;
        }
        else if(in < min)
        {
            return min;
        }
        else
        {
            return in;
        }
    }
}
