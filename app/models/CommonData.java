package models;

import java.util.List;
import java.util.Map;

import com.google.common.base.Optional;
import controllers.urls.ShopRoutes;
import play.i18n.Lang;
import play.mvc.Call;

/**
 * A container for stuff needed in almost every template.
 */
public class CommonData {
    private final UserContext userContext;
    private final List<Lang> availableLang;
    private final Optional<ShopCategory> currentCategory;
    private final Optional<ShopProduct> currentProduct;
    private final ShopRoutes shopRoutes;

    CommonData(UserContext userContext, List<Lang> availableLang, ShopRoutes shopRoutes,
               Optional<ShopCategory> currentCategory, Optional<ShopProduct> currentProduct) {
        this.userContext = userContext;
        this.availableLang = availableLang;
        this.currentCategory = currentCategory;
        this.currentProduct = currentProduct;
        this.shopRoutes = shopRoutes;
    }

    public UserContext context() {
        return userContext;
    }

    public List<Lang> availableLang() {
        return availableLang;
    }

    public ShopRoutes routes() {
        return shopRoutes;
    }

    public Map<Lang, Call> localizedRoutes() {
        return shopRoutes.all(currentCategory, currentProduct);
    }

    public Optional<ShopCategory> currentCategory() {
        return currentCategory;
    }

    public Optional<ShopProduct> currentProduct() {
        return currentProduct;
    }
}