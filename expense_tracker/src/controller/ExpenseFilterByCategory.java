package controller;
import java.util.List;

/**
 * creating ExpenseFilterByCategory class that overrides the filter function of Transaction filter interface to filter on the basis of category
 */
import model.Transaction;
public class ExpenseFilterByCategory implements TransactionFilter {
    private String filterCategory;

    public ExpenseFilterByCategory(String filterCategory) {
        this.filterCategory = filterCategory; //constructor takes user input for category filter as parameter.
    }

    @Override
    public List<Transaction> filter(List<Transaction> transactions) {
        return transactions.stream()
            .filter(t -> t.getCategory().equalsIgnoreCase(filterCategory)) // overriding filter method. creates a list of all transactions that have a matching category
            .toList();
    }
}
