package com.shinc.duobaohui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liugaopo on 15/9/1.
 */
public class IndexHotIconBean implements Serializable {
    private String id;
    private String bigIconUrl;
    private List<SmallHotProductBean> smallHotProductBeans;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBigIconUrl() {
        return bigIconUrl;
    }

    public void setBigIconUrl(String bigIconUrl) {
        this.bigIconUrl = bigIconUrl;
    }

    public List<SmallHotProductBean> getSmallHotProductBeans() {
        return smallHotProductBeans;
    }

    public void setSmallHotProductBeans(List<SmallHotProductBean> smallHotProductBeans) {
        this.smallHotProductBeans = smallHotProductBeans;
    }

    public class SmallHotProductBean {
        private String id;
        private String topText;
        private String buttomtext;
        private String iconUrl;

        public SmallHotProductBean() {
        }

        public SmallHotProductBean(String id, String topText, String buttomtext, String iconUrl) {
            this.id = id;
            this.topText = topText;
            this.buttomtext = buttomtext;
            this.iconUrl = iconUrl;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTopText() {
            return topText;
        }

        public void setTopText(String topText) {
            this.topText = topText;
        }

        public String getButtomtext() {
            return buttomtext;
        }

        public void setButtomtext(String buttomtext) {
            this.buttomtext = buttomtext;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        @Override
        public String toString() {
            return "SmallHotProductBean{" +
                    "id='" + id + '\'' +
                    ", topText='" + topText + '\'' +
                    ", buttomtext='" + buttomtext + '\'' +
                    ", iconUrl='" + iconUrl + '\'' +
                    '}';
        }
    }
}
