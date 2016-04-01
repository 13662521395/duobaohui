package com.shinc.duobaohui.bean;


import java.util.ArrayList;

/**
 * 名称：MenuListBean
 * 作者：zhaopl 时间: 15/11/23.
 * 实现的主要功能：
 */
public class MenuListBean {

    private String code;
    private String msg;
    private ArrayList<CategoryMenu> data;


    public MenuListBean(String code, String msg, ArrayList<CategoryMenu> data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<CategoryMenu> getData() {
        return data;
    }

    public void setData(ArrayList<CategoryMenu> data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "MenuListBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public class CategoryMenu {
        private String id;
        private String cat_name;
        private String img_url;

        public CategoryMenu(String id, String cat_name, String img_url) {
            this.id = id;
            this.cat_name = cat_name;
            this.img_url = img_url;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCat_name() {
            return cat_name;
        }

        public void setCat_name(String cat_name) {
            this.cat_name = cat_name;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        @Override
        public String toString() {
            return "CategoryMenu{" +
                    "id='" + id + '\'' +
                    ", cat_name='" + cat_name + '\'' +
                    ", img_url='" + img_url + '\'' +
                    '}';
        }
    }
}
