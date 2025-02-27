package ExpenseTracker;

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

    public JSONArray getExpenses() {
        return expenses;
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
        float  amount = Float.parseFloat((String)expenseMap.get("amount"));
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
