package Chapter301ModelAndPrototype.Section201NetworkProgramming.JavaSocket.TCPSocket;



import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 聊天室服务器
 */
public class ChatRoomServer {
    //准备容器，存储客户端
    private static List<Chanel> list = new CopyOnWriteArrayList<>();
    
    
    
    //主函数
    public static void main(String[] args) throws IOException {
        int num = 0;
        //创建服务器
        ServerSocket server = null;
        try {
            server = new ServerSocket(8888);
            System.out.println("服务器已启动，端口号---->8888");
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(true)
        {
            //接收客户端
            Socket client = server.accept();
            num++;
            System.out.println(num+"个服务器已连接");
            //存储到容器
            Chanel chanel = new Chanel(client);
            list.add(chanel);
            //开启线程
            new Thread(chanel).start();
        }
    }
    
    
    
    //信号通道
    static class Chanel implements Runnable{
        private Socket clinet = null;//客户端
        private boolean flag = true;//控制运行
        private DataInputStream clientR = null; //读信息
        private DataOutputStream clientW = null;//写信息
        private String name; //客户端姓名
        public Chanel(Socket clinet) {
            this.clinet = clinet;
            //初始化
            try {
                this.clientR = new DataInputStream(clinet.getInputStream());
                this.clientW = new DataOutputStream(clinet.getOutputStream());
                this.name = clientR.readUTF();
                this.send("欢迎来到聊天室！");
            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println("初始化失败！");
                flag = false;
            }
        }
        
        @Override
        public void run() {
            while(flag)
            {
                sendOthers();
            }
        }
        //发送
        private void send(String msg)
        {
            
            //判断是否为空
            try {
                //System.out.println("send ---- "+msg);
                clientW.writeUTF(msg);
                clientW.flush();
            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println("发送失败！");
                flag = false;
            }
        }
        
        //接收
        private String receive()
        {
            String msg = null;
            try {
                msg = clientR.readUTF();
            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println("接收失败！");
                flag = false;
            }
            return msg;
        }
        
        //发送群消息
        private void sendOthers()
        {
            String msg = receive();
            for(Chanel other: list)
            {
                if(other == this)
                    continue;
                else
                {
                    other.send(this.name+"说"+msg);
                }
            }
        }
    }
}
