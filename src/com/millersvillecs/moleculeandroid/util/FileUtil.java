package com.millersvillecs.moleculeandroid.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author William Gervasio
 */
public class FileUtil {

    /**
     * Parses a file for the text
     * @param fileLocation
     * @return The text as a String
     */
    public static String readText(String fileLocation) {
        StringBuilder shaderString = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileLocation));
            String line;
            while ((line = reader.readLine()) != null) {
                shaderString.append(line).append("\n");
            }
            reader.close();
        } catch (FileNotFoundException e) {
            
        } catch (IOException e) {
            
        }

        return shaderString.toString();
    }
}