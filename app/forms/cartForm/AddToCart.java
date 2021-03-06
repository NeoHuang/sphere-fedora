package forms.cartForm;

import play.data.validation.Constraints;


public class AddToCart {

    @Constraints.Required(message = "Product required")
    public String productId;

    @Constraints.Required(message = "Variant required")
    public int variantId;

    @Constraints.Required(message = "Quantity required")
    @Constraints.Min(1)
    @Constraints.Max(10)
    public int quantity;

    public String size;


    public AddToCart() {

    }

}
