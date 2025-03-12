import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

// Expense class to represent individual expenses
class Expense {
    private String category;
    private double amount;
    private String description;
    private String date;

    public Expense(String category, double amount, String description) {
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Date: " + date + ", Category: " + category + ", Amount: $" + amount + ", Description: " + description;
    }
}

// ExpenseManager class to manage all expenses
class ExpenseManager {
    private List<Expense> expenses;
    private static final String FILE_NAME = "expenses.txt";

    public ExpenseManager() {
        expenses = new ArrayList<>();
        loadExpensesFromFile();
    }

    public void addExpense(String category, double amount, String description) {
        Expense newExpense = new Expense(category, amount, description);
        expenses.add(newExpense);
        saveExpensesToFile();
        System.out.println("Expense added successfully!");
    }

    public void viewDailyExpenses(String date) {
        double total = 0;
        System.out.println("Expenses for " + date + ":");
        for (Expense expense : expenses) {
            if (expense.getDate().equals(date)) {
                System.out.println(expense);
                total += expense.getAmount();
            }
        }
        System.out.println("Total expenses for " + date + ": $" + total);
    }

    public void viewWeeklyExpenses(String startDate) {
        // Implement logic to calculate weekly expenses
        // This is a simplified version; you can expand it to handle actual weeks
        double total = 0;
        System.out.println("Expenses for the week starting " + startDate + ":");
        for (Expense expense : expenses) {
            if (expense.getDate().compareTo(startDate) >= 0) {
                System.out.println(expense);
                total += expense.getAmount();
            }
        }
        System.out.println("Total expenses for the week: $" + total);
    }

    public void viewMonthlyExpenses(String month) {
        double total = 0;
        System.out.println("Expenses for " + month + ":");
        for (Expense expense : expenses) {
            if (expense.getDate().startsWith(month)) {
                System.out.println(expense);
                total += expense.getAmount();
            }
        }
        System.out.println("Total expenses for " + month + ": $" + total);
    }

    private void saveExpensesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Expense expense : expenses) {
                writer.write(expense.getDate() + "," + expense.getCategory() + "," + expense.getAmount() + "," + expense.getDescription());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving expenses to file: " + e.getMessage());
        }
    }

    private void loadExpensesFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    expenses.add(new Expense(parts[1], Double.parseDouble(parts[2]), parts[3]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading expenses from file: " + e.getMessage());
        }
    }
}

// Main class to run the application
public class DailyExpenseTracker {
    public static void main(String[] args) {
        ExpenseManager manager = new ExpenseManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nDaily Expense Tracker");
            System.out.println("1. Add Expense");
            System.out.println("2. View Daily Expenses");
            System.out.println("3. View Weekly Expenses");
            System.out.println("4. View Monthly Expenses");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter category: ");
                    String category = scanner.nextLine();
                    System.out.print("Enter amount: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter description: ");
                    String description = scanner.nextLine();
                    manager.addExpense(category, amount, description);
                    break;
                case 2:
                    System.out.print("Enter date (yyyy-MM-dd): ");
                    String date = scanner.nextLine();
                    manager.viewDailyExpenses(date);
                    break;
                case 3:
                    System.out.print("Enter start date (yyyy-MM-dd): ");
                    String startDate = scanner.nextLine();
                    manager.viewWeeklyExpenses(startDate);
                    break;
                case 4:
                    System.out.print("Enter month (yyyy-MM): ");
                    String month = scanner.nextLine();
                    manager.viewMonthlyExpenses(month);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}