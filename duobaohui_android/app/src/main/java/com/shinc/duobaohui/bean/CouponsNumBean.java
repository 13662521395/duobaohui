package com.shinc.duobaohui.bean;

/**
 * @作者: efort
 * @日期: 16/1/12 - 15:29
 * @工程名: duobaohui
 * @类简介:
 */
public class CouponsNumBean {
    private int canUseNum = 0;
    private int userdNum = 0;
    private int overdueNum = 0;

    public CouponsNumBean() {
    }

    public CouponsNumBean(String canUseNum, String userdNum, String overdueNum) {
        this.canUseNum = Integer.parseInt(canUseNum);
        this.userdNum = Integer.parseInt(userdNum);
        this.overdueNum = Integer.parseInt(overdueNum);
    }

    public int getCanUseNum() {
        return canUseNum;
    }

    public void setCanUseNum(int canUseNum) {
        this.canUseNum = canUseNum;
    }

    public int getUserdNum() {
        return userdNum;
    }

    public void setUserdNum(int userdNum) {
        this.userdNum = userdNum;
    }

    public int getOverdueNum() {
        return overdueNum;
    }

    public void setOverdueNum(int overdueNum) {
        this.overdueNum = overdueNum;
    }
}
