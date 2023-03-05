import com.FlooringMastery.dao.FlooringMasteryDaoFileImpl;
import com.FlooringMastery.dao.FlooringMasteryPersistenceException;
import com.FlooringMastery.dto.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class FlooringMasteryDaoFileImplTest {


    FlooringMasteryDaoFileImpl testDao;
    //The date for all the tests
    LocalDate testDate = LocalDate.of(2040,3,8);
    //file name for all the tests
    String testFile = "test/Orders_" + testDate.format(DateTimeFormatter.ofPattern("MMddyyyy"))+".txt";


    public FlooringMasteryDaoFileImplTest() {    }



    @BeforeEach
    void setUp() throws Exception {
        testDao = new FlooringMasteryDaoFileImpl();

        testDao.readTaxFile("Taxes.txt");
        testDao.readProductFile("Products.txt");

        //clears the order map each test
        testDao.getOrderMap().clear();
        //Clears the test file
        testDao.writeOrderFile(testFile);


        //sets the order map to a starting file
        testDao.readOrderFile("test/TestStartingFile.txt",testDate);

    }



    @Test
    public void testAddOrder() throws FlooringMasteryPersistenceException {

        // Create an Order object
        int  expectedOrderNumber = 4;    //what the order number should be
        Order order = new Order();
        order.setOrderDate(testDate);
        order.setCustomerName("test order");
        order.getState().setStateAbbreviation("WA");
        order.getProductType().setProductType("Wood");
        order.setArea(BigDecimal.valueOf(243.00));

        //config order
        testDao.configOrder(order);

        //adds the order to the test file
        testDao.assignOrderNumber(order);
        //Add given order to order file
        testDao.addOrderToFile(order,testFile);


        //test if the right order number is set to new order
        int assignedOrderNumber = order.getOrderNumber();
        assertEquals(expectedOrderNumber,assignedOrderNumber, "Checking assigned order number.");

        //test if order made it to the
        Order retrievedOrderFromMap = testDao.getOrderMap().get(expectedOrderNumber);
        assertEquals(order, retrievedOrderFromMap, "Checking if Order was added to map.");

        //test if order is on the file
        testDao.readOrderFile(testFile,testDate);
        Order retrievedOrderFromFile = testDao.getOrderMap().get(expectedOrderNumber);
        assertEquals(order, retrievedOrderFromFile, "Checking if Order was added to File.");

    }


    @Test
    public void testEditOrder() throws FlooringMasteryPersistenceException {

        //get an order to edit
        Order orderToEdit = testDao.getOrderMap().get(3);


        // Create a new Order to replace the old one
        //info that will stay the same: order number, date, state

        Order editedOrder = new Order();
        editedOrder.setOrderNumber(orderToEdit.getOrderNumber());   //keep order number
        editedOrder.setOrderDate(testDate);                         //keep order date
        editedOrder.setCustomerName("Albert_edited");       //name should change
        editedOrder.getState().setStateAbbreviation(orderToEdit.getState().getStateAbbreviation());   //state should stay the same
        editedOrder.getProductType().setProductType("Wood");    //product type should change
        editedOrder.setArea(BigDecimal.valueOf(243.00));        //area should change

        //config order
        testDao.configOrder(editedOrder);

        //Add given order to order file
        testDao.addOrderToFile(editedOrder,testFile);


        //test the info that should be the same
        assertEquals(orderToEdit.getOrderNumber(),editedOrder.getOrderNumber() , "Checking if Order Number is the same.");
        assertEquals(orderToEdit.getOrderDate(),editedOrder.getOrderDate() , "Checking if Order Date is the same.");
        assertEquals(orderToEdit.getState(),editedOrder.getState() , "Checking if State is the same.");


        //test if the edited order is in the map and replaces the old one
        Order retrievedOrderFromMap = testDao.getOrderMap().get(orderToEdit.getOrderNumber());
        assertEquals(retrievedOrderFromMap, editedOrder, "Checking if the edited Order was added to map.");
        assertNotEquals(retrievedOrderFromMap, orderToEdit, "Checking if the old Order was not in the map.");


        //test if the edited order is in the file and replaces the old one
        testDao.readOrderFile(testFile,testDate);
        Order retrievedOrderFromFile = testDao.getOrderMap().get(orderToEdit.getOrderNumber());
        assertEquals(retrievedOrderFromFile, editedOrder, "Checking if the edited Order was added to file.");
        assertNotEquals(retrievedOrderFromFile, orderToEdit, "Checking if the old Order was not in the file.");

    }


    @Test
    public void testRemoveOrder() throws FlooringMasteryPersistenceException {

        //get an order to remove
        Order orderToRemove = testDao.getOrderMap().get(3);

        //removes the order from the file
        testDao.removeOrderToFile(orderToRemove,testFile);

        //test if the order is removed from the map
        Order retrievedOrderFromMap = testDao.getOrderMap().get(orderToRemove.getOrderNumber());
        assertNotEquals(retrievedOrderFromMap, orderToRemove, "Checking if the Order was removed from the map.");


        //test if the order is removed from the file
        testDao.readOrderFile(testFile,testDate);
        Order retrievedOrderFromFile = testDao.getOrderMap().get(orderToRemove.getOrderNumber());
        assertNotEquals(retrievedOrderFromFile, orderToRemove, "Checking if the Order was removed from the file.");




    }




}
