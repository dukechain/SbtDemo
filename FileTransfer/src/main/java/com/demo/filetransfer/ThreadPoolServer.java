package com.demo.filetransfer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chenxu on 22.09.16.
 */
public class ThreadPoolServer extends Server{
    private ExecutorService exec = Executors.newFixedThreadPool(2);

    @Override
    protected void readData(final SelectionKey key) throws IOException {
        // 移除掉这个key的可读事件，已经在线程池里面处理
        key.interestOps(key.interestOps() & (~SelectionKey.OP_READ));
        exec.execute(new Runnable() {
            @Override
            public void run() {
                ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
                FileChannel fileChannel = fileMap.get(key);
                buffer.clear();
                SocketChannel socketChannel = (SocketChannel) key.channel();
                int num = 0;
                try {
                    while ((num = socketChannel.read(buffer)) > 0) {
                        buffer.flip();
                        // 写入文件
                        fileChannel.write(buffer);
                        buffer.clear();
                    }
                } catch (IOException e) {
                    key.cancel();
                    e.printStackTrace();
                    return;
                }
                // 调用close为-1 到达末尾
                if (num == -1) {
                    try {
                        fileChannel.close();
                        System.out.println("上传完毕");
                        buffer.put((socketChannel.getRemoteAddress() + "上传成功").getBytes());
                        buffer.clear();
                        socketChannel.write(buffer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // 只有调用cancel才会真正从已选择的键的集合里面移除，否则下次select的时候又能得到
                    // 一端close掉了，其对端仍然是可读的，读取得到EOF，返回-1
                    key.cancel();
                    return;
                }
                // Channel的read方法可能返回0，返回0并不一定代表读取完了。
                // 工作线程结束对通道的读取，需要再次更新键的ready集合，将感兴趣的集合重新放在里面
                key.interestOps(key.interestOps() | SelectionKey.OP_READ);
                // 调用wakeup，使得选择器上的第一个还没有返回的选择操作立即返回即重新select
                key.selector().wakeup();
            }
        });
    }


    public static void main(String[] args) throws IOException {
        ThreadPoolServer server = new ThreadPoolServer();
        server.startServer();
    }
}
