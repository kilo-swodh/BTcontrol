package com.example.a45556.btcontrol.utils;

/**
 * Created by 45556 on 2016-12-18.
 */

public class ArrayUtil {
    public static int[][] rate = {{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0}};
    public static int[][] light = {{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0}};
    public static String[] times = new String[]{"0","0","0","0","0","0"};
    public static int[] lastLight = {0,0,0,0,0};

    private static ArrayUtil s = null;
    private ArrayUtil(){}
    public static ArrayUtil getInstance(){
        if(s == null){							//多次判断,稍微提升了用锁旗位的效率
            synchronized(ArrayUtil.class){
                if(s== null)
                    s = new ArrayUtil();     //延迟加载
            }
        }
        return s;
    }
}
