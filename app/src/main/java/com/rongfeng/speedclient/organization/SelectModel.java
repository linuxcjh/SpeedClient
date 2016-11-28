package com.rongfeng.speedclient.organization;

import java.io.Serializable;

/**
 * Created by tl on 15/11/8.
 */
public class SelectModel  implements Serializable {

    private boolean isChecked;
    private String name;
    private String id;
    private String letter;

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }


}
