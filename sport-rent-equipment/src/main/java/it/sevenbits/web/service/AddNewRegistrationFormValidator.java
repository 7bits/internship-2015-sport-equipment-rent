package it.sevenbits.web.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by awemath on 7/17/15.
 */
    @Service
    public class AddNewRegistrationFormValidator {
        @Autowired
        private CommonFieldValidator validator;

        private static final Logger LOG = Logger.getLogger(AddNewGoodsFormValidator.class);

    }
