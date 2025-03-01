package ExpenseTracker;

import java.util.Map;
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
    public void testCheckArgsToAddExpenseWithTwoDescriptions() {
        String[] args = {"add","--description","buy a house"
        ,"--category","food","--description","pay services"};
        assertEquals(1, API.checkArgsToAddExpenseOption(args));
    }
    
    @Test
    public void testGetExpenseMap(){
        String[] args = {"add","--amount","5000.00",
            "--description","buy a house","--category"
                ,"investment"};
        Map test = API.getExpenseMap(args);
        float amount = (float)test.get("amount");
        String description = (String)test.get("description");
        assertEquals(5000.00f, amount);
        assertEquals("buy a house", description);
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
    
    @Test
    public void testCheckArgsToUpdateExpenseOptionBadId(){
        String[] args = {"update","--id","123.00",
            "--description","pay lunch"};
        assertEquals(2,API.checkArgsToUpdateExpenseOption(args));
    }
    
    @Test
    public void testCheckArgsToUpdateExpenseOptionWithIncompleteValues(){
        String[] args = {"update","--id","123",
            "--description","--category"};
        assertEquals(1,API.checkArgsToUpdateExpenseOption(args));
    }
    
    @Test
    public void testCheckArgsToUpdateExpenseOptionWithoutIdOption(){
        String[] args = {"update","--amount","123.34"
                ,"--description","--category"};
        assertEquals(1,API.checkArgsToUpdateExpenseOption(args));
    }
    
    @Test
    public void testCheckArgsToUpdateExpenseOptionWithUnorderedId(){
        String[] args = {"update","--amount","123.34","--id","23"
                ,"--description","--category"};
        assertEquals(1,API.checkArgsToUpdateExpenseOption(args));
    }
    
    @Test
    public void testCheckArgsToUpdateExpenseOptionWithTwoId(){
        String[] args = {"update","--id","123","--id","23"
                ,"--description","rent a car"};
        assertEquals(1,API.checkArgsToUpdateExpenseOption(args));
    }
    
    @Test
    public void testCheckArgsToUpdateExpenseOptionWithTwoCategories(){
        String[] args = {"update","--id","123","--category","food"
                ,"--description","rent a car","--category","clothes"};
        assertEquals(1,API.checkArgsToUpdateExpenseOption(args));
    }
    
    @Test
    public void testCheckArgsToGetMonthlySummary(){
        String[] args = {"summary","--month","2"};
        assertEquals(0,API.checkMonthlySummaryArgs(args));
    }
    
    @Test
    public void testCheckArgsToGetMonthlySummaryWithBadMonth(){
        String[] args = {"summary","--month","23"};
        assertEquals(2,API.checkMonthlySummaryArgs(args));
    }
    
    @Test
    public void testCheckArgsToGetMonthlySummaryWithBadOption(){
        String[] args = {"summary","--id","2"};
        assertEquals(1,API.checkMonthlySummaryArgs(args));
    }
    
    @Test
    public void testCheckArgsToGetMonthlySummaryWithBadAmountOfArgs(){
        String[] args = {"summary","--month","2","--year", "2025"};
        assertEquals(1,API.checkMonthlySummaryArgs(args));
    }
    
    @Test
    public void testListExpenses(){
        assertDoesNotThrow(()->{API.listExpenses(API.crud.getExpenses());});
    }
    
    @Test
    public void testCheckDescriptionLength(){
        String string = "pay middle school last month";
        String test = "pay middle scho...";
        
        assertEquals(test, API.checkDescriptionLength(string));
    }
    
    @Test
    public void testGetMonthlySummary(){
        String[] args = {"summary", "--month","8"};
        assertDoesNotThrow(()->{API.getMonthlySummary(args);});
    }
    
    @Test
    public void testGetSummary(){
        assertDoesNotThrow(()->{API.getSummary();});
    }
    
    @Test
    public void testCheckArgsToListExpensesByCategory(){
        String[] args = {"list","--category","food"};
        assertEquals(0, API.checkArgsToListExpensesByCategory(args));
    }
    
    @Test
    public void testCheckArgsToListExpensesByCategoryWithBadCategory(){
        String[] args = {"list","--category","car"};
        assertEquals(2, API.checkArgsToListExpensesByCategory(args));
    }
    
    @Test
    public void testCheckArgsToListExpensesByCategoryWithBadOption(){
        String[] args = {"list","category","food"};
        assertEquals(1, API.checkArgsToListExpensesByCategory(args));
    }
}
