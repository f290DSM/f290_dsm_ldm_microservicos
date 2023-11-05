package br.com.fatecararas.api.core.dto;

public class Product {

    private Integer id;
    private String description;
    private Double price;
    private String barcode;

    public Product(Integer id, String description, Double price, String barcode) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.barcode = barcode;
    }

    public Product() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", barcode='" + barcode + '\'' +
                '}';
    }
}
