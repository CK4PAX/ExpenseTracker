package ExpenseTracker;

import java.text.DateFormat;
import java.util.Date;

public class Expense {
    private int id;
    private String description;
    private String date = DateFormat
            .getDateInstance(DateFormat.SHORT).format(new Date());
    private float amount;
    private String category;
    
    public Expense(int id, String description, Float amount){
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.category = "others";
    }
    
    public Expense(int id, String description, Float amount, String category){
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.category = category;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    
}
