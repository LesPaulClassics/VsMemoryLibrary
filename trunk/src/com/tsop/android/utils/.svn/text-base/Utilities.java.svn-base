package com.tsop.android.utils;

import android.util.DisplayMetrics;

public class Utilities 
{
    public static int scalePixels(DisplayMetrics metrics, int dips)
    {
    	final float densityScale = metrics.density;
    	final float resScale = (320.f / metrics.widthPixels);
    	return (int)(dips / (densityScale * resScale));
    }

    public static int scalePixels2(DisplayMetrics metrics, int dips)
    {
//    	final float densityScale = metrics.density;
    	final float resScale = (320.f / metrics.widthPixels);
    	return (int) (dips / resScale);
//    	return (int)(dips / (densityScale * resScale));
    }

}
