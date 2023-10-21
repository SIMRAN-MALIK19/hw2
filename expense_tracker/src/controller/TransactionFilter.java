package controller;
import java.util.List;

import model.Transaction;
/**
 * creating TransactionFilter interface with filter method which will be overriden by ExpenseFilterByAmount ExpenseFilterByCategory classes to add filtering logic
 */
public interface TransactionFilter {
    List<Transaction> filter(List<Transaction> transactions);
}