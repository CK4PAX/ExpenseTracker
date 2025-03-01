package ExpenseTracker;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CRUDTest {
    
    CRUD crud = new CRUD();
    public CRUDTest() {
    }

    @Test
    public void testInit() {
        assertDoesNotThrow(()->{crud.init();});
    }
    
    @Test
    public void testGetMonthlySummary(){
        int month = 2;
        assertDoesNotThrow(()->{crud.getMonthlySummary(month);});
    }
    
    @Test
    public void testGetSummary(){
        assertDoesNotThrow(()->{crud.getSummary();});
    }
    
    @Test
    public void testCreateExpenseWithoutCategory() {
        Map test = new HashMap();

        String description = "buy a car";
        float amount = 123.34f;

        test.put("description", description);
        test.put("amount",amount);

        Expense expense = crud.createExpense(test);

        assertEquals(description, expense.getDescription());
        assertEquals(amount, expense.getAmount());
    }
    
    @Test
    public void testCreateExpenseWithCategory() {
        Map test = new HashMap();

        String description = "buy a car";
        float amount = 123.34f;
        String category = "investment";

        test.put("description", description);
        test.put("amount",amount);
        test.put("category",category);

        Expense expense = crud.createExpense(test);

        assertEquals(description, expense.getDescription());
        assertEquals(amount, expense.getAmount());
        assertEquals(category, expense.getCategory());
    }
    
    @Test
    public void testAddExpensive(){
        Map test = new HashMap();

        String description = "buy a car";
        float amount = 123.34f;
        String category = "inversion";

        test.put("description", description);
        test.put("amount",amount);
        test.put("category",category);
        
        int id = crud.addExpense(test);
        crud.deleteExpense(id);
        assertNotEquals(-1,id);
    }
    
    @Test
    public void testCheckExpenseExist(){
        Map m = new HashMap();
        m.put("description", "pay taxes");
        m.put("amount", 123.45f);
        int id = crud.addExpense(m);
        assertTrue(crud.checkExpenseExist(id));
        crud.deleteExpense(id);
    }
    
    @Test
    public void testCheckExpenseExistUnknowedId(){
        int id = -12;
        assertFalse(crud.checkExpenseExist(id));
    }
    
    @Test
    public void testDeleteExpense(){
        Map m = new HashMap();
        m.put("description", "pay taxes");
        m.put("amount", 123.45f);
        int id = crud.addExpense(m);
        crud.deleteExpense(id);
        assertFalse(crud.checkExpenseExist(id));
    }
    
    @Test
    public void testDeleteExpenseUnknowedId(){
        int id = -99;
        assertEquals(-1, crud.deleteExpense(id));
    }
    
    @Test
    public void testGetExpense(){
        Map m = new HashMap();
        m.put("description", "laundry payment");
        m.put("amount", 76.45f);
        
        int id = crud.addExpense(m);
        JSONObject expense = crud.getExpense(id);
        assertEquals("laundry payment",expense.get("description"));
        assertEquals(76.45f, expense.get("amount"));
        crud.deleteExpense(id);
    }
    
    @Test
    public void testUpdateExpense(){
        Map m = new HashMap();
        m.put("description", "pay taxes");
        m.put("amount", 123.45f);
        
        int id = crud.addExpense(m);
        
        Map modified = new HashMap();
        
        modified.put("id", id);
        modified.put("description", "pay dinner");
        modified.put("amount", 50.00f);
        
        crud.updateExpense(modified);
        
        JSONObject expense = crud.getExpense(id);
        
        assertEquals("pay dinner", expense.getString("description"));
        assertEquals(50.00f, expense.getFloat("amount"));
        crud.deleteExpense(id);
    }
    
    
    @Test
    public void testGetExpensesByCategory(){
        assertDoesNotThrow(()->{crud.getExpensesByCategory("food");});
    }
    
    @Test
    public void testCheckBudget(){
        assertDoesNotThrow(()->{crud.checkBudgetReached();});
    }
    
    @Test
    public void testCheckBudgetExceeded(){
        float budget = crud.getBudget().getAmount();
        crud.setBudget(500);
        Map m = new HashMap();
        m.put("description", "fix the kitchen");
        m.put("amount", 600f);
        
        int id = crud.addExpense(m);
        boolean result = crud.checkBudgetReached();
        
        crud.setBudget(budget);
        crud.deleteExpense(id);
        assertTrue(result);
    }
}
