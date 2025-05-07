package org.assignment.core.job;

import de.hybris.platform.cms2.model.site.CMSSiteModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.time.TimeService;
import de.hybris.platform.site.BaseSiteService;
import org.apache.commons.collections4.CollectionUtils;
import org.assignment.core.dao.OrderDao;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class OrderToSiteJobPerformable extends AbstractJobPerformable<CronJobModel> {

    private ModelService modelService;

    private OrderDao orderDao;

    private BaseSiteService baseSiteService;
    @Override
    public PerformResult perform(CronJobModel cronJobModel) {
        LocalDate localDate = LocalDate.now();
        final Date currentDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        final List<AbstractOrderModel> orders = orderDao.getOrders(currentDate);
        if(CollectionUtils.isNotEmpty(orders)) {
            final CMSSiteModel baseSiteModel = (CMSSiteModel) baseSiteService.getBaseSiteForUID("nagacc");
            baseSiteModel.setOrders(Set.copyOf(orders));
            modelService.save(baseSiteModel);
        }
        return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
    }

    @Override
    public boolean isAbortable(){
        return true;
    }

    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }

    public void setBaseSiteService(BaseSiteService baseSiteService) {
        this.baseSiteService = baseSiteService;
    }
}
