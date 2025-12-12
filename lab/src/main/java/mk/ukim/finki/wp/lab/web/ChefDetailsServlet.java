package mk.ukim.finki.wp.lab.web;

import mk.ukim.finki.wp.lab.model.Chef;
import mk.ukim.finki.wp.lab.model.Dish;
import mk.ukim.finki.wp.lab.service.ChefService;
import mk.ukim.finki.wp.lab.service.DishService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(name = "ChefDetailsServlet", urlPatterns = "/servlet/chefDetails")
public class ChefDetailsServlet extends HttpServlet {

    private final SpringTemplateEngine springTemplateEngine;
    private final ChefService chefService;
    private final DishService dishService;

    public ChefDetailsServlet(SpringTemplateEngine springTemplateEngine, ChefService chefService, DishService dishService) {
        this.springTemplateEngine = springTemplateEngine;
        this.chefService = chefService;
        this.dishService = dishService;
    }

    // Handle GET request: Show chef details
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);

        WebContext context = new WebContext(webExchange);

        Long chefId = Long.parseLong(req.getParameter("chefId"));

        // Get the chef details
        Chef chef = chefService.findById(chefId);
        context.setVariable("chef", chef); // Set chef details for the view

        // Render the template
        springTemplateEngine.process("chefDetails.html", context, resp.getWriter());
    }

    // Handle POST request: Add a dish to the chef
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long chefId = Long.parseLong(req.getParameter("chefId"));
        String dishId = req.getParameter("dishId");

        Chef chef = chefService.findById(chefId);
        Dish dish = dishService.findByDishId(dishId);

        if (chef != null && dish != null) {
            chefService.addDishToChef(chefId, dishId); // Add dish to chef
        }

        // After adding the dish, redirect back to the same page to show the updated chef details
        resp.sendRedirect("/servlet/chefDetails?chefId=" + chefId);
    }
}

