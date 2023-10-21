package controller;
import java.util.List;

/**
 * creating ExpenseFilterByAmount class that overrides the filter function of Transaction filter interface filter on the basis of amount
 */
import model.Transaction;

public class ExpenseFilterByAmount implements TransactionFilter{
    private double input_Amount;

    public ExpenseFilterByAmount(double input_Amount) {
        this.input_Amount = input_Amount; //constructor takes user input for amount filter as parameter.
    }

    @Override
    public List<Transaction> filter(List<Transaction> transactions) {
        return transactions.stream()
            .filter(t -> t.getAmount() == input_Amount) // overriding filter method. creates a list of all transactions that have a matching amount field and returns it.
            .toList();
    }
}
