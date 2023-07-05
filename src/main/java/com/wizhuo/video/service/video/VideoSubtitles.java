package com.wizhuo.video.service.video;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 给视频添加字幕
 * @author willJo
 * @date 2023年07月05日 15:15
 */
public class VideoSubtitles {
    public static void main(String[] args) {
        String ffmpegPath = "ffmpeg";  // 根据你的实际安装位置修改
        String videoPath = "/com/example/demo/video/video/video.mp4";
        String subtitlesPath = "/com/example/demo/video/video/subtitles.srt";
        String outputPath = "/com/example/demo/video/video/output1.mp4";

        addSubtitlesToVideo(ffmpegPath,videoPath, subtitlesPath, outputPath);
    }

    public static void addSubtitlesToVideo(String ffmpegPath,String videoPath, String subtitlesPath, String outputPath) {
        try {
            // 构造 FFmpeg 命令行
            String[] cmd = {
                    ffmpegPath,
                    "-i", videoPath,
                    "-vf", "subtitles='E:\\video\\subtitles.srt'",
                    outputPath
            };
            // 创建进程并执行命令
            ProcessBuilder builder = new ProcessBuilder(cmd);
            Process process = builder.start();

            // 获取输出信息（可选）
            // BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            // String line;
            // while ((line = reader.readLine()) != null) {
            //     System.out.println(line);
            // }

            // 等待进程执行完成
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("字幕已成功添加到视频！");
            } else {
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                System.out.println("添加字幕时出错，错误代码: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}