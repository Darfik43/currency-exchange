package com.example.currencyexchanger.servlets;

import com.example.currencyexchanger.model.Currency;
import com.example.currencyexchanger.repositories.CurrencyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.http.HttpServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "CurrencyServlet", value = "/currency/*")
public class CurrencyServlet extends HttpServlet {
    private CurrencyRepository currencyRepository;

    public void init(ServletConfig config) {
        currencyRepository = (CurrencyRepository) config.getServletContext().getAttribute("currencyRepository");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getPathInfo() == null || request.getPathInfo().equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Incorrect currency, /currency/USD");
            return;
        }

        String currencyCode = request.getPathInfo().replaceFirst("/", "").toUpperCase();

        Optional<Currency> currency = currencyRepository.findByName(currencyCode);

        if (!currency.isPresent()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Currency doesn't exist. /currency/USD");
            return;
        }
        response.getWriter().write(new ObjectMapper().writeValueAsString(currency.get()));
    }
}
