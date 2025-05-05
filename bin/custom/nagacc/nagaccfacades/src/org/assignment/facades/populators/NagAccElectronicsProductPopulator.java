package org.assignment.facades.populators;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.apache.commons.collections4.CollectionUtils;
import org.assignment.core.model.SellerModel;
import org.assignment.facades.product.data.SellerData;

import java.util.ArrayList;
import java.util.List;

public class NagAccElectronicsProductPopulator implements Populator<ProductModel, ProductData> {

    private Converter<SellerModel, SellerData> sellerConverter;
    @Override
    public void populate(ProductModel productModel, ProductData productData) throws ConversionException {
           if(productModel instanceof ProductModel) {
               if (CollectionUtils.isNotEmpty(productModel.getProducts())) {
                   List<SellerData> sellerDataList = new ArrayList<>();
                    for(SellerModel sellerModel : productModel.getProducts()){
                        sellerDataList.add(sellerConverter.convert(sellerModel));
                    }
                    productData.setSellers(sellerDataList);
               }
           }
    }

    public void setSellerConverter(
            Converter<SellerModel, SellerData> sellerConverter) {
        this.sellerConverter = sellerConverter;
    }
}
