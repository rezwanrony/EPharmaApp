package com.appersden.epharma;

/**
 * Created by azeR on 12/5/2018.
 */

public interface DrawableClickListener {

    public static enum DrawablePosition { TOP, BOTTOM, LEFT, RIGHT };
    public void onClick(DrawablePosition target);
}
