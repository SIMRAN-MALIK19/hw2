import java.util.List;
import java.util.Optional;

import javax.swing.JOptionPane;

import controller.ExpenseTrackerController;
import controller.TransactionFilter;
import model.ExpenseTrackerModel;
import model.Transaction;
import view.ExpenseTrackerView;

public class ExpenseTrackerApp {

  public static void main(String[] args) {
    
    // Create MVC components
    ExpenseTrackerModel model = new ExpenseTrackerModel();
    ExpenseTrackerView view = new ExpenseTrackerView();
    ExpenseTrackerController controller = new ExpenseTrackerController(model, view);

    // Initialize view
    view.setVisible(true);

    // Handle add transaction button clicks
    view.getAddTransactionBtn().addActionListener(e -> {
      // Get transaction data from view
      double amount = view.getAmountField();
      String category = view.getCategoryField();
      
      // Call controller to add transaction
      boolean added = controller.addTransaction(amount, category);
      
      if (!added) {
        JOptionPane.showMessageDialog(view, "Invalid amount or category entered");
        view.toFront();
        added=true;
      }

    });
    //creating action listener for apply filter button
    view.getApplyFilterBtn().addActionListener(e -> {

      
      Optional<TransactionFilter> filter = view.ret_filter();
      if (filter.isPresent()) { //performing input validation using ret_filter
        List<Transaction> transactionList = controller.applyFilter(filter.get()); //calling applyFilter method and generating a list of filtered transactions
        view.getHighlight(transactionList);//calling getHighlight to highLight filtered rows
      }
    });
  }
}

