public class Product {
    private static int generalId = 15485787;
    private int id;
    private String name;
    private float price;
    private float discount;
    private String description;

    private void generateId(){
        this.id = generalId;
        generalId += 1;
    }
    public Product(String Name, float Price, float Discount, String Description){
        generateId();
        setName(Name);
        setPrice(Price);
        setDiscount(Discount);
        setDescription(Description);
    }

    public void setName(String Name){
        this.name = Name;
    }

    public void setPrice(float Price){
        this.price = Price;
    }

    public void setDiscount(float Discount){
        this.discount = Discount;
    }

    public void setDescription(String Description){
        this.description = Description;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public float getPrice() {
        return this.price;
    }

    public float getDiscount() {
        return this.discount;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return "--------------Product card-------------| ID:" + getId() + "\n"
                +  getName() + ".\nPrice: "
                + getPrice() + "$\nDiscount: "
                + getDiscount() + "$\nAdditional description: "
                + getDescription() +
                "\n---------------------------------------------";
    }
}
