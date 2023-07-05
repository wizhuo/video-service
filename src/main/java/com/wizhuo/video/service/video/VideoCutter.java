package com.wizhuo.video.service.video;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 每5秒剪切一个视频
 * @author willJo
 * @date 2023年07月05日 15:15
 */
public class VideoCutter {
    public static void main(String[] args) {
        String inputVideo = "E:\\video\\video.mp4"; // 输入视频文件路径
        String outputFolder = "E:\\video\\"; // 输出视频文件夹路径
        int duration = 5; // 每个视频的时长（单位：秒）

        // 计算视频总时长（单位：秒）
        long totalDuration = getVideoDuration(inputVideo);

        // 计算要剪切成多少个视频
        int numVideos = (int) Math.ceil(totalDuration / (double) duration);

        System.out.println("视频时长："+totalDuration+",视频个数:"+numVideos);
        // 循环剪切视频
        for (int i = 0; i < numVideos; i++) {
            // 计算剪切的起始时间
            int startTime = i * duration;

            // 构建 ffmpeg 命令
            String[] command = {
                    "ffmpeg",
                    "-i", inputVideo,
                    "-ss", String.valueOf(startTime),
                    "-t", String.valueOf(duration),
                    "-c:v", "copy",
                    "-c:a", "copy",
                    outputFolder + "output_" + i + ".mp4"
            };

            try {
                // 执行命令
                ProcessBuilder pb = new ProcessBuilder(command);
                Process process = pb.start();
                process.waitFor();

                System.out.println("第 " + (i + 1) + " 个视频剪切完成！");
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // 获取视频时长（单位：秒）
    private static long getVideoDuration(String videoPath) {
        try {
            // 构建 ffprobe 命令
            String[] command = {
                    "ffprobe",
                    "-i", videoPath,
                    "-show_entries", "format=duration",
                    "-v", "quiet",
                    "-of", "csv=p=0"
            };

            // 执行命令
            ProcessBuilder pb = new ProcessBuilder(command);
            Process process = pb.start();

            // 读取命令输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String durationString = reader.readLine();

            // 关闭流
            reader.close();

            // 解析时长字符串为长整型
            double duration = Double.parseDouble(durationString);

            return Math.round(duration);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }
}

