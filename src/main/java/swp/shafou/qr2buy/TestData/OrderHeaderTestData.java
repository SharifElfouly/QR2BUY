package swp.shafou.qr2buy.TestData;

import java.util.ArrayList;

import swp.shafou.qr2buy.POJO.OrderHeader;
import swp.shafou.qr2buy.POJO.OrderPosition;

/**
 *------------------------------------*
 * - Klasse erstellt. 09.02.17 ELFOULY
 *------------------------------------*
 *
 * - Diese Klasse beeinhaltet OrderHeader Test Objekte.
 */
public class OrderHeaderTestData {

    public static ArrayList<OrderHeader> orderHeaderTestList;

    static {

        orderHeaderTestList = new ArrayList<>();

        OrderHeader orderHeader1 = new OrderHeader(168362, "20.04.2017", 1, 12, 12, "Freigegeben");
        OrderHeader orderHeader2 = new OrderHeader(872862, "19.01.2017", 1, 2, 12, "Freigegeben");
        OrderHeader orderHeader3 = new OrderHeader(149433, "02.02.2017", 1, 12, 12, "Freigegeben");
        OrderHeader orderHeader4 = new OrderHeader(974634, "12.12.2017", 1, 16, 12, "Freigegeben");
        OrderHeader orderHeader5 = new OrderHeader(905325, "01.01.2017", 1, 4, 12, "Freigegeben");

        orderHeaderTestList.add(orderHeader1);
        orderHeaderTestList.add(orderHeader2);
        orderHeaderTestList.add(orderHeader3);
        orderHeaderTestList.add(orderHeader4);
        orderHeaderTestList.add(orderHeader5);
    }

}
