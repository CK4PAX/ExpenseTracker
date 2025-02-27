package ExpenseTracker;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DataFileTest {
    
    DataFile data = new DataFile();
    public DataFileTest() {
        
    }

    @Test
    public void testCheckFile() {
        assertEquals(0, data.checkFile());
    }
    
    @Test
    public void testread() {
        assertNotNull(data.read());
    }
}
