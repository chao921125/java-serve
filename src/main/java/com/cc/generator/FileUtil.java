package com.cc.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {
    public static void writeToFile(String filePath, String content) throws IOException {
        File file = new File(filePath);
        File parent = file.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        }
    }
} 