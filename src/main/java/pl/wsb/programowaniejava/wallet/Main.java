package pl.wsb.programowaniejava.wallet;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import pl.wsb.programowaniejava.wallet.entities.*;
import pl.wsb.programowaniejava.wallet.managers.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        try (SessionFactory sessionFactory = new Configuration()
                /*.addPackage("pl.wsb.programowaniejava.wallet.entities") */
                .addAnnotatedClass(Currency.class)
                .addAnnotatedClass(ExpenseCategory.class)
                .addAnnotatedClass(ExpenseSubcategory.class)
                .addAnnotatedClass(ExpenseTransaction.class)
                .addAnnotatedClass(IncomeCategory.class)
                .addAnnotatedClass(IncomeTransaction.class)
                .addAnnotatedClass(LongTermInvestment.class)
                .addAnnotatedClass(LongTermInvestmentTransaction.class)
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(ShortTermInvestment.class)
                .addAnnotatedClass(ShortTermInvestmentTransaction.class)
                .buildSessionFactory()) {

            System.out.println("Welcome to the mWallet app!");

            while(true) {
                Scanner scanner1 = new Scanner(System.in);
                System.out.print("Please press any key to add another family member or [c] to continue: ");
                String newPerson = scanner1.nextLine();
                if (newPerson.equals("c")) {
                    break;
                }
                Scanner scanner2 = new Scanner(System.in);
                System.out.print("Please enter a valid first name: ");
                String firstName = scanner2.nextLine();

                Scanner scanner3 = new Scanner(System.in);
                System.out.print("Please enter a valid last name: ");
                String lastName = scanner3.nextLine();

                Scanner scanner4 = new Scanner(System.in);
                System.out.print("Please enter a valid date of birth [yyyy-mm-dd]: ");
                String date = scanner4.nextLine();
                LocalDate dateOfBirth = LocalDate.parse(date);

                Scanner scanner5 = new Scanner(System.in);
                System.out.print("Please enter a valid e-mail address: ");
                String email = scanner5.nextLine();

                PersonManager personManager = new PersonManager(sessionFactory);
                personManager.addPerson(firstName, lastName, dateOfBirth, email);
                System.out.printf("Check (1): %s%n", personManager.getPersons());

                /*PersonManager personManager = new PersonManager(sessionFactory);
                personManager.addPerson("Piotr", "Pastusiak", LocalDate.parse("1990-03-30"),"piterhawk16@gmail.com");
                System.out.printf("Check: %s%n", personManager.getPersons());

                personManager.getPerson(1);
                System.out.printf("Check: %s%n", personManager.getPersons());

                personManager.updatePerson(new Person("Piotr", "Pastusiak", LocalDate.parse("1990-03-30"),"piotr.pastusiak.90@gmail.com"), 1);
                System.out.printf("Check: %s%n", personManager.getPersons());

                personManager.deletePerson(1);
                System.out.printf("Check: %s%n", personManager.getPersons());*/
            }

            CurrencyManager currencyManager = new CurrencyManager(sessionFactory);
            currencyManager.addCurrency(new Currency("PLN","Polish Zloty",null));
            currencyManager.addCurrency(new Currency("EUR","Euro",null));
            currencyManager.addCurrency(new Currency("USD","US Dollar",null));
            currencyManager.addCurrency(new Currency("GBP","British Pound",null));
            currencyManager.addCurrency(new Currency("CHF","Swiss Franc",null));
            System.out.printf("Check (2): %s%n", currencyManager.getCurrencies());

            /*currencyManager.updateCurrency(new Currency("PLN","Polish Zloty",new BigDecimal(2.0000)));
            System.out.printf("Check: %s%n", currencyManager.getCurrencies());

            currencyManager.deleteCurrency("PLN");
            System.out.printf("Check: %s%n", currencyManager.getCurrencies());*/

            IncomeCatManager incomeCatManager = new IncomeCatManager(sessionFactory);
            incomeCatManager.addIncomeCategory(new IncomeCategory("SAL","Salary"));
            incomeCatManager.addIncomeCategory(new IncomeCategory("BON","Bonus salary"));
            incomeCatManager.addIncomeCategory(new IncomeCategory("BUS","Business activity"));
            incomeCatManager.addIncomeCategory(new IncomeCategory("PSV","Passive income"));
            incomeCatManager.addIncomeCategory(new IncomeCategory("SOC","Social grants"));
            incomeCatManager.addIncomeCategory(new IncomeCategory("OTH","Other"));
            System.out.printf("Check (3): %s%n", incomeCatManager.getIncomeCategories());

            while(true) {
                Scanner scanner13 = new Scanner(System.in);
                System.out.print("Please press any key to add new income or [c] to continue: ");
                String newIncome = scanner13.nextLine();
                if (newIncome.equals("c")) {
                    break;
                }

                Scanner scanner14 = new Scanner(System.in);
                System.out.print("Please enter a valid transaction name: ");
                String incomeName = scanner14.nextLine();

                Scanner scanner15 = new Scanner(System.in);
                System.out.print("Please enter a valid transaction date [yyyy-mm-dd]: ");
                String incomeDate = scanner15.nextLine();
                LocalDate incomeLocalDate = LocalDate.parse(incomeDate);

                Scanner scanner16 = new Scanner(System.in);
                System.out.print("Please enter a valid amount: ");
                BigDecimal incomeAmount = scanner16.nextBigDecimal();

                Scanner scanner17 = new Scanner(System.in);
                System.out.print("Please enter a valid currency: ");
                String incomeCurrency = scanner17.nextLine();

                Scanner scanner18 = new Scanner(System.in);
                System.out.print("Please enter a valid income category: ");
                String incomeCategory = scanner18.nextLine();

                Scanner scanner19 = new Scanner(System.in);
                System.out.print("Please enter a valid person: ");
                int incomePerson = scanner19.nextInt();

                IncomeTxnManager incomeTxnManager = new IncomeTxnManager(sessionFactory);
                incomeTxnManager.addIncomeTransaction(incomeName, incomeLocalDate, incomeAmount, new Currency(incomeCurrency), new IncomeCategory(incomeCategory), new Person (incomePerson));
                System.out.printf("Check (4): %s%n", incomeTxnManager.getIncomeTransactions());
            }

            /*IncomeTxnManager incomeTxnManager = new IncomeTxnManager(sessionFactory);
            incomeTxnManager.addIncomeTransaction("lol", LocalDate.parse("2023-04-04"), new BigDecimal(5000), new Currency("PLN"), new IncomeCategory("SAL"), new Person (1));
            incomeTxnManager.addIncomeTransaction("lol2", LocalDate.parse("2023-05-05"), new BigDecimal(1000), new Currency("PLN"), new IncomeCategory("BON"), new Person (1));
            System.out.printf("Check: %s%n", incomeTxnManager.getIncomeTransactions());*/

            ExpenseCatManager expenseCatManager = new ExpenseCatManager(sessionFactory);
            expenseCatManager.addExpenseCategory(new ExpenseCategory("HSE","House/flat"));
            expenseCatManager.addExpenseCategory(new ExpenseCategory("SHP","Shopping"));
            expenseCatManager.addExpenseCategory(new ExpenseCategory("HEA","Healthcare"));
            expenseCatManager.addExpenseCategory(new ExpenseCategory("CAR","Car"));
            expenseCatManager.addExpenseCategory(new ExpenseCategory("TRN","Transport"));
            expenseCatManager.addExpenseCategory(new ExpenseCategory("EDU","Education"));
            expenseCatManager.addExpenseCategory(new ExpenseCategory("ENT","Entertainment"));
            expenseCatManager.addExpenseCategory(new ExpenseCategory("LOA","Loans/liabilities"));
            expenseCatManager.addExpenseCategory(new ExpenseCategory("OTH","Other"));
            System.out.printf("Check (5): %s%n", expenseCatManager.getExpenseCategories());

            ExpenseSubcatManager expenseSubcatManager = new ExpenseSubcatManager(sessionFactory);
            expenseSubcatManager.addExpenseSubcategory(new ExpenseSubcategory("CRE","House credit",new ExpenseCategory("HSE")));
            expenseSubcatManager.addExpenseSubcategory(new ExpenseSubcategory("BILL","House bills",new ExpenseCategory("HSE")));
            expenseSubcatManager.addExpenseSubcategory(new ExpenseSubcategory("RENO","House renovation",new ExpenseCategory("HSE")));
            expenseSubcatManager.addExpenseSubcategory(new ExpenseSubcategory("REPHSE","House repairs",new ExpenseCategory("HSE")));
            expenseSubcatManager.addExpenseSubcategory(new ExpenseSubcategory("OTHHSE","Other house expenses",new ExpenseCategory("HSE")));
            expenseSubcatManager.addExpenseSubcategory(new ExpenseSubcategory("FOOD","Food",new ExpenseCategory("SHP")));
            expenseSubcatManager.addExpenseSubcategory(new ExpenseSubcategory("CHEM","Chemicals",new ExpenseCategory("SHP")));
            expenseSubcatManager.addExpenseSubcategory(new ExpenseSubcategory("CLO","Clothes",new ExpenseCategory("SHP")));
            expenseSubcatManager.addExpenseSubcategory(new ExpenseSubcategory("ELEC","Electronic devices",new ExpenseCategory("SHP")));
            expenseSubcatManager.addExpenseSubcategory(new ExpenseSubcategory("OTHSHP","Other shopping expenses",new ExpenseCategory("SHP")));
            expenseSubcatManager.addExpenseSubcategory(new ExpenseSubcategory("DOC","Doctor appointments",new ExpenseCategory("HEA")));
            expenseSubcatManager.addExpenseSubcategory(new ExpenseSubcategory("TRT","Health treatments",new ExpenseCategory("HEA")));
            expenseSubcatManager.addExpenseSubcategory(new ExpenseSubcategory("MED","Medicines",new ExpenseCategory("HEA")));
            expenseSubcatManager.addExpenseSubcategory(new ExpenseSubcategory("COS","Cosmetics",new ExpenseCategory("HEA")));
            expenseSubcatManager.addExpenseSubcategory(new ExpenseSubcategory("OTHHEA","Other health expenses",new ExpenseCategory("HEA")));
            expenseSubcatManager.addExpenseSubcategory(new ExpenseSubcategory("FUEL","Fuel",new ExpenseCategory("CAR")));
            expenseSubcatManager.addExpenseSubcategory(new ExpenseSubcategory("REPCAR","Car repairs",new ExpenseCategory("CAR")));
            expenseSubcatManager.addExpenseSubcategory(new ExpenseSubcategory("FEES","Car fees",new ExpenseCategory("CAR")));
            expenseSubcatManager.addExpenseSubcategory(new ExpenseSubcategory("OTHCAR","Other car expenses",new ExpenseCategory("CAR")));
            expenseSubcatManager.addExpenseSubcategory(new ExpenseSubcategory("TIX","Transport tickets",new ExpenseCategory("TRN")));
            expenseSubcatManager.addExpenseSubcategory(new ExpenseSubcategory("OTHTRN","Other transport expenses",new ExpenseCategory("TRN")));
            expenseSubcatManager.addExpenseSubcategory(new ExpenseSubcategory("EDU","Education",new ExpenseCategory("EDU")));
            expenseSubcatManager.addExpenseSubcategory(new ExpenseSubcategory("ENT","Entertainment",new ExpenseCategory("ENT")));
            expenseSubcatManager.addExpenseSubcategory(new ExpenseSubcategory("LOA","Loans/liabilities",new ExpenseCategory("LOA")));
            expenseSubcatManager.addExpenseSubcategory(new ExpenseSubcategory("OTH","Other",new ExpenseCategory("OTH")));
            System.out.printf("Check (6): %s%n", expenseSubcatManager.getExpenseSubcategories());

            while(true) {
                Scanner scanner6 = new Scanner(System.in);
                System.out.print("Please press any key to add a new expense or [c] to continue: ");
                String newExpense = scanner6.nextLine();
                if (newExpense.equals("c")) {
                    break;
                }

                Scanner scanner7 = new Scanner(System.in);
                System.out.print("Please enter a valid transaction name: ");
                String expenseName = scanner7.nextLine();

                Scanner scanner8 = new Scanner(System.in);
                System.out.print("Please enter a valid transaction date [yyyy-mm-dd]: ");
                String expenseDate = scanner8.nextLine();
                LocalDate expenseLocalDate = LocalDate.parse(expenseDate);

                Scanner scanner9 = new Scanner(System.in);
                System.out.print("Please enter a valid amount: ");
                BigDecimal expenseAmount = scanner9.nextBigDecimal();

                Scanner scanner10 = new Scanner(System.in);
                System.out.print("Please enter a valid currency: ");
                String expenseCurrency = scanner10.nextLine();

                Scanner scanner11 = new Scanner(System.in);
                System.out.print("Please enter a valid expense category: ");
                String expenseCategory = scanner11.nextLine();

                Scanner scanner12 = new Scanner(System.in);
                System.out.print("Please enter a valid person: ");
                int expensePerson = scanner12.nextInt();

                ExpenseTxnManager expenseTxnManager = new ExpenseTxnManager(sessionFactory);
                expenseTxnManager.addExpenseTransaction(expenseName, expenseLocalDate, expenseAmount, new Currency(expenseCurrency), new ExpenseSubcategory(expenseCategory), new Person (expensePerson));
                System.out.printf("Check (7): %s%n", expenseTxnManager.getExpenseTransactions());
            }

            /*ExpenseTxnManager expenseTxnManager = new ExpenseTxnManager(sessionFactory);
            expenseTxnManager.addExpenseTransaction("lol", LocalDate.parse("2023-04-04"), new BigDecimal(500), new Currency("PLN"), new ExpenseSubcategory("BILL"), new Person (1));
            expenseTxnManager.addExpenseTransaction("lol2", LocalDate.parse("2023-05-05"), new BigDecimal(1500), new Currency("PLN"), new ExpenseSubcategory("TIX"), new Person (1));
            System.out.printf("Check: %s%n", expenseTxnManager.getExpenseTransactions());*/

            try (Session session = sessionFactory.openSession()) {
                Query<BigDecimal> incomePLN = session.createQuery("SELECT sum(amount) FROM IncomeTransaction WHERE currency = 'PLN' AND YEAR(date) = YEAR(CURRENT_TIMESTAMP) AND MONTH(date) = MONTH(CURRENT_TIMESTAMP)", BigDecimal.class);
                BigDecimal sumIncomePLN = incomePLN.list().get(0);
                /*Query<BigDecimal> incomeEUR = session.createQuery("SELECT sum(amount) FROM IncomeTransaction WHERE currency = 'EUR' AND YEAR(date) = YEAR(CURRENT_TIMESTAMP) AND MONTH(date) = MONTH(CURRENT_TIMESTAMP)", BigDecimal.class);
                BigDecimal sumIncomeEUR = incomeEUR.list().get(0);
                Query<BigDecimal> incomeUSD = session.createQuery("SELECT sum(amount) FROM IncomeTransaction WHERE currency = 'USD' AND YEAR(date) = YEAR(CURRENT_TIMESTAMP) AND MONTH(date) = MONTH(CURRENT_TIMESTAMP)", BigDecimal.class);
                BigDecimal sumIncomeUSD = incomeUSD.list().get(0);
                Query<BigDecimal> incomeGBP = session.createQuery("SELECT sum(amount) FROM IncomeTransaction WHERE currency = 'GBP' AND YEAR(date) = YEAR(CURRENT_TIMESTAMP) AND MONTH(date) = MONTH(CURRENT_TIMESTAMP)", BigDecimal.class);
                BigDecimal sumIncomeGBP = incomeGBP.list().get(0);
                Query<BigDecimal> incomeCHF = session.createQuery("SELECT sum(amount) FROM IncomeTransaction WHERE currency = 'CHF' AND YEAR(date) = YEAR(CURRENT_TIMESTAMP) AND MONTH(date) = MONTH(CURRENT_TIMESTAMP)", BigDecimal.class);
                BigDecimal sumIncomeCHF = incomeCHF.list().get(0);*/
                Query<BigDecimal> expensePLN = session.createQuery("SELECT sum(amount) FROM ExpenseTransaction WHERE currency = 'PLN' AND YEAR(date) = YEAR(CURRENT_TIMESTAMP) AND MONTH(date) = MONTH(CURRENT_TIMESTAMP)", BigDecimal.class);
                BigDecimal sumExpensePLN = expensePLN.list().get(0);
                /*Query<BigDecimal> expenseEUR = session.createQuery("SELECT sum(amount) FROM ExpenseTransaction WHERE currency = 'EUR' AND YEAR(date) = YEAR(CURRENT_TIMESTAMP) AND MONTH(date) = MONTH(CURRENT_TIMESTAMP)", BigDecimal.class);
                BigDecimal sumExpenseEUR = expenseEUR.list().get(0);
                Query<BigDecimal> expenseUSD = session.createQuery("SELECT sum(amount) FROM ExpenseTransaction WHERE currency = 'USD' AND YEAR(date) = YEAR(CURRENT_TIMESTAMP) AND MONTH(date) = MONTH(CURRENT_TIMESTAMP)", BigDecimal.class);
                BigDecimal sumExpenseUSD = expenseUSD.list().get(0);
                Query<BigDecimal> expenseGBP = session.createQuery("SELECT sum(amount) FROM ExpenseTransaction WHERE currency = 'GBP' AND YEAR(date) = YEAR(CURRENT_TIMESTAMP) AND MONTH(date) = MONTH(CURRENT_TIMESTAMP)", BigDecimal.class);
                BigDecimal sumExpenseGBP = expenseGBP.list().get(0);
                Query<BigDecimal> expenseCHF = session.createQuery("SELECT sum(amount) FROM ExpenseTransaction WHERE currency = 'CHF' AND YEAR(date) = YEAR(CURRENT_TIMESTAMP) AND MONTH(date) = MONTH(CURRENT_TIMESTAMP)", BigDecimal.class);
                BigDecimal sumExpenseCHF = expenseCHF.list().get(0);*/
                System.out.printf("Total monthly income [PLN]: %s%n", sumIncomePLN);
                /*System.out.printf("Total monthly income [EUR]: %s%n", sumIncomeEUR);
                System.out.printf("Total monthly income [USD]: %s%n", sumIncomeUSD);
                System.out.printf("Total monthly income [GBP]: %s%n", sumIncomeGBP);
                System.out.printf("Total monthly income [CHF]: %s%n", sumIncomeCHF);*/
                System.out.printf("Total monthly expenses [PLN]: %s%n", sumExpensePLN);
                /*System.out.printf("Total monthly expenses [EUR]: %s%n", sumExpenseEUR);
                System.out.printf("Total monthly expenses [USD]: %s%n", sumExpenseUSD);
                System.out.printf("Total monthly expenses [GBP]: %s%n", sumExpenseGBP);
                System.out.printf("Total monthly expenses [CHF]: %s%n", sumExpenseCHF);*/
                BigDecimal balancePLN = sumIncomePLN.subtract(sumExpensePLN);
                System.out.printf("Total monthly balance [PLN]: %s%n", balancePLN);
                /*System.out.printf("Total monthly balance [EUR]: %s%n", sumIncomeEUR.subtract(sumExpenseEUR));
                System.out.printf("Total monthly balance [USD]: %s%n", sumIncomeUSD.subtract(sumExpenseUSD));
                System.out.printf("Total monthly balance [GBP]: %s%n", sumIncomeGBP.subtract(sumExpenseGBP));
                System.out.printf("Total monthly balance [CHF]: %s%n", sumIncomeCHF.subtract(sumExpenseCHF));*/

                if (balancePLN.signum() > 0) {
                    System.out.printf("Well done! Your monthly financial balance is positive and you can save or invest: %s PLN :) %n", balancePLN);

                    ShortTermInvestManager shortTermInvestManager = new ShortTermInvestManager(sessionFactory);
                    shortTermInvestManager.addShortTermInvestment(new ShortTermInvestment("KO","Fixed-interest-rate savings account"));
                    shortTermInvestManager.addShortTermInvestment(new ShortTermInvestment("LOK","Fixed-interest-rate deposit"));
                    shortTermInvestManager.addShortTermInvestment(new ShortTermInvestment("OTS","Fixed-interest-rate 3-month bonds"));
                    shortTermInvestManager.addShortTermInvestment(new ShortTermInvestment("ROR","Variable-interest-rate 1-year bonds"));
                    shortTermInvestManager.addShortTermInvestment(new ShortTermInvestment("DOR","Variable-interest-rate 2-year bonds"));
                    shortTermInvestManager.addShortTermInvestment(new ShortTermInvestment("TOS","Fixed-interest-rate 3-year bonds"));
                    System.out.printf("Check (8): %s%n", shortTermInvestManager.getShortTermInvestments());

                    while(true) {
                        Scanner scanner20 = new Scanner(System.in);
                        System.out.print("Please press any key to add a new short-term investment or [c] to continue: ");
                        String newShortTermInvestment = scanner20.nextLine();
                        if (newShortTermInvestment.equals("c")) {
                            break;
                        }

                        Scanner scanner21 = new Scanner(System.in);
                        System.out.print("Please enter a valid transaction name: ");
                        String shortTermInvestmentName = scanner21.nextLine();

                        Scanner scanner22 = new Scanner(System.in);
                        System.out.print("Please enter a valid transaction date [yyyy-mm-dd]: ");
                        String shortTermInvestmentDate = scanner22.nextLine();
                        LocalDate shortTermInvestmentLocalDate = LocalDate.parse(shortTermInvestmentDate);

                        Scanner scanner23 = new Scanner(System.in);
                        System.out.print("Please enter a valid amount: ");
                        BigDecimal shortTermInvestmentAmount = scanner23.nextBigDecimal();

                        Scanner scanner24 = new Scanner(System.in);
                        System.out.print("Please enter a valid currency: ");
                        String shortTermInvestmentCurrency = scanner24.nextLine();

                        Scanner scanner25 = new Scanner(System.in);
                        System.out.print("Please enter a valid short-term investment category: ");
                        String shortTermInvestmentCategory = scanner25.nextLine();

                        Scanner scanner26 = new Scanner(System.in);
                        System.out.print("Please enter a valid person: ");
                        int shortTermInvestmentPerson = scanner26.nextInt();

                        ShortTermInvestTxnManager shortTermInvestTxnManager = new ShortTermInvestTxnManager(sessionFactory);
                        shortTermInvestTxnManager.addShortTermInvestmentTransaction(shortTermInvestmentName, shortTermInvestmentLocalDate, shortTermInvestmentAmount, new Currency(shortTermInvestmentCurrency), new ShortTermInvestment(shortTermInvestmentCategory), new Person (shortTermInvestmentPerson));
                        System.out.printf("Check (9): %s%n", shortTermInvestTxnManager.getShortTermInvestmentTransactions());
                    }

                    LongTermInvestManager longTermInvestManager = new LongTermInvestManager(sessionFactory);
                    longTermInvestManager.addLongTermInvestment(new LongTermInvestment("IKE","Individual Retirement Account"));
                    longTermInvestManager.addLongTermInvestment(new LongTermInvestment("IKZE","Individual Retirement Protection Account"));
                    longTermInvestManager.addLongTermInvestment(new LongTermInvestment("PPE","Employee Pension Schemes"));
                    longTermInvestManager.addLongTermInvestment(new LongTermInvestment("PPK","Employee Capital Plans"));
                    longTermInvestManager.addLongTermInvestment(new LongTermInvestment("COI","Inflation-indexed 4-year bonds"));
                    longTermInvestManager.addLongTermInvestment(new LongTermInvestment("ROS","Inflation-indexed 6-year bonds"));
                    longTermInvestManager.addLongTermInvestment(new LongTermInvestment("EDO","Inflation-indexed 10-year bonds"));
                    longTermInvestManager.addLongTermInvestment(new LongTermInvestment("ROD","Inflation-indexed 12-year bonds"));
                    System.out.printf("Check (10): %s%n", longTermInvestManager.getLongTermInvestments());

                    while(true) {
                        Scanner scanner27 = new Scanner(System.in);
                        System.out.print("Please press any key to add a new long-term investment or [c] to continue: ");
                        String newLongTermInvestment = scanner27.nextLine();
                        if (newLongTermInvestment.equals("c")) {
                            break;
                        }
                        Scanner scanner28 = new Scanner(System.in);
                        System.out.print("Please enter a valid transaction name: ");
                        String longTermInvestmentName = scanner28.nextLine();

                        Scanner scanner29 = new Scanner(System.in);
                        System.out.print("Please enter a valid transaction date [yyyy-mm-dd]: ");
                        String longTermInvestmentDate = scanner29.nextLine();
                        LocalDate longTermInvestmentLocalDate = LocalDate.parse(longTermInvestmentDate);

                        Scanner scanner30 = new Scanner(System.in);
                        System.out.print("Please enter a valid amount: ");
                        BigDecimal longTermInvestmentAmount = scanner30.nextBigDecimal();

                        Scanner scanner31 = new Scanner(System.in);
                        System.out.print("Please enter a valid currency: ");
                        String longTermInvestmentCurrency = scanner31.nextLine();

                        Scanner scanner32 = new Scanner(System.in);
                        System.out.print("Please enter a valid long-term investment category: ");
                        String longTermInvestmentCategory = scanner32.nextLine();

                        Scanner scanner33 = new Scanner(System.in);
                        System.out.print("Please enter a valid person: ");
                        int longTermInvestmentPerson = scanner33.nextInt();

                        LongTermInvestTxnManager longTermInvestTxnManager = new LongTermInvestTxnManager(sessionFactory);
                        longTermInvestTxnManager.addLongTermInvestmentTransaction(longTermInvestmentName, longTermInvestmentLocalDate, longTermInvestmentAmount, new Currency(longTermInvestmentCurrency), new LongTermInvestment(longTermInvestmentCategory), new Person(longTermInvestmentPerson));
                        System.out.printf("Check (11): %s%n", longTermInvestTxnManager.getLongTermInvestmentTransactions());
                    }
                } else {
                    System.out.printf("Unfortunately, your monthly financial balance is not positive: %s PLN and does not allow you to save or invest any money :( %n", balancePLN);
                }
            }

        } catch (Exception e) {
            System.out.printf("Failed: %s%n", e.getMessage());

        }

        System.out.println("Thank you for using our app!");

    }
}