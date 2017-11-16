package swp.shafou.qr2buy.TestData;

import java.util.ArrayList;

import swp.shafou.qr2buy.POJO.OrderPosition;

/**
 *------------------------------------*
 * - Klasse erstellt. 06.01.17 ELFOULY
 *------------------------------------*
 *
 * - Diese Klasse beeinhaltet OrderPosition Test Objekte.
 */
public class OrderPositionTestData {

    public static ArrayList<OrderPosition> orderPositionTestList;

    static {

        orderPositionTestList = new ArrayList<>();

        OrderPosition bestellungEinsEins = new OrderPosition(1, 1, 2999980, 1, 4.00, 1.00, "20.04.2017", "Bestellt");
        OrderPosition bestellungEinsZwei = new OrderPosition(2, 1, 2999981, 4, 4.00, 4.00, "20.04.2017", "Bestellt");
        OrderPosition bestellungEinsDrei = new OrderPosition(3, 1, 2999982, 2, 4.00, 2.00, "20.04.2017", "Bestellt");
        OrderPosition bestellungZwei = new OrderPosition(4, 2, 2999981, 2, 4.00, 2.00, "19.01.2017", "Bestellt");
        OrderPosition bestellungDreiEins = new OrderPosition(5, 3, 2999982, 1, 4.00, 1.00, "02.02.2016", "Bestellt");
        OrderPosition bestellungDreiZwei = new OrderPosition(10, 3, 2999983, 4, 4.00, 4.00, "02.02.2016", "Bestellt");
        OrderPosition bestellungDreiDrei = new OrderPosition(11, 3, 2999984, 3, 4.00, 3.00, "02.02.2016", "Bestellt");
        OrderPosition bestellungVier = new OrderPosition(6, 4, 2999981, 4, 4.00, 4.00, "12.12.2015", "Bestellt");
        OrderPosition bestellungFünf = new OrderPosition(7, 5, 2999982, 1, 4.00, 1.00, "01.01.2017", "Bestellt");
        OrderPosition bestellungFünfEins = new OrderPosition(8, 5, 2999984, 1, 4.00, 1.00, "01.01.2017", "Bestellt");
        OrderPosition bestellungFünfZwei = new OrderPosition(9, 5, 2999983, 2, 4.00, 2.00, "01.01.2017", "Bestellt");

        orderPositionTestList.add(bestellungEinsEins);
        orderPositionTestList.add(bestellungEinsZwei);
        orderPositionTestList.add(bestellungEinsDrei);
        orderPositionTestList.add(bestellungZwei);
        orderPositionTestList.add(bestellungDreiEins);
        orderPositionTestList.add(bestellungDreiZwei);
        orderPositionTestList.add(bestellungDreiDrei);
        orderPositionTestList.add(bestellungVier);
        orderPositionTestList.add(bestellungFünf);
        orderPositionTestList.add(bestellungFünfEins);
        orderPositionTestList.add(bestellungFünfZwei);
    }
}
