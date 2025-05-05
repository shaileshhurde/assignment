package org.assignment.facades.populators;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import org.assignment.core.model.SellerModel;
import org.assignment.facades.product.data.SellerData;

public class ProductSellerPopulator implements Populator<SellerModel, SellerData> {
    @Override
    public void populate(SellerModel sellerModel, SellerData sellerData) throws ConversionException {
          sellerData.setSellerCode(sellerModel.getSellerCode());
          sellerData.setSellerName(sellerModel.getSellerName());
    }
}
