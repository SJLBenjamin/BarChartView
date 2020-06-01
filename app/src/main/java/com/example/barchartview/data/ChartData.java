package com.example.barchartview.data;

public class ChartData {
    //柱状图数据列表
    private float[] mDataList;
    //水平方向X轴坐标
    private String[] mHorizontalAxis;

    public float[] getDataList() {
        return mDataList;
    }

    public void setDataList(float[] mDataList) {
        this.mDataList = mDataList;
    }

    public String[] getHorizontalAxis() {
        return mHorizontalAxis;
    }

    public void setHorizontalAxis(String[] mHorizontalAxis) {
        this.mHorizontalAxis = mHorizontalAxis;
    }
}
