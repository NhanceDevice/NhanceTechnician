package com.nhance.technician.ui.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * EditText utils
 */
public class EditTextUtils {
    private EditTextUtils() {
        throw new AssertionError();
    }

    /**
     * returns text string from passed EditText
     */
    public static String getText(EditText edit) {
        if (edit.getText() == null) {
            return "";
        }
        return edit.getText().toString().trim();
    }

    /**
     * moves caret to end of text
     */
    public static void moveToEnd(EditText edit) {
        if (edit.getText() == null) {
            return;
        }
        edit.setSelection(edit.getText().toString().length());
    }

    /**
     * returns true if nothing has been entered into passed editor
     */
    public static boolean isEmpty(EditText edit) {
        return TextUtils.isEmpty(getText(edit));
    }

    /**
     * hide the soft keyboard for the passed EditText
     */
    public static void hideSoftInput(EditText edit) {
        if (edit == null) {
            return;
        }
        Context context = edit.getContext();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
        }
    }

    public static boolean isNumeric(String s) {
//        http://stackoverflow.com/questions/12715246/how-to-check-if-a-character-in-a-string-is-a-digit-or-letter
//        http://stackoverflow.com/questions/19988434/android-check-if-string-contains-characters-other-than-0-9
        return s.matches("[-+]?\\d*\\.?\\d+");
    }

    //    http://stackoverflow.com/questions/11376516/change-drawable-color-programmatically
    public static Drawable changeDrawableColor(int drawableRes, int colorRes, Context context) {
        //Convert drawable res to bitmap
        final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableRes);
        final Bitmap resultBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth() - 1, bitmap.getHeight() - 1);
        final Paint p = new Paint();
        final Canvas canvas = new Canvas(resultBitmap);
        canvas.drawBitmap(resultBitmap, 0, 0, p);

        //Create new drawable based on bitmap
        final Drawable drawable = new BitmapDrawable(context.getResources(), resultBitmap);
        drawable.setColorFilter(new
                PorterDuffColorFilter(context.getResources().getColor(colorRes), PorterDuff.Mode.MULTIPLY));
        return drawable;
    }

    /**
     * returns text string from passed TextInputEditText
     */
    public static String getText(TextInputEditText edit) {
        if (edit.getText() == null) {
            return "";
        }
        return edit.getText().toString().trim();
    }

    /**
     * returns text string from passed TextView
     */
    public static String getText(TextView edit) {
        if (edit.getText() == null) {
            return "";
        }
        return edit.getText().toString().trim();
    }
}
