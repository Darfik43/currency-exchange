package com.example.currencyexchanger.servlets;

import com.example.currencyexchanger.model.Currency;
import com.example.currencyexchanger.repositories.CurrencyRepository;
import com.example.currencyexchanger.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@MultipartConfig
@WebServlet(name = "index currencies servlet", value = "/currencies")
public class CurrenciesServlet extends HttpServlet {
        private CurrencyRepository currencyRepository;

        public void init(ServletConfig config) throws ServletException {
            currencyRepository = (CurrencyRepository) config.getServletContext().getAttribute("currencyRepository");
        }

        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            new ObjectMapper().writeValue(resp.getWriter(), currencyRepository.findAll());
        }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = Utils.getStringFromPartName(req, "code");
        String name = Utils.getStringFromPartName(req, "name");
        String sign = Utils.getStringFromPartName(req, "sign");

        if (Utils.isNotValidCurrenciesArgs(code, name, sign)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Не правильно введены данные. Пример: code = 'USD', name = 'US Dollar', sign = '$'");
            return;
        }

        if (currencyRepository.findByName(code).isPresent()) {
            resp.sendError(HttpServletResponse.SC_CONFLICT, "Валюта с таким кодом уже существует");
            return;
        }

        currencyRepository.save(new Currency(code, name, sign));

        doGet(req, resp);
    }
}
