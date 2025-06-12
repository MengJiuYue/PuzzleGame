package Log;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class LS implements LogServe{
    final static String FILE_PATH="src/Log/log.txt";
    public static void saveLog(String massage) {
        //追加写入错误日志
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH,true))) {
            bw.write(massage);
            bw.newLine();
            bw.write("------------------------------");
            bw.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
