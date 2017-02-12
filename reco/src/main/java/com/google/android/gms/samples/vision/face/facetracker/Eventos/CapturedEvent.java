package com.google.android.gms.samples.vision.face.facetracker.Eventos;

import java.util.EventObject;

/**
 * Created by i7 on 11-02-17.
 */

public class CapturedEvent extends EventObject{
    Object data;
    public CapturedEvent(Object aThis, Object Data){
        super(aThis);
        this.data=Data;
    }

    public Object getData() {
        return data;
    }
}
