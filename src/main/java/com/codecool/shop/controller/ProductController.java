package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.config.TemplateEngineUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ProductDao productDataStore = ProductDaoJDBC.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        ShoppingCartDao shoppingCarts = ShoppingCartDaoMem.getInstance();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        int userId = 1; // TODO : USER SYSTEM!
        context.setVariable("sum_of_items", shoppingCarts.getSumOfItems().get(userId));
        context.setVariable("recipient", "World");
        context.setVariable("products", productDataStore.getAll());
        context.setVariable("category", productCategoryDataStore.find(1));
        context.setVariable("categories", productCategoryDataStore.getAll());
        context.setVariable("suppliers", supplierDataStore.getAll());
        context.setVariable("userID", userId);
        engine.process("product/index.html", context, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ShoppingCartDao shoppingCartDao = ShoppingCartDaoMem.getInstance();

        String stringItemId = req.getParameter("changeCart");
        int userId = 1; // TODO: USER SYSTEM

        Map<Integer, Integer> userCart = shoppingCartDao.getAll().get(userId);

        if (stringItemId != null) {
            ShoppingCartController.updateCart(userId, shoppingCartDao, stringItemId, userCart);
        }

        resp.sendRedirect("/");



    }

}
