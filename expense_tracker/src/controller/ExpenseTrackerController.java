package controller;

import java.util.List;

import model.ExpenseTrackerModel;
import model.Transaction;
import view.ExpenseTrackerView;
public class ExpenseTrackerController {
  
  private ExpenseTrackerModel model;
  private ExpenseTrackerView view;

  public ExpenseTrackerController(ExpenseTrackerModel model, ExpenseTrackerView view) {
	this.model = model;
	this.view = view;

	// Set up view event handlers
  }

  public void refresh() {

	// Get transactions from model
	List<Transaction> transactions = model.getTransactions();

	// Pass to view
	view.refreshTable(transactions);

  }

  public boolean addTransaction(double amount, String category) {
	if (!InputValidation.isValidAmount(amount)) {
	  return false;
	}
	if (!InputValidation.isValidCategory(category)) {
	  return false;
	}

	Transaction t = new Transaction(amount, category);
	model.addTransaction(t);
	view.getTableModel().addRow(new Object[]{t.getAmount(), t.getCategory(), t.getTimestamp()});
	refresh();
	return true;
  }
  //defining applyFilter method that calls the filter method of TransactionFilter interface and returns the filtered(matching) list of transactions
  public List<Transaction> applyFilter(TransactionFilter transactionFilter){
	
	
	  List<Transaction> transactions = model.getTransactions();
	  return transactionFilter.filter(transactions);
  }


}