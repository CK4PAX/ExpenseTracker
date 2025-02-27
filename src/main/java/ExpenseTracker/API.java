package ExpenseTracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

public class API {
    
    private static CRUD crud = new  CRUD();
    
    public static int checkArgsToUpdateExpenseOption(String[] args){
        if(args.length < 5)
            return 1;
        
        for (int i = 1; i < args.length - 1; i++) {
            switch (args[i].toLowerCase()) {
                case "--id":
                    if(!Pattern.matches("\\d+", args[i+1]))
                        return 2;
                    if(!checkExpenseExist(Integer.parseInt(args[i+1])))
                        return 3;
                    i++;
                    break;
                case "--description":
                    if(!Pattern.matches("^(?!--).+", args[i+1]))
                        return 1;
                    i++;
                case "--amount":
                    if(!Pattern.matches("^(?!--).+", args[i+1]))
                        return 1;
                    if(!Pattern.matches("\\d+\\.?\\d*", args[i+1]))
                        return 4;
                    i++;
                case "--category":
                    if(!Pattern.matches("^(?!--).+", args[i+1]))
                        return 1;
                   if(!checkCategory(args[i+1]))
                        return 5;
                    i++;
                default:
                    return 1;
            }
        }
        return 0;
    }
    
    public static int deleteExpense(String[] args){
        int checkArgsResult = checkArgsToDeleteExpenseOption(args);
        if(checkArgsResult == 1){
            System.out.println("Follow this pattern: "
                    + "'delete --id <value>'");
            return -1;
        }
        if(checkArgsResult == 2){
            System.out.println("Third argument has to be a integer number");
            return -1;
        }
        if(checkArgsResult == 3){
            System.out.println("The ID has to be a positive integer");
            return -1;
        }
        crud.deleteExpense(Integer.parseInt(args[2]));
        return 0;
    }

    public static boolean checkExpenseExist(int id){
        return crud.checkExpenseExist(id);
    }
    
    public static int checkArgsToDeleteExpenseOption(String[] args){
        if(args.length != 3)
            return 1;
        if(!args[1].equalsIgnoreCase("--id"))
            return 1;
        if(!Pattern.matches("\\d+", args[2]))
            return 2;
        if(Integer.parseInt(args[2]) < 1)
            return 3;
        return 0;
    }
    
    public static int addExpense(String[] args){
        int result = checkArgsToAddExpenseOption(args);
        if(result == 1){
            System.out.println("Follow this pattern: "
                    + "'add --description <value> "
                    + "--amount <value> --category <value>'");
            return -1;
        }
        if(result == 2){
            System.out.println("""
                               Unlisted category.Use one of these:
                               - home
                               - food
                               - clothes
                               - services
                               - transport
                               - health
                               - education
                               - investment
                               - others""");
            return -1;
        }
        Map expenseMap = getExpenseMap(args);
        int id = crud.addExpense(expenseMap);
        return id;
    }
    
    public static Map getExpenseMap(String[] args){
        Map expenseMap = new HashMap();
        
        for (int i = 1; i < args.length - 1; i++) {
            expenseMap.put(args[i].substring(2), args[i+1]);
            i++;
        }
        return expenseMap;
    }
    
    public static boolean checkCategory(String category){
        String[] categories = {"home", "food","clothes","services"
                ,"transport","health","education","investment","others"};
        for (String i : categories) {
            if(i.equalsIgnoreCase(category))
                return true;
        }
        return false;
    }
    
    public static int checkArgsToAddExpenseOption(String[] args){
        
        if(args.length < 5)
            return 1;
        
        int aux = 0;
        Pattern p = Pattern.compile("^(?!--).+");
        
        for (int i = 1; i < args.length - 1; i++) {
            switch (args[i].toLowerCase()) {
                case "--description":
                    if(!p.matcher(args[i+1]).matches())
                        return 1;
                    aux++;
                    i++;
                    break;
                case "--amount":
                    if(!p.matcher(args[i+1]).matches())
                        return 1;
                    aux++;
                    i++;
                    break;
                case "--category":
                    if(!p.matcher(args[i+1]).matches())
                        return 1;
                    if(!checkCategory(args[i+1]))
                        return 2;
                    i++;
                    break;
                default:
                    return 1;
            }
        }
        
        if(aux != 2)
            return 1;
        
        return 0;
    }
    
    public static void doThings(String[] args){
        
        if(args.length == 0){
            System.out.println("Enter an option");
            return;
        }
        
        switch (args[0].toLowerCase()) {
            case "add":
                int id = addExpense(args);
                if(id != -1)
                    System.out.printf("Expense added "
                    + "successfully (ID: %d)%n", id);
                break;
            case "delete":
                int result = deleteExpense(args);
                if(result == 0){
                    System.out.println("Expense deleted successfully");
                }
                break;
            case "update":
                break;
            default:
                System.out.println("Unknow option");;
        }
    }
    
}
