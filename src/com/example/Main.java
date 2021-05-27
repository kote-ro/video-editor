package com.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        // -- Linux --
        // задание рабочей директории
        processBuilder.directory(new File("/home/ruslan/dev/video-editor/data"));
        // преобразование avi в mp4
        processBuilder.command("/bin/bash", "-c", "ffmpeg -i example.avi example.mp4");
        // запись снипптов видео в директорию
        processBuilder.command("/bin/bash", "-c", "ffmpeg -i example.mp4 -c copy -map 0 -segment_time 00:00:05 -f segment example%d.mp4");

        try {
            Process process = processBuilder.start();
            StringBuilder output = new StringBuilder();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println(output);
                System.exit(0);
            }else {
                System.out.println("Something is wrong");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
