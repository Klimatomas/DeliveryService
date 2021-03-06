package cz.muni.fi.pa165.deliveryservice.api.dto;


import cz.muni.fi.pa165.deliveryservice.api.dto.utils.InvalidPriceException;
import cz.muni.fi.pa165.deliveryservice.api.dto.utils.InvalidWeightException;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Created by Pavel on 25. 11. 2015.
 * @author Pavel
 * @author Matej Leško
 */
public class ProductCreateDTO {

    @NotNull
    private String name;

    @NotNull
    private LocalDate addedDate;

    private Long price;

    private Long weight;

    private OrderDTO order;

    public ProductCreateDTO() {
        addedDate = LocalDate.now();
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductCreateDTO)) return false;

        ProductCreateDTO that = (ProductCreateDTO) o;

        if (!price.equals(that.price)) return false;
        if (!name.equals(that.name)) return false;
        if (!weight.equals(that.weight)) return false;
        return addedDate.equals(that.addedDate);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + addedDate.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + weight.hashCode();
        return result;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(LocalDate addedDate) {
        this.addedDate = addedDate;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) throws InvalidPriceException {
        if (price < 0)
            throw new InvalidPriceException();
        this.price = price;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) throws InvalidWeightException {
        if (weight < 0)
            throw new InvalidWeightException();
        this.weight = weight;
    }

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }
}
