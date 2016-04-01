package com.shinc.duobaohui.model;

/**
 * Created by liugaopo on 15/10/7.
 */
public interface DuoBaoRecrodModelInterface {
    void getDuoBaoDate(int page);

    /*dev V1.1版*/
    void getDuoBaoRecrod(String flag, int page);

    void getDuoBaoTadNumber();
}
