package controllers;

import controllers.actions.SaveContext;
import io.sphere.client.ProductSort;
import io.sphere.client.facets.Facet;
import io.sphere.client.facets.Facets;
import io.sphere.client.facets.expressions.FacetExpression;
import io.sphere.client.filters.Filter;
import io.sphere.client.filters.Filters;
import io.sphere.client.filters.expressions.FilterExpression;
import io.sphere.client.filters.expressions.FilterExpressions;
import io.sphere.client.model.SearchResult;
import io.sphere.client.shop.model.Category;
import io.sphere.client.shop.model.Product;
import play.mvc.Result;
import play.mvc.With;
import sphere.ShopController;
import sphere.SearchRequest;
import views.html.index;
import views.html.listProducts;
import views.html.gridProducts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Categories extends ShopController {

    public static String QUERY_COLOR = "color";
    public static String QUERY_PRICE = "price";

    @With(SaveContext.class)
    public static Result home() {
        SearchResult<Product> searchResultNew = sphere().products().all().sort(ProductSort.price.desc).fetch();
        SearchResult<Product> searchResultOffer = sphere().products().all().sort(ProductSort.price.asc).fetch();
        return ok(index.render(searchResultNew, searchResultOffer));
    }

    @With(SaveContext.class)
    public static Result select(String categoryPath, int page, int show, String sort, String list) {
        String[] categorySlugs = categoryPath.split("/");
        String categorySlug = categorySlugs[categorySlugs.length - 1];
        Category category = sphere().categories().getBySlug(categorySlug);
        if (category == null) {
            return notFound("Category not found: " + categorySlug);
        }
        FilterExpression categoryFilter = new FilterExpressions.CategoriesOrSubcategories(Collections.singletonList(category));
        SearchRequest <Product> searchRequest = sphere().products().filter(categoryFilter);
        searchRequest = filterBy(searchRequest);
        searchRequest = sortBy(searchRequest, sort);
        searchRequest = paging(searchRequest, page, show);
        SearchResult<Product> searchResult = searchRequest.fetch();
        if (searchResult.getCount() < 1) {
            flash("product-list-info", "No products found");
        }
        if (list.isEmpty()) {
            return ok(gridProducts.render(category, searchResult));
        }
        return ok(listProducts.render(category, searchResult));
    }

    protected static SearchRequest<Product> filterBy(SearchRequest<Product> searchRequest) {
        // Filters
        List<Filter> filterList = new ArrayList<Filter>();
        // By price
        Filters.Price.DynamicRange filterPrice = new Filters.Price.DynamicRange().setQueryParam(QUERY_PRICE);
        filterList.add(filterPrice);
        // Build request
        List<FilterExpression> filterExp = bindFiltersFromRequest(filterList);
        searchRequest = searchRequest.filter(filterExp);

        // Facets
        List<Facet> facetList = new ArrayList<Facet>();
        // By color
        Facets.StringAttribute.Terms facetColor = new Facets.StringAttribute.Terms("variants.attributes.color").setQueryParam(QUERY_COLOR);
        facetList.add(facetColor);
        // Build request
        List<FacetExpression> facetExp = bindFacetsFromRequest(facetList);
        searchRequest = searchRequest.facet(facetExp);

        return searchRequest;
    }

    protected static SearchRequest<Product> sortBy(SearchRequest<Product> searchRequest, String sort) {
        switch (sort) {
            case "desc":
                searchRequest = searchRequest.sort(ProductSort.price.desc);
                break;
            case "asc":
                searchRequest = searchRequest.sort(ProductSort.price.asc);
                break;
        }
        return searchRequest;
    }

    protected static SearchRequest<Product> paging(SearchRequest<Product> searchRequest, int currentPage, int pageSize) {
        if (currentPage < 1) currentPage = 1;
        if (pageSize != 24) pageSize = 12;
        // Convert page from 1..N to 0..N-1
        currentPage--;
        return searchRequest.page(currentPage).pageSize(pageSize);
    }


}
