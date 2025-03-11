package com.caa.kata.service.tax;

import com.caa.kata.utils.constant.Constants;
import com.caa.kata.utils.service.ValidatorService;
import com.caa.kata.exception.TechnicalException;
import com.caa.kata.model.product.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;


public class TaxStrategyFactory {

    private final List<TaxStrategy> strategies;
    private final ImportedTaxStrategy importedTaxStrategy;
    private static final Logger logger = LoggerFactory.getLogger(ImportedTaxStrategy.class);
    private final String LOG_PREFIX = "[ImportedTaxStrategy] : ";


    public TaxStrategyFactory(List<TaxStrategy> strategies, ImportedTaxStrategy importedTaxStrategy) {
        this.strategies = strategies;
        this.importedTaxStrategy = importedTaxStrategy;
    }

    /**
     * @param product
     * @return TaxStrategy depending on product Family
     * @throws IllegalArgumentException if one of method parameters is null
     * @throws TechnicalException       other cases
     */
    public TaxStrategy getTaxStrategy(Product product) throws IllegalArgumentException, TechnicalException {

        try {
            ValidatorService.validateList(strategies, Constants.TAX_STRATEGIES_LIST);
            ValidatorService.validateObject(product, Constants.PRODUCT);

            TaxStrategy taxStrategy = strategies.stream().filter(strategy -> strategy.appliesTo(product)).findFirst().orElseThrow(() -> new IllegalArgumentException("Cannot found tax strategy"));

            if (product.imported()) {
                importedTaxStrategy.setInitialTaxStrategy(taxStrategy);
                return importedTaxStrategy;
            }

            return taxStrategy;

        } catch (IllegalArgumentException e) {
            logger.warn(LOG_PREFIX + Constants.ERROR, e.getLocalizedMessage());
            throw e;
        } catch (Exception e) {
            logger.warn(LOG_PREFIX + Constants.ERROR, e.getLocalizedMessage());
            throw new TechnicalException(e.getMessage(), e.getCause());
        }
    }


}
