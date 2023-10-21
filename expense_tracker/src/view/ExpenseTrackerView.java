package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.text.NumberFormat;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.ExpenseFilterByAmount;
import controller.ExpenseFilterByCategory;
import controller.InputValidation;
import controller.TransactionFilter;
import model.Transaction;


public class ExpenseTrackerView extends JFrame {

  private JTable transactionsTable;
  private JButton addTransactionBtn;
  private JFormattedTextField amountField;
  private JTextField categoryField;
  private DefaultTableModel model;
  private JButton applyFilterBtn;// creating new apply filter button
  private JFormattedTextField filterAmountField;// creating filter amount text filed for user input
  private JTextField filterCategoryField;// creating category amount text filed for user input


  public ExpenseTrackerView() {
    setTitle("Expense Tracker"); // Set title
    setSize(1200, 800); // Make GUI larger

    String[] columnNames = {"serial", "Amount", "Category", "Date"};
    this.model = new DefaultTableModel(columnNames, 0);

    addTransactionBtn = new JButton("Add Transaction");
    // Create UI components
    JLabel amountLabel = new JLabel("Amount:");
    NumberFormat format = NumberFormat.getNumberInstance();

    amountField = new JFormattedTextField(format);
    amountField.setColumns(10);

    
    JLabel categoryLabel = new JLabel("Category:");
    categoryField = new JTextField(10);
    //creating UI components for to support filtering functionality
    applyFilterBtn = new JButton("Apply Filter");

    JLabel filterAmountLabel = new JLabel("Filter Amount:");
    filterAmountField = new JFormattedTextField(format);
    filterAmountField.setFocusLostBehavior(3);

    filterAmountField.setColumns(10);
    JLabel filterCategoryLabel = new JLabel("Filter Category:");
    filterCategoryField = new JTextField(10);

    // Create table
    transactionsTable = new JTable(model);
  
    // Layout components
    JPanel inputPanel = new JPanel();
    inputPanel.add(amountLabel);
    inputPanel.add(amountField);
    inputPanel.add(categoryLabel);
    inputPanel.add(categoryField);
    inputPanel.add(addTransactionBtn);
    //adding filter components
    inputPanel.add(filterAmountLabel);
    inputPanel.add(filterAmountField);
    inputPanel.add(filterCategoryLabel);
    inputPanel.add(filterCategoryField);
    inputPanel.add(applyFilterBtn);

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(addTransactionBtn);
    buttonPanel.add(applyFilterBtn); //adding apply filter button

  
    // Add panels to frame
    add(inputPanel, BorderLayout.NORTH);
    add(new JScrollPane(transactionsTable), BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);
  
    // Set frame properties
    setSize(1000, 700);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  
  }

  public void refreshTable(List<Transaction> transactions) {
      // Clear existing rows
      model.setRowCount(0);
      // Get row count
      int rowNum = model.getRowCount();
      double totalCost=0;
      // Calculate total cost
      for(Transaction t : transactions) {
        totalCost+=t.getAmount();
      }
      // Add rows from transactions list
      for(Transaction t : transactions) {
        model.addRow(new Object[]{rowNum+=1,t.getAmount(), t.getCategory(), t.getTimestamp()});
      }
      // Add total row
      Object[] totalRow = {"Total", null, null, totalCost};
      model.addRow(totalRow);
  
      // Fire table update
      transactionsTable.updateUI();
  
  }
  public JButton getAddTransactionBtn() {
    return addTransactionBtn;
  }
  public JButton getApplyFilterBtn() {
    return applyFilterBtn;
  }
  public DefaultTableModel getTableModel() {
    return model;
  }
  // Other view methods
    public JTable getTransactionsTable() {
    return transactionsTable;
  }

  public double getAmountField() {
    if(amountField.getText().isEmpty()) {
      return 0;
    }
    else {
      double amount = Double.parseDouble(amountField.getText());
      return amount;
    }
  }
  //defining getter and setter methods for filter text fields
  public void setAmountField(JFormattedTextField amountField) {
    this.amountField = amountField;
  }

  
  public String getCategoryField() {
    return categoryField.getText();
  }

  public void setCategoryField(JTextField categoryField) {
    this.categoryField = categoryField;
  }
  public double getFilterAmountField() {
      //creating a try and catch block to alert against string input in filter amount field
      try{
        double amount = Double.parseDouble(
        filterAmountField.getText());
        return amount;
      }
      catch(Exception e){
        JOptionPane.showMessageDialog(this, "please enter a numerical value");
        toFront();
        return 0.0;
      }
      
      
  }
  public void setFilterAmountField(JFormattedTextField filterAmountField) {
    this.filterAmountField = filterAmountField;
  }
  public String getFilterCategoryField() {
        return filterCategoryField.getText();
  }
  //defining ret_filter method to validate user input and making sure only valid input is passed for filtering
  public Optional<TransactionFilter> ret_filter() {
    if (!filterAmountField.getText().isEmpty()){
      if (!InputValidation.isValidAmount(getFilterAmountField())) {
        JOptionPane.showMessageDialog(this, "Invalid amount entered");
        toFront();
      }
      else{
        return Optional.of(new ExpenseFilterByAmount(getFilterAmountField()));
      }
    }
    else if (!filterCategoryField.getText().isEmpty()){
      if (!InputValidation.isValidCategory(getFilterCategoryField())) {
        JOptionPane.showMessageDialog(this, "Invalid category entered");
        toFront();
      }
      else{
        return Optional.of(new ExpenseFilterByCategory(getFilterCategoryField()));
      }
    }
    return Optional.empty();
  }
  //defining method for highlighting filtered rows
  public void getHighlight(List<Transaction> transactionList) {
    transactionsTable.clearSelection();
    int k = 0;
    for (int i = 0; i < transactionsTable.getRowCount(); i++) {
      //this loop iterates through rows of transactions
      Object[] rowData = new Object[transactionsTable.getColumnCount()] ;
      for(int j = 0; j < transactionsTable.getColumnCount(); j++) {
        // this row iterates through compnents(timestamp,amount,category) of the transaction row
        rowData[j] = transactionsTable.getValueAt(i, j);//creating an array of individual components of transactions
      }
      //if condition checks if the transaction components matches with the components of filtered list
      if (rowData[1].equals(transactionList.get(k).getAmount()) && rowData[2].equals(transactionList.get(k).getCategory()) && rowData[3].equals(transactionList.get(k).getTimestamp())){
        transactionsTable.addRowSelectionInterval(i, i);
        transactionsTable.setSelectionBackground(new Color(173, 255, 168));//highlighting matching rows in green
        k++;
      }
    }

  }
  //setter method for filter category field
  public void setFilterCategoryField(JTextField filterCategoryField) {
    this.filterCategoryField = filterCategoryField;
  }

}
