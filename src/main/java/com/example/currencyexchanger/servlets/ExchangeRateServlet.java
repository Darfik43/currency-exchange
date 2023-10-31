package com.example.currencyexchanger.servlets;

import com.example.currencyexchanger.model.ExchangeRate;
import com.example.currencyexchanger.repositories.ExchangeRatesRepository;
import com.example.currencyexchanger.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;


@MultipartConfig
@WebServlet(name = "exchangeRate", value = "/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {
    private ExchangeRatesRepository exchangeRatesRepository;

    public void init(ServletConfig config) throws ServletException {
        exchangeRatesRepository = (ExchangeRatesRepository) config.getServletContext().getContext("exchangeRatesRepository");
    }

    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getMethod().equals("PATCH")) {
            doPatch(req, resp);
        } else {
            super.service(req, resp);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getPathInfo() == null || request.getPathInfo().equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "The currency codes don't exist Example .../exchangeRate/USDRUB");
            return;
        }

        String currenciesCodes = request.getPathInfo().replaceFirst("/", "").toUpperCase();

        if (currenciesCodes.length() != 6) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Incorrect currency pair. Example .../exchangeRate/USDRUB");
            return;
        }

        Optional<ExchangeRate> exchangeRate = exchangeRatesRepository.findByCodes(
                currenciesCodes.substring(0, 3), currenciesCodes.substring(3, 6));

        if (!exchangeRate.isPresent()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Exchange rate's not found");
            return;
        }

        response.getWriter().write(new ObjectMapper().writeValueAsString(exchangeRate.get()));
    }

    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rate = Utils.getStringFromPartName(request, "rate");

        if (rate == null || rate.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Exchange rate doesn't exist");
            return;
        }

        if (!Utils.isStringDouble(rate)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Enter a value. Example 1.05");
            return;
        }

        String currenciesCodes = request.getPathInfo().replaceFirst("/", "").toUpperCase();

        Optional<ExchangeRate> exchangeRate = exchangeRatesRepository.findByCodes(
                currenciesCodes.substring(0, 3), currenciesCodes.substring(3, 6));

        if (exchangeRate.isPresent()) {
            exchangeRate.get().setRate(BigDecimal.valueOf(Double.parseDouble(rate)));
            exchangeRatesRepository.update(exchangeRate.get());
        }

        doGet(request, response);
    }
}
