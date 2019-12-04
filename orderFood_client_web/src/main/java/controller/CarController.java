package controller;

import com.github.pagehelper.PageInfo;
import domain.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import service.CarService;

import javax.servlet.http.HttpServletRequest;

//购物车
@Controller
@RequestMapping("/car")
public class CarController {
    @Autowired
    private CarService carService;

    @RequestMapping("/all")
    public ModelAndView all(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize) {
        PageInfo<Car> cars = carService.all(pageNum, pageSize);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModelMap().addAttribute("cars", cars);
        modelAndView.setViewName("car_list");
        return modelAndView;
    }

    @RequestMapping("/add")
    public String add(HttpServletRequest request,String productId) {
        carService.add(productId);
        String referer = request.getHeader("referer");
        return "redirect:"+referer;
    }

    @RequestMapping("/del")
    public String del(HttpServletRequest request,String productId) {
        carService.del(productId);
        String referer = request.getHeader("referer");
        return "redirect:"+referer;
    }
}
