package ExpenseTracker;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class APITest {
    
    public APITest() {
    }

    @Test
    public void testDoThingsZeroArgs() {
        String[] args = {};
        assertDoesNotThrow(()->{API.doThings(args);});
    }
    
    @Test
    public void testDoThingsUnknowedOption() {
        String[] args = {"eliminar"};
        assertDoesNotThrow(()->{API.doThings(args);});
    }
    
    @Test
    public void testCheckArgsToAddExpenseEssentialArgs() {
        String[] args = {"add","--description","buy a house"
        ,"--amount","5000.00"};
        assertEquals(0, API.checkArgsToAddExpenseOption(args));
    }
    
    @Test
    public void testCheckArgsToAddExpenseBadEssentialArgs() {
        String[] args = {"add","--descriptions","buy a house"
        ,"--cost","5000.00"};
        assertEquals(1, API.checkArgsToAddExpenseOption(args));
    }
    
    @Test
    public void testCheckArgsToAddExpenseWithoutOptions() {
        String[] args = {"add","buy a house"
        ,"5000.00"};
        assertEquals(1, API.checkArgsToAddExpenseOption(args));
    }
    
    @Test
    public void testCheckArgsToAddExpenseUnorderedEssentialArgs() {
        String[] args = {"add","--amount","5000.00",
            "--description","buy a house"
        };
        assertEquals(0, API.checkArgsToAddExpenseOption(args));
    }
    
    @Test
    public void testCheckArgsToAddExpenseWithoutEssentialOptions() {
        String[] args = {"add","--description","buy a house"
        ,"--category","food"};
        assertEquals(1, API.checkArgsToAddExpenseOption(args));
    }
    
    @Test
    public void testGetExpenseMap(){
        String[] args = {"add","--amount","5000.00",
            "--description","buy a house","--category"
                ,"investment"};
        assertDoesNotThrow(()->{API.getExpenseMap(args);});
    }
    
    @Test
    public void testCheckCategory(){
        String category = "clothes";
        assertTrue(API.checkCategory(category));
    }
    
    @Test
    public void testCheckCategoryWithFailedArg(){
        String category = "vacations";
        assertFalse(API.checkCategory(category));
    }
    
    @Test
    public void testCheckArgsToDeleteExpenseOption(){
        String[] args = {"delete","--id", "1"};
        assertEquals(0, API.checkArgsToDeleteExpenseOption(args));
    }
    
    @Test
    public void testCheckArgsToDeleteExpenseOptionWithBadThirdArg(){
        String[] args = {"delete","--id", "uno"};
        assertEquals(2, API.checkArgsToDeleteExpenseOption(args));
    }
    
    @Test
    public void testCheckArgsToDeleteExpenseOptionWithMoreThanThreeArgs(){
        String[] args = {"delete","--id", "uno", "dos"};
        assertEquals(1, API.checkArgsToDeleteExpenseOption(args));
    }
    
    @Test
    public void testCheckArgsToDeleteExpenseOptionWithBadSecondArg(){
        String[] args = {"delete","--identity", "uno"};
        assertEquals(1, API.checkArgsToDeleteExpenseOption(args));
    }
    
    @Test
    public void testCheckExpenseExist(){
        String[] args = {"add","--amount","5000.00",
            "--description","buy a house","--category"
                ,"investment"};
        int id = API.addExpense(args);
        assertTrue(API.checkExpenseExist(id));
    }
    
    @Test
    public void testCheckArgsToUpdateExpenseOption(){
        String[] args = {"update","--id","123",
            "--description","pay lunch","--amount"
                ,"1234.34","--category","others"};
        assertEquals(0,API.checkArgsToUpdateExpenseOption(args));
    }
    
    @Test
    public void testCheckArgsToUpdateExpenseOptionChangingDescriptionAndAmount(){
        String[] args = {"update","--id","123",
            "--description","pay lunch","--amount"
                ,"1234.34"};
        assertEquals(0,API.checkArgsToUpdateExpenseOption(args));
    }
    
    @Test
    public void testCheckArgsToUpdateExpenseOptionChangingDescriptionAndCategory(){
        String[] args = {"update","--id","123",
            "--description","pay lunch","--category"
                ,"food"};
        assertEquals(0,API.checkArgsToUpdateExpenseOption(args));
    }
    
    @Test
    public void testCheckArgsToUpdateExpenseOptionChangingDescription(){
        String[] args = {"update","--id","123",
            "--description","pay lunch"};
        assertEquals(0,API.checkArgsToUpdateExpenseOption(args));
    }
}
