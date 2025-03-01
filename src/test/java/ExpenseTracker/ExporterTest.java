package ExpenseTracker;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExporterTest {
    Exporter exporter = new Exporter("test.csv");
    JSONObject f = new JSONObject();
    JSONObject s = new JSONObject();
    JSONObject t = new JSONObject();
    JSONArray expenses = new JSONArray();
        
    public ExporterTest() {
        f.put("id", 1);
        f.put("description", "buy cheese");
        f.put("amount", 15.50f);
        f.put("category", "food");
        f.put("date", "2025-02-15");
        s.put("id", 2);
        s.put("description", "fix roof");
        s.put("amount", 200.00f);
        s.put("category", "others");
        s.put("date", "2025-01-15");
        t.put("id", 3);
        t.put("description", "buy chairs");
        t.put("amount", 500.00f);
        t.put("category", "home");
        t.put("date", "2024-02-15");
        expenses.put(f);
        expenses.put(s);
        expenses.put(t);
    }

    
    @Test
    public void testCheckFileExist() {
        assertEquals(0, exporter.checkFileExist());
    }
    
    @Test
    public void testFormat() {
        assertNotNull(Exporter.format(expenses));
    }
    
    @Test
    public void testExport(){
        assertEquals(0, exporter.export(expenses));
    }
    
    @Test
    public void testWrite(){
        String data = "each,data,chair";
        assertEquals(0, exporter.write(data));
    }
    
    @Test
    public void testWriteNullString(){
        String data = null;
        assertEquals(1, exporter.write(data));
    }
}
