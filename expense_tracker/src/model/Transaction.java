package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
  // making amount,category and  timestamp as private final for encapsulation and immutability
  private final double amount;
  private final String category;
  private final String timestamp;

  public Transaction(double amount, String category) {
    this.amount = amount;
    this.category = category;
    this.timestamp = generateTimestamp();
  }
  //removed the setter functions for amount,category and timestamp field to maintain immutability(once object is created these fields should not be changed)
  public double getAmount() {
    return amount;
  }

  public String getCategory() {
    return category;
  }

  public String getTimestamp() {
    return timestamp;
  }

  private String generateTimestamp() {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm"); 
    return sdf.format(new Date());
  }

}