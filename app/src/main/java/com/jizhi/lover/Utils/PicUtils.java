package com.jizhi.lover.Utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.jizhi.lover.R;

import java.io.File;

/**
 * Created by zheng_liu on 2018/3/14.
 */

public class PicUtils {
    public static Drawable getProfilePictureDrawable(Context context,boolean isAdded) {
        Drawable pictureDrawable;
        try {
            if(isAdded){
                File pictureFile = new File(PhotoBitmapUtils.getPhotoFileName(context)
                        + "/" +"User.jpg");
                if (pictureFile.exists()) {
                    pictureDrawable = Drawable.createFromPath(pictureFile.getAbsolutePath());
                } else {
                    pictureDrawable = context.getDrawable(R.mipmap.ic_person_picture_default);
                }
            }else  pictureDrawable = context.getDrawable(R.mipmap.ic_person_picture_default);
        } catch (NullPointerException e) {
            pictureDrawable = context.getDrawable(R.mipmap.ic_person_picture_default);
        }
        return pictureDrawable;
    }
}
