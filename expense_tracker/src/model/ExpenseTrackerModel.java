package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ExpenseTrackerModel {

  private final List<Transaction> transactions;   //encapsulated transcactions list by making it private

  public ExpenseTrackerModel() {
    transactions = new ArrayList<>();
  }

  public void addTransaction(Transaction t) {
    transactions.add(t);
  }

  public void removeTransaction(Transaction t) {
    transactions.remove(t);
  }

  public List<Transaction> getTransactions() {
    return Collections.unmodifiableList(transactions);//achieved immutability by returning an immutable list
  }

}