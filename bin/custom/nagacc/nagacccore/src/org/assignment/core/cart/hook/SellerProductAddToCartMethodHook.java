package org.assignment.core.cart.hook;

import de.hybris.platform.commerceservices.order.CommerceCartModification;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.commerceservices.order.hook.CommerceAddToCartMethodHook;
import de.hybris.platform.commerceservices.order.hook.impl.ConfigurableProductAddToCartMethodHook;
import de.hybris.platform.commerceservices.service.data.CommerceCartParameter;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.servicelayer.model.ModelService;
import org.apache.commons.collections4.CollectionUtils;
import org.assignment.core.model.SellerModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SellerProductAddToCartMethodHook implements CommerceAddToCartMethodHook {

    private static final Logger LOG = LoggerFactory.getLogger(SellerProductAddToCartMethodHook.class);

    private ModelService modelService;

    @Override
    public void beforeAddToCart(CommerceCartParameter parameters) throws CommerceCartModificationException {

    }

    @Override
    public void afterAddToCart(CommerceCartParameter parameters, CommerceCartModification result)
            throws CommerceCartModificationException {
        if (result.getQuantityAdded() > 0) {
            final AbstractOrderEntryModel entry = result.getEntry();
            if (entry == null) {
                LOG.warn("No entry created");
            } else {
                final List<SellerModel> models = entry.getProduct().getProducts().stream().toList();
                if (CollectionUtils.isNotEmpty(entry.getProduct().getProducts())) {
                    entry.setSellerInfo(models.get(0));
                    this.modelService.save(entry);
                }
            }
        }

    }

    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }
}
