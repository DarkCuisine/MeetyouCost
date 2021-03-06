package com.meiyou.meetyoucost;

import android.os.Looper;

import com.meiyou.meetyoucost.ui.LogView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class CostLog {

    public static Map<String, Long> sStartTime = new HashMap<>();
    public static Map<String, Long> sEndTime = new HashMap<>();

    /**
     * 设置开始时间，内部使用
     * @param methodName
     * @param time
     */
    public static void setStartTime(String methodName, long time) {
        sStartTime.put(methodName, time);
    }
    /**
     * 设置结束时间，内部使用
     * @param methodName
     * @param time
     */
    public static void setEndTime(String methodName, long time) {
        sEndTime.put(methodName, time);
    }

    /**
     * 获取某方法耗时结果，内部使用
     * @param methodName
     * @return
     */
    public static String getCostTime(String methodName) {
        long start = sStartTime.get(methodName);
        long end = sEndTime.get(methodName);
        long cost = Long.valueOf(end - start) / (1000 * 1000) ;
        String log = "Usopp MeetyouCost Method:==> " + methodName + " ==>Cost:" + cost+ " ms";
        try{

            //监听
            if (MeetyouCost.mOnLogListener != null) {
                MeetyouCost.mOnLogListener.log(log,methodName,cost);
            }
            //收集日志
            if(MeetyouCost.isOpenLogCache)
                MeetyouCost.mLogCache.add(log);
            //显示UI
            if(MeetyouCost.isOpenLogUI){
                if(Thread.currentThread() == Looper.getMainLooper().getThread()){
                    if(logView==null){
                        logView = new LogView(MeetyouCost.mContext);
                    }
                    if(cost>50){
                        String costLogs = "<font color='#ff74b9'>"+cost+"</font>";
                        logView.appendLog(methodName+":"+costLogs+" ms");
                    }else {
                        logView.appendLog(methodName+":"+cost+" ms");
                    }
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return log;
    }

    private static LogView logView;

}
