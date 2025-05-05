package org.assignment.facades.populators;

import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.assignment.core.model.SellerModel;
import org.assignment.facades.product.data.SellerData;

public class NagAccOrderEntryPopulator implements Populator<AbstractOrderEntryModel, OrderEntryData> {
    private Converter<SellerModel, SellerData> sellerConverter;
    @Override
    public void populate(AbstractOrderEntryModel abstractOrderEntryModel, OrderEntryData orderEntryData)
            throws ConversionException {
          if(null!= abstractOrderEntryModel.getSellerInfo()){
              orderEntryData.setSellerInfo(sellerConverter.convert(abstractOrderEntryModel.getSellerInfo()));
          }
    }

    public void setSellerConverter(
            Converter<SellerModel, SellerData> sellerConverter) {
        this.sellerConverter = sellerConverter;
    }
}
