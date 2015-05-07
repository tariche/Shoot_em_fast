package com.tarikzunic.shootemfast;

import com.tarikzunic.framework.FileIO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by Tarik on 7.5.2015.
 */
public class Settings {
    public static boolean soundEabled = true;
    public static int[] highScore = new int[]{100, 80, 50, 30, 10};

    public static void load(FileIO files) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(files.readFile(".shootemfast")));
            soundEabled = Boolean.parseBoolean(in.readLine());
            for (int i = 0; i < 5; i++) {
                highScore[i] = Integer.parseInt(in.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public static void save(FileIO files) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(files.writeAsset(".shootemfast")));
            out.write(Boolean.toString(soundEabled));
            for (int i = 0; i < 5; i++) {
                out.newLine();
                out.write(Integer.toString(highScore[i]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null)
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public static void addScore(int score) {
        
    }
}
