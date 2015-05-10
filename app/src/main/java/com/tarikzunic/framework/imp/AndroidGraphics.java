package com.tarikzunic.framework.imp;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.tarikzunic.framework.Graphics;
import com.tarikzunic.framework.Pixmap;

import java.io.IOException;
import java.io.InputStream;


/**
 * Created by Tarik on 21.4.2015.
 */
public class AndroidGraphics implements Graphics {
    AssetManager assets;
    Bitmap frameBuffer;
    Canvas canvas;
    Paint paint;
    Rect srcRect = new Rect();
    Rect dstRect = new Rect();

    public AndroidGraphics(AssetManager assets, Bitmap frameBuffer) {
        this.assets = assets;
        this.frameBuffer = frameBuffer;
        this.canvas = new Canvas(frameBuffer);
        this.paint = new Paint();
    }

    @Override
    public Pixmap newPixmap(String fileName, PixmapFormat format) {
        Config config = null;
        if (format == PixmapFormat.RGB565) {
            config = Config.RGB_565;
        } else if (format == PixmapFormat.ARGB4444) {
            config = Config.ARGB_4444;
        } else
            config = Config.ARGB_8888;

        Options options = new Options();
        options.inPreferredConfig = config;

        InputStream in = null;
        Bitmap bitmap = null;
        try {
            in = assets.open(fileName);
            bitmap = BitmapFactory.decodeStream(in, null, options);
            if (bitmap == null)
                throw new RuntimeException("Couldn't load bitmap from asset '" + fileName + "'");
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load bitmap from asset '" + fileName + "'");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (bitmap.getConfig() == Config.RGB_565)
            format = PixmapFormat.RGB565;
        else if (bitmap.getConfig() == Config.ARGB_4444)
            format = PixmapFormat.ARGB4444;
        else if (bitmap.getConfig() == Config.ARGB_8888)
            format = PixmapFormat.ARGB8888;

        return new AndroidPixmap(bitmap, format);
    }

    @Override
    public void clear(int color) {
        // Extract R, G, B from 32-bit color
        canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8, (color & 0xff));
    }

    @Override
    public void drawPixel(int x, int y, int color) {
        paint.setColor(color);
        canvas.drawPoint(x, y, paint);
    }

    @Override
    public void drawLine(int x, int y, int x2, int y2, int color) {
        paint.setColor(color);
        canvas.drawLine(x, y, x2, y2, paint);
    }

    @Override
    public void drawRect(int x, int y, int width, int hight, int color) {
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY, int srcWidth, int srcHight) {
        srcRect.left = srcX;
        srcRect.top = srcY;
        srcRect.right = srcX + srcWidth - 1;
        srcRect.bottom = srcY + srcHight - 1;

        dstRect.left = x;
        dstRect.top = y;
        dstRect.right = x + srcWidth - 1;
        dstRect.bottom = y +  srcHight - 1;

        canvas.drawBitmap(((AndroidPixmap) pixmap).bitmap, srcRect, dstRect, null);
    }

    @Override
    public void drawPixmap(Pixmap pixmap, int x, int y) {
        canvas.drawBitmap(((AndroidPixmap) pixmap).bitmap, x, y, null);
    }

    @Override
    public void drawText(String text, int x, int y, Paint paint) {
        canvas.drawText(text, x, y, paint);
    }

    @Override
    public Paint setPaintText(String font, int color, int size) {
        Typeface typeface = Typeface.createFromAsset(assets, font);
        paint.setColor(color);
        paint.setTypeface(typeface);
        paint.setTextSize(size);
        return paint;
    }

    @Override
    public Rect textBounds(String text, Paint paint) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return bounds;
    }

    @Override
    public int getWidth() {
        return frameBuffer.getWidth();
    }

    @Override
    public int getHight() {
        return frameBuffer.getHeight();
    }
}
