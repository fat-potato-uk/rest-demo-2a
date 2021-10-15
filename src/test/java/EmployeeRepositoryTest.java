package demo.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testSetupRepoCount() {
        long b = employeeRepository.countEmployeeByName("Bilbo Baggins");
        assertEquals(1, b);
        System.out.println("Passed");
    }

    @Test
    public void testSetupRepoCountForFail() {
        long f = employeeRepository.countEmployeeByName("Frodo Baggins");
        assertEquals(4, f);
        System.out.println("Failed");
    }
}