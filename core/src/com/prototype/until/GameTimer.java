package com.prototype.until;

import com.badlogic.gdx.Gdx;

public class GameTimer {
    private int s,mn;
    public GameTimer(int s, int mn)
    {
        this.s = s;
        this.mn = mn;

    }
    public void update(){
        s++;
        if(s>=60){
            s=0;
            mn++;
        }

    }

    public String getString(){
        return String.format("%02d:%02d",this.mn,this.s);
    }

    public int getMn() {
        return mn;
    }

    public int getS() {
        return s;
    }
}
