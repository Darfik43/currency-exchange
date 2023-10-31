package com.example.currencyexchanger.listeners;

import com.example.currencyexchanger.repositories.CurrencyRepository;
import com.example.currencyexchanger.repositories.ExchangeRatesRepository;

import javax.servlet.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Properties;

@WebListener
public class ContextListener implements ServletContextListener {

    public ContextListener() {

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
