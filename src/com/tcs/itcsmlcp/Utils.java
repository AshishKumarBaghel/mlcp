package com.tcs.itcsmlcp;

import java.io.InputStream;
import java.io.OutputStream;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class Utils {
	public static final String BOLD_FONT_PATH = "fonts/Montserrat-Bold.ttf";
	public static final String REGULAR_FONT_PATH = "fonts/Montserrat-Regular.ttf";
	public static Typeface regularFont, boldFont;

	public static void loadFonts() {
		regularFont = Typeface.createFromAsset(MyApp.getContext().getAssets(),
				Utils.REGULAR_FONT_PATH);
		boldFont = Typeface.createFromAsset(MyApp.getContext().getAssets(),
				Utils.REGULAR_FONT_PATH);
	}

	@SuppressLint("DefaultLocale")
	public static void setFontAllView(ViewGroup vg) {

		for (int i = 0; i < vg.getChildCount(); ++i) {

			View child = vg.getChildAt(i);

			if (child instanceof ViewGroup) {

				setFontAllView((ViewGroup) child);

			} else if (child != null) {
				Typeface face;
				if (child.getTag() != null
						&& child.getTag().toString().toLowerCase()
								.equals("bold")) {
					face = boldFont;
				} else {
					face = regularFont;
				}
				if (child instanceof TextView) {
					TextView textView = (TextView) child;
					textView.setTypeface(face);
				} else if (child instanceof EditText) {
					EditText editView = (EditText) child;
					editView.setTypeface(face);
				} else if (child instanceof RadioButton) {
					RadioButton radioView = (RadioButton) child;
					radioView.setTypeface(face);
				} else if (child instanceof CheckBox) {
					CheckBox checkboxView = (CheckBox) child;
					checkboxView.setTypeface(face);
				}

			}

		}
	}

	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}
	
	public static Bitmap GetImageFromAssets(Context context,String imagePath){
		Bitmap bmp = null;
		try {
	        InputStream bitmap= context.getAssets().open(imagePath);
	        bmp = BitmapFactory.decodeStream(bitmap);

	    } catch (Exception e1) {
	    	Log.d("Application Find", e1.getMessage());
	    }
		return bmp;
	}
}
