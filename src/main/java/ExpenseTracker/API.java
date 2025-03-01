package ExpenseTracker;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

public class API {
    
    public static CRUD crud = new  CRUD();
    
    public static String setMonthlyBudget(String[] args){
        if(args.length !=  2)
            return "Follow this pattern -> budget <value>";
        if(!Pattern.matches("\\d+?.\\d*", args[1]))
            return "The value of budget has to be a number";
        crud.setBudget(Float.parseFloat(args[1]));
        return "Budget set up correctly";
    }
    
    public static void listExpensesByCategory(String[] args){
        int checkArgsResult = checkArgsToListExpensesByCategory(args);
        if(checkArgsResult == 1){
            System.out.println("Follow this pattern -> list --category <value>");
            return;
        }
        if(checkArgsResult == 2){
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
            return;
        }
        JSONArray expenses = crud.getExpensesByCategory(args[2]);
        listExpenses(expenses);
    }
    
    public static int checkArgsToListExpensesByCategory(String[] args){
        if(args.length != 3)
            return 1;
        if(!args[1].equalsIgnoreCase("--category"))
            return 1;
        if(!Pattern.matches("^(?!--).+", args[2]))
            return 1;
        if(!checkCategory(args[2]))
            return 2;
        return 0;
    }
    
    public static String getMonthlySummary(String[] args){
        int checkArgsResult = checkMonthlySummaryArgs(args);
        
        if(checkArgsResult == 1)
            return "Follow this pattern: summary --month <value>";
        
        if(checkArgsResult == 2)
            return "The value of month has to "
                    + "be a valid number[1-12]";
        
        float summary = crud.getMonthlySummary(Integer.parseInt(args[2]));

        if(summary == -1)
            return "There is no any stored expense";
        
        return NumberFormat.getCurrencyInstance().format(summary);
    }
    
    public static int checkMonthlySummaryArgs(String[] args){
        if(args.length != 3){
            System.out.println("Follow these pattern: summary "
                    + "or summary --month <value>");
            return 1;
        }
        if(!args[1].equalsIgnoreCase("--month"))
            return 1;
        
        if(!Pattern.matches("[1-9]", args[2]) && !Pattern.matches("1?\\d", args[2]))
            return 2;
        
        return 0;
    }
    
    public static String getSummary(){
        float summary = crud.getSummary();
        
        if(summary == -1)
            return "There is no any stored expense";
        
        return NumberFormat.getCurrencyInstance().format(summary);
    }
    
    public static String checkDescriptionLength(String description){
        int length = 15;
        if(description.length() > length)
            return description.substring(0, length) + "...";
        return description;
    }
    
    public static void listExpenses(JSONArray expenses){
        if(expenses.length() == 0){
            System.out.println("There are not expenses");
            return;
        }
        System.out.println("ID\tDate\t\tDescription\t\tAmount");
        for (int i = 0; i < expenses.length(); i++) {
            JSONObject expense = expenses.getJSONObject(i);
            int id = expense.getInt("id");
            String date = expense.getString("date");
            String description = checkDescriptionLength(expense
                    .getString("description"));
            String formatedAmount = NumberFormat.getCurrencyInstance()
                    .format(expense.getFloat("amount"));
            System.out.printf("%d\t%s\t\t%s\t\t%s%n", id
                    , date, description, formatedAmount);
        }
    }
    
    public static int updateExpense(String[] args){
        int checkArgsResult = checkArgsToUpdateExpenseOption(args);
        if(checkArgsResult == 1){
            System.out.println("Follow this pattern: "
                    + "'update --id <value> "
                    + "--description <value> --amount <value>"
                    + " --category <value>'");
            return -1;
        }
        if(checkArgsResult == 2){
            System.out.println("The id has to be a positive number");
            return -1;
        }
        if(checkArgsResult == 3){
            System.out.println("The value of amount has to "
                    + "be a number (1-9 & .)");
            return -1;
        }
        if(checkArgsResult == 4){
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
        Map uexpenseMap = getExpenseMap(args);
        int id = crud.updateExpense(uexpenseMap);
        return id;
    }
    
    public static int checkArgsToUpdateExpenseOption(String[] args){
        if(args.length < 5)
            return 1;
        if(!args[1].equalsIgnoreCase("--id"))
            return 1;
        if(!Pattern.matches("\\d+", args[2]))
            return 2;
        
        boolean[] flags = {false,false,false};
        
        for (int i = 3; i < args.length - 1; i++) {
            switch (args[i].toLowerCase()) {
                case "--description":
                    if(!Pattern.matches("^(?!--).+", args[i+1]) || flags[0])
                        return 1;
                    flags[0] = true;
                    i++;
                    break;
                case "--amount":
                    if(!Pattern.matches("^(?!--).+", args[i+1]) || flags[1])
                        return 1;
                    if(!Pattern.matches("\\d+\\.?\\d*", args[i+1]))
                        return 3;
                    flags[1] = true;
                    i++;
                    break;
                case "--category":
                    if(!Pattern.matches("^(?!--).+", args[i+1]) || flags[2])
                        return 1;
                    if(!checkCategory(args[i+1]))
                        return 4;
                    flags[2] = true;
                    i++;
                    break;
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
            String type = args[i].substring(2);
            switch (type.toLowerCase()) {
                case "id":
                    expenseMap.put(type, Integer.parseInt(args[i+1]));
                    i++;
                    break;
                case "description":
                    expenseMap.put(type, args[i+1]);
                    i++;
                    break;
                case "amount":
                    expenseMap.put(type, Float.parseFloat(args[i+1]));
                    i++;
                    break;
                case "category":
                    expenseMap.put(type, args[i+1]);
                    i++;
                    break;
                default:
                    break;
            }
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
        
        boolean[] flags = {false,false,false};
        
        for (int i = 1; i < args.length - 1; i++) {
            switch (args[i].toLowerCase()) {
                case "--description":
                    if(!p.matcher(args[i+1]).matches() || flags[0])
                        return 1;
                    flags[0] = true;
                    aux++;
                    i++;
                    break;
                case "--amount":
                    if(!p.matcher(args[i+1]).matches() || flags[1])
                        return 1;
                    flags[1] = true;
                    aux++;
                    i++;
                    break;
                case "--category":
                    if(!p.matcher(args[i+1]).matches() || flags[2])
                        return 1;
                    if(!checkCategory(args[i+1]))
                        return 2;
                    flags[2] = true;
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
        int id;
        switch (args[0].toLowerCase()) {
            case "add":
                id = addExpense(args);
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
                id = updateExpense(args);
                if(id != -1){
                    System.out.println("Expense updated succesfully");
                }
                break;
            case "list":
                System.out.println(args.length);
                if(args.length == 1){
                    listExpenses(crud.getExpenses());
                }else{
                    listExpensesByCategory(args);
                }
                break;
            case "summary":
                if(args.length == 1){
                    System.out.println(getSummary());
                }
                else{
                    System.out.println(getMonthlySummary(args));
                }
                break;
            case "budget":
                break;
            default:
                System.out.println("Unknow option");;
        }
    }
    
    public static void main(String[] args) {
        listExpenses(crud.getExpenses());
    }
    
}
