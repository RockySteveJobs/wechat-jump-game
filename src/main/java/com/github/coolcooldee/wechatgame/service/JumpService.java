package com.github.coolcooldee.wechatgame.service;
import com.github.coolcooldee.wechatgame.tools.android.AdbToolHelper;
import com.github.coolcooldee.wechatgame.tools.log.Log;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 跳跃的核心逻辑
 *
 * @Description
 * @author Dee1024 <coolcooldee@gmail.com>
 * @Version 1.0
 * @Since 1.0
 * @Date 2018/1/3
 */

public abstract class JumpService {

    private static double distance2timeRatio = 0;
    private static final String SCREENCAP_PATH = "jumpgame.png";
    private static final Map<String, Double> resolutionMapDistance2timeRatio = new HashMap<String, Double>();
    static {
        resolutionMapDistance2timeRatio.put("1600*2560",0.92);
        resolutionMapDistance2timeRatio.put("1440*2560",1.039);
        resolutionMapDistance2timeRatio.put("1080*1920",1.392);
        resolutionMapDistance2timeRatio.put("720*1280",2.078);
    }
    static Point beginPoint = null;
    static Point endPoint = null;


    /**
     * 进行跳跃，同时等待一会，等到其停止，方便下一步截屏
     * @param beginPoint
     * @param endPoint
     */
    public static boolean jump(Point beginPoint, Point endPoint){
        int d = getDistance(beginPoint, endPoint);
        Log.println("跳跃距离 "+d);
        if(d<100){
            Log.println("距离太小，重新跳跃 "+d);
            return false;
        }
        AdbToolHelper.screentouch(Math.floor(d * getDistance2timeRatio()));
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        return true;
    }


    private static int getDistance(Point a, Point b) {
        double _x = Math.abs(a.x - b.x);
        double _y = Math.abs(a.y - b.y);
        return (int)Math.sqrt(_x*_x+_y*_y);
    }

    public static String genAndGetScreencapPath() {
        AdbToolHelper.screencap();
        return SCREENCAP_PATH;
    }

    public static String getScreencapPath() {
        return SCREENCAP_PATH;
    }

    public static Point getBeginPoint() {
        return beginPoint;
    }

    public static void setBeginPoint(Point beginPoint) {
        JumpService.beginPoint = beginPoint;
        if(beginPoint!=null){
            Log.println("起跳点 (" + beginPoint.getX() + ", " + beginPoint.getY() + ")");
        }
    }

    public static double getDistance2timeRatio() {
        return distance2timeRatio;
    }

    public static void setDistance2timeRatio(double distance2timeRatio) {
        JumpService.distance2timeRatio = distance2timeRatio;
    }

    public static Double getDistance2timeRatioByResolution(String resolution){
        return resolutionMapDistance2timeRatio.get(resolution);
    }

    public static Point getEndPoint() {
        return endPoint;
    }

    public static void setEndPoint(Point endPoint) {
        JumpService.endPoint = endPoint;
        if(endPoint!=null){
            Log.println("目标点 ("+endPoint.getX()+", "+endPoint.getY()+")");
        }

    }
}
