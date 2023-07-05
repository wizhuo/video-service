package com.wizhuo.video.service.video;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 多个视频合成一个视频
 *
 * @author willJo
 * @date 2023年07月05日 15:44
 */

public class VideoConcatenator {
    public static void main(String[] args) {
        String inputFolder = "input/"; // 输入视频文件夹路径
        String outputFile = "output.mp4"; // 输出视频文件路径

        // 构建 ffmpeg 命令
        String[] command = {
                "ffmpeg",
                "-f", "concat",
                "-safe", "0",
                "-i", getInputFileList(inputFolder),
                "-c", "copy",
                outputFile
        };

        try {
            // 执行命令
            ProcessBuilder pb = new ProcessBuilder(command);
            Process process = pb.start();
            process.waitFor();

            System.out.println("视频合成完成！");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 获取输入视频文件列表
    private static String getInputFileList(String inputFolder) {
        try {
            // 构建文件列表
            StringBuilder fileList = new StringBuilder();
            String[] fileExtensions = {".mp4", ".avi", ".mov"}; // 可根据实际需要修改支持的视频格式

            File folder = new File(inputFolder);
            File[] files = folder.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        for (String extension : fileExtensions) {
                            if (file.getName().toLowerCase().endsWith(extension)) {
                                fileList.append("file '")
                                        .append(file.getAbsolutePath())
                                        .append("'\n");
                                break;
                            }
                        }
                    }
                }
            }

            // 生成文件列表文本文件
            File listFile = new File("input.txt");
            FileWriter writer = new FileWriter(listFile);
            writer.write(fileList.toString());
            writer.close();

            return listFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
