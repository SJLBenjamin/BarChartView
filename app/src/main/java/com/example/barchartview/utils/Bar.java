package com.example.barchartview.utils;

public class Bar {
    int left;
    int top;
    int right;
    int bottom;
    float value;//柱状条原始数据的大小
    float transformedValue;//原始数据转换为像素大小
    int currentTop;//当前的顶部

    public int getCurrentTop() {
        return currentTop;
    }

    public void setCurrentTop(int currentTop) {
        this.currentTop = currentTop;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getTransformedValue() {
        return transformedValue;
    }

    public void setTransformedValue(float transformedValue) {
        this.transformedValue = transformedValue;
    }
}
