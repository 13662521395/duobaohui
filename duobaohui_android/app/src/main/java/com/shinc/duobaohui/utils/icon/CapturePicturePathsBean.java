package com.shinc.duobaohui.utils.icon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vince on 15/9/15.
 */
public class CapturePicturePathsBean {

    List<String> picturePathsList;

    public CapturePicturePathsBean() {
        picturePathsList = new ArrayList<>();
    }

    public List<String> getPicturePathsList() {
        return picturePathsList;
    }

    public void setPicturePathsList(ArrayList<String> picturePathsList) {
        this.picturePathsList = picturePathsList;
    }

    public void addPathToList(String path){
        picturePathsList.add(path);
    }
    public String getPicPath(int position){
        return picturePathsList.get(position);
    }

    public int getListSize(){
        return picturePathsList.size();
    }
}
