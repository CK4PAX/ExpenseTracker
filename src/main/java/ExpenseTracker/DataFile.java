
package ExpenseTracker;

import java.nio.file.Files;
import java.nio.file.Path;

public class DataFile {
    private Path path;
    
    public DataFile(){
        checkFile();
    }
    
    public int checkFile(){
        path = Path.of("data.json");
        if (!Files.exists(path)){
            try {
                Files.createFile(path);
            } catch (Exception e) {
                System.out.println("Data file can not be created");
                return 1;
            }
        }
        return 0;
    }
    
    public String read(){
        String string = null;
        try {
            string = Files.readString(path);
           
        } catch (Exception e) {
            System.out.println("Data file can not be readed");
            return null;
        }
        return string;
    }
    
    public void write(String data) throws Exception{
        Files.writeString(path, data);
    }
}
