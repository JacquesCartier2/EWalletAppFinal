public class Transaction {
    String type;
    double amount;
    String description;

    public Transaction(String type, double amount, String description){
        this.type = type;
        this.amount = amount;
        this.description = description;
    }

    public String getType(){
        return type;
    }

    public double getAmount(){
        return amount;
    }

    public String getDescription(){
        return description;
    }

}
