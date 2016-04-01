package com.shinc.duobaohui.bean;

import java.util.ArrayList;

/**
 * 名称：SearchResultBean
 * 作者：zhaopl 时间: 15/11/18.
 * 实现的主要功能：搜索结果承载类；
 */
public class SearchResultBean {

    private String code;

    private String message;

    private ArrayList<HotSearch> hotSearchList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<HotSearch> getHotSearchList() {
        return hotSearchList;
    }

    public void setHotSearchList(ArrayList<HotSearch> hotSearchList) {
        this.hotSearchList = hotSearchList;
    }

    public SearchResultBean(String code, String message, ArrayList<HotSearch> hotSearchList) {
        this.code = code;
        this.message = message;
        this.hotSearchList = hotSearchList;
    }

    @Override
    public String toString() {
        return "HotSearchBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", hotSearchList=" + hotSearchList +
                '}';
    }

    public static class HotSearch {

        private int id;
        private String SearchName;
        private String hotId;

        private int main_id;

        public int getMain_id() {
            return main_id;
        }

        public void setMain_id(int main_id) {
            this.main_id = main_id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSearchName() {
            return SearchName;
        }

        public void setSearchName(String searchName) {
            SearchName = searchName;
        }

        public String getHotId() {
            return hotId;
        }

        public void setHotId(String hotId) {
            this.hotId = hotId;
        }

        public HotSearch(int id, String searchName) {
            this.id = id;
            SearchName = searchName;
        }

        public HotSearch() {
        }

        @Override
        public String toString() {
            return "HotSearch{" +
                    "id='" + id + '\'' +
                    ", SearchName='" + SearchName + '\'' +
                    '}';
        }
    }
}
