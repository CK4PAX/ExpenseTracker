package ExpenseTracker;

import java.util.HashMap;
import java.util.Map;
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
    public void testCreateExpenseWithoutCategory() {
        Map test = new HashMap();

        String description = "buy a car";
        String amount = "123.34";

        test.put("description", description);
        test.put("amount",amount);

        Expense expense = crud.createExpense(test);

        assertEquals(description, expense.getDescription());
        assertEquals(Float.parseFloat(amount), expense.getAmount());
    }
    
    @Test
    public void testCreateExpenseWithCategory() {
        Map test = new HashMap();

        String description = "buy a car";
        String amount = "123.34";
        String category = "inversion";

        test.put("description", description);
        test.put("amount",amount);
        test.put("category",category);

        Expense expense = crud.createExpense(test);

        assertEquals(description, expense.getDescription());
        assertEquals(Float.parseFloat(amount), expense.getAmount());
        assertEquals(category, expense.getCategory());
    }
    
    @Test
    public void testAddExpensive(){
        Map test = new HashMap();

        String description = "buy a car";
        String amount = "123.34";
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
        m.put("amount", "123.45");
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
        m.put("amount", "123.45");
        int id = crud.addExpense(m);
        crud.deleteExpense(id);
        assertFalse(crud.checkExpenseExist(id));
    }
    
    @Test
    public void testDeleteExpenseUnknowedId(){
        int id = -99;
        assertEquals(-1, crud.deleteExpense(id));
    }
}
