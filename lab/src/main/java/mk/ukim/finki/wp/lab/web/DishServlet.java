package mk.ukim.finki.wp.lab.web;

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

@WebServlet(name = "DishServlet", urlPatterns = "/servlet/dish")
public class DishServlet extends HttpServlet {

    private final SpringTemplateEngine springTemplateEngine;
    private final ChefService chefService;
    private final DishService dishService;

    public DishServlet(SpringTemplateEngine springTemplateEngine, ChefService chefService, DishService dishService) {
        this.springTemplateEngine = springTemplateEngine;
        this.chefService = chefService;
        this.dishService = dishService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException{
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);


        Long chefId = Long.parseLong(req.getParameter("chefId"));
        WebContext context = new WebContext(webExchange);

        context.setVariable("chef", chefService.findById(chefId));
        context.setVariable("dishes", dishService.listDishes());

        springTemplateEngine.process("dishesList.html", context, resp.getWriter());
    }
}
