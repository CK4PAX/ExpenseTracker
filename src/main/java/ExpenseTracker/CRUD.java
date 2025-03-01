package ExpenseTracker;

import java.time.LocalDate;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CRUD {
    
    private JSONArray expenses;
    private Budget budget = new Budget();
    private DataFile file = new DataFile();
    
    public CRUD(){
        init();
    }
    
    public void init(){
        try {
            JSONObject data = new JSONObject(file.read());
            budget.setAmount(data.getFloat("budget"));
            expenses = data.getJSONArray("expenses");
        } catch (JSONException e) {
            budget.setAmount(0);
            expenses = new JSONArray();
        }
    }

    public Budget getBudget() {
        return budget;
    }
    
    public boolean checkBudgetReached(){
        int length = expenses.length();
        
        if(budget.getAmount() == 0)
            return false;
        if(expenses.length() == 0)
            return false;
        
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        float suma = 0;
        
        for (int i = 0; i < length; i++) {
            JSONObject expense = expenses.getJSONObject(i);
            LocalDate date = LocalDate.parse(expense.getString("date"));
            
            if(year == date.getYear() && month == date.getMonthValue())
                suma += expense.getFloat("amount");
        }
        
        if(suma < budget.getAmount())
            return false;
        
        return true;
    }
    
    public void setBudget(float budget){
        this.budget.setAmount(budget);
    }

    public JSONArray getExpenses() {
        return expenses;
    }
    
    public JSONArray getExpensesByCategory(String category){
        int length = expenses.length();
        JSONArray filteredExpenses = new JSONArray();
        if(length == 0)
            return null;
        
        for (int i = 0; i < length; i++) {
            JSONObject expense = expenses.getJSONObject(i);
            if(expense.getString("category").equalsIgnoreCase(category))
                filteredExpenses.put(expense);
        }
        return filteredExpenses;
    }
    
    public float getMonthlySummary(int month){
        if(expenses.length() == 0)
            return -1;
        
        float summary = 0;
        int year = LocalDate.now().getYear();
        
        for (int i = 0; i < expenses.length(); i++){ 
            
            JSONObject expense = expenses.getJSONObject(i);
            LocalDate date = LocalDate.parse(expense.getString("date"));
            
            if(date.getYear() == year && date.getMonthValue() == month)
                summary += expenses.getJSONObject(i).getFloat("amount");
        }
        
        return summary;
    }
    
    public float getSummary(){
        if(expenses.length() == 0)
            return -1;
        
        float summary = 0;
        
        for (int i = 0; i < expenses.length(); i++) 
            summary += expenses.getJSONObject(i).getFloat("amount");
        
        return summary;
    }
    
    public JSONObject getExpense(int id){
        for (int i = 0; i < expenses.length(); i++) {
            JSONObject expense = expenses.getJSONObject(i);
            if(expense.getInt("id") == id)
                return expense;
        }
        return null;
    }
    
    public int updateExpense(Map uexpenseMap){
        int id  = (int)uexpenseMap.get("id");
        
        for (int i = 0; i < expenses.length(); i++) {
            JSONObject expense = expenses.getJSONObject(i);
            if(expense.getInt("id") == id){
                uexpenseMap.forEach((k,v)->{expense.put((String)k,v);});
                write();
                return id;
            }
        }
        return -1;
    }
    
    public boolean checkExpenseExist(int id){
        for (int i = 0; i < expenses.length(); i++) {
            JSONObject expense = expenses.getJSONObject(i);
            if(expense.getInt("id") == id)
                return true;
        }
        return false;
    }
    
    public int deleteExpense(int id){
        
        for (int i = 0; i < expenses.length(); i++) {
            JSONObject expense = expenses.getJSONObject(i);
            if(expense.getInt("id") == id){
                expenses.remove(i);
                write();
                return id;
            }
        }
        return -1;
    }
    
    public  Expense createExpense(Map expenseMap){
        
        String description = (String)expenseMap.get("description");
        float  amount = (float)expenseMap.get("amount");
        String category = (String)expenseMap.get("category");
        Expense expense = null;
        int id = 0;
        
        if(!expenses.isEmpty()){
            id = expenses.getJSONObject(expenses.length()-1).getInt("id");
        }
        id = id + 1;
        
        
        if(category == null){
            expense = new Expense(id, description, amount);
        }else{
            expense = new Expense(id, description, amount, category);
        }
        return expense;
    }
    
    public int write(){
        try {
            JSONObject data = new JSONObject();
            data.put("budget", budget.getAmount());
            data.put("expenses", expenses);
            file.write(data.toString());
            return 0;
        } catch (Exception e) {
            System.out.println("Data file can not be written");
            return 1;
        }
    }
    
    public int addExpense(Map expenseMap){
        Expense expense = createExpense(expenseMap);
        expenses.put(new JSONObject(expense));

        if(write() != 0)
            return -1;

        return expense.getId();
    }
    
    public static void main(String[] args) {
        new CRUD().deleteExpense(5);
    }
}
