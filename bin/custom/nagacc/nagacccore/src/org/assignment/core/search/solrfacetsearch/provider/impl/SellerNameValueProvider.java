package org.assignment.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractPropertyFieldValueProvider;
import org.assignment.core.model.SellerModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SellerNameValueProvider extends AbstractPropertyFieldValueProvider implements FieldValueProvider {
    private FieldNameProvider fieldNameProvider;

    @Override
    public Collection<FieldValue> getFieldValues(IndexConfig indexConfig, IndexedProperty indexedProperty, Object o)
            throws FieldValueProviderException {
        if (o instanceof ProductModel)
        {
            final ProductModel product = (ProductModel) o;

            final List<FieldValue> fieldValues = new ArrayList<FieldValue>();

            final BaseSiteModel baseSiteModel = indexConfig.getBaseSite();

            if (baseSiteModel != null && baseSiteModel.getStores() != null && !baseSiteModel.getStores().isEmpty())
            {
                for(SellerModel sel: product.getProducts()){
                    addFieldValues(
                            fieldValues,
                            indexedProperty, sel.getSellerName());
                }
            }

            return fieldValues;
        }
        else
        {
            throw new FieldValueProviderException("Cannot get stock of non-product item");
        }
    }

    protected void addFieldValues(final List<FieldValue> fieldValues, final IndexedProperty indexedProperty, final Object value)
    {
        final Collection<String> fieldNames = getFieldNameProvider().getFieldNames(indexedProperty, null);
        for (final String fieldName : fieldNames)
        {
            fieldValues.add(new FieldValue(fieldName, value));
        }
    }

    public void setFieldNameProvider(FieldNameProvider fieldNameProvider) {
        this.fieldNameProvider = fieldNameProvider;
    }

    public FieldNameProvider getFieldNameProvider() {
        return fieldNameProvider;
    }
}
