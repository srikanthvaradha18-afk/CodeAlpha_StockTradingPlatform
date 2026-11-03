import java.util.*;

public class StockTradingSystem {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        User user = new User("Investor", 100000);

        ArrayList<Stock> market = new ArrayList<>();

        market.add(new Stock("TCS", "Tata Consultancy", 3500));
        market.add(new Stock("INFY", "Infosys", 1500));
        market.add(new Stock("RELI", "Reliance", 2700));

        while (true) {

            System.out.println("\n===== STOCK MARKET =====");
            System.out.println("1. View Market");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Transaction History");
            System.out.println("6. Exit");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    displayMarket(market);
                    break;

                case 2:
                    buyStock(sc, user, market);
                    break;

                case 3:
                    sellStock(sc, user, market);
                    break;

                case 4:
                    showPortfolio(user, market);
                    break;

                case 5:
                    displayHistory(user);
                    break;

                case 6:
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }

    static void displayMarket(List<Stock> stocks) {

        System.out.println("\nMarket Data:");

        for (Stock s : stocks) {
            System.out.println(
                    s.getSymbol() + " - " +
                    s.getCompanyName() +
                    " : ₹" + s.getPrice());
        }
    }

    static void buyStock(Scanner sc,
                         User user,
                         List<Stock> market) {

        System.out.print("Enter Stock Symbol: ");
        String symbol = sc.next();

        System.out.print("Quantity: ");
        int qty = sc.nextInt();

        for (Stock stock : market) {

            if (stock.getSymbol()
                     .equalsIgnoreCase(symbol)) {

                double cost =
                        stock.getPrice() * qty;

                if (user.getCashBalance() >= cost) {

                    user.deductCash(cost);

                    user.getPortfolio()
                        .buyStock(symbol, qty);

                    user.getHistory().add(
                            new Transaction(
                                    symbol,
                                    qty,
                                    stock.getPrice(),
                                    "BUY"));

                    System.out.println(
                            "Stock Purchased!");
                } else {
                    System.out.println(
                            "Insufficient Balance!");
                }
            }
        }
    }

    static void sellStock(Scanner sc,
                          User user,
                          List<Stock> market) {

        System.out.print("Enter Stock Symbol: ");
        String symbol = sc.next();

        System.out.print("Quantity: ");
        int qty = sc.nextInt();

        for (Stock stock : market) {

            if (stock.getSymbol()
                     .equalsIgnoreCase(symbol)) {

                boolean success =
                        user.getPortfolio()
                            .sellStock(symbol, qty);

                if (success) {

                    double value =
                            stock.getPrice() * qty;

                    user.addCash(value);

                    user.getHistory().add(
                            new Transaction(
                                    symbol,
                                    qty,
                                    stock.getPrice(),
                                    "SELL"));

                    System.out.println(
                            "Stock Sold!");
                }
                else {
                    System.out.println(
                            "Not enough shares.");
                }
            }
        }
    }

    static void showPortfolio(User user,
                              List<Stock> market) {

        double totalValue = 0;

        System.out.println("\nPortfolio");

        for (Map.Entry<String, Integer> entry :
                user.getPortfolio()
                    .getHoldings()
                    .entrySet()) {

            String symbol = entry.getKey();
            int qty = entry.getValue();

            for (Stock stock : market) {

                if (stock.getSymbol()
                         .equals(symbol)) {

                    double value =
                            qty * stock.getPrice();

                    totalValue += value;

                    System.out.println(
                            symbol +
                            " | Qty: " + qty +
                            " | Value: ₹" + value);
                }
            }
        }

        System.out.println(
                "Cash Balance: ₹" +
                user.getCashBalance());

        System.out.println(
                "Portfolio Value: ₹" +
                totalValue);
    }

    static void displayHistory(User user) {

        System.out.println("\nTransaction History");

        for (Transaction t :
                user.getHistory()) {

            System.out.println(t);
        }
    }
}