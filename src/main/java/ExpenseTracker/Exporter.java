package ExpenseTracker;

import java.nio.file.Files;
import java.nio.file.Path;
import org.json.JSONArray;
import org.json.JSONObject;

public class Exporter {
    private Path file;
    
    public Exporter(String file){
        this.file = Path.of(file);
        checkFileExist();
    }
    
    public int checkFileExist(){
        if(!Files.exists(file)){
            try {
                Files.createFile(file);
            } catch (Exception e) {
                System.out.println("CSV file can not be created");
                return 1;
            }
        }
        return 0;
    }
    
    public static String format(JSONArray expenses){
        int length = expenses.length();
        if(length == 0)
            return null;
        String data = "";
        for (int i = 0; i < length; i++) {
            JSONObject expense = expenses.getJSONObject(i);
            data = data.concat(String.format("%d,%s,%s,%s,%s%n"
                            ,expense.getInt("id")
                            ,expense.getString("description")
                            ,String.valueOf(expense.getFloat("amount"))
                            ,expense.getString("category")
                            ,expense.getString("date")));
        }
        return data;
    }
    
    public int write(String data){

        try {
            Files.writeString(file, data);
        }catch(NullPointerException e){
            return 1;
        }catch (Exception e) {
            return 2;
        }
        return 0;
    }
    
    public int export(JSONArray expenses){
        if(expenses.length() == 0)
            return 1;
        String data = format(expenses);
        int result = write(data);
        return result;
    }
}
