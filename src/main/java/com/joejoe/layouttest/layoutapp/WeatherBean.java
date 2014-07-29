package com.joejoe.layouttest.layoutapp;


public class WeatherBean {

    /**
     * 省名称
     */
    private String quName;
    /**
     * 城市名称
     */
    private String cityname;
    /**
     * 天气状况
     */
    private String stateDetailed;
    /**
     * 最高温度
     */
    private String tem1;
    /**
     * 最低温度
     */
    private String tem2;
    /**
     * 风向
     */
    private String windState;



    public String getWindState() {
        return windState;
    }

    public void setWindState(String windState) {
        this.windState = windState;
    }

    public String getQuName() {
        return quName;
    }

    public void setQuName(String quName) {
        this.quName = quName;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getStateDetailed() {
        return stateDetailed;
    }

    public void setStateDetailed(String stateDetailed) {
        this.stateDetailed = stateDetailed;
    }

    public String getTem1() {
        return tem1;
    }

    public void setTem1(String tem1) {
        this.tem1 = tem1;
    }

    public String getTem2() {
        return tem2;
    }

    public void setTem2(String tem2) {
        this.tem2 = tem2;
    }


}
