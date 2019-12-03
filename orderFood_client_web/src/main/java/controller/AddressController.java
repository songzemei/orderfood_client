package controller;

import com.github.pagehelper.PageInfo;
import domain.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import service.AddressService;

import java.util.UUID;

@Controller
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    //查找所有地址
    @RequestMapping("all")
    public ModelAndView all(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize) {
        PageInfo<Address> addresses = addressService.all(pageNum, pageSize);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModelMap().addAttribute("addresses", addresses);
        modelAndView.setViewName("address_list");
        return modelAndView;
    }

    //修改地址
    @RequestMapping("/update")
    public String update(Address address) {
        addressService.update(address);
        return "redirect:/address/all";
    }

    //添加地址
    @RequestMapping("/add")
    public String add(Address address) {
        addressService.add(address);
        return "redirect:/address/all";
    }

    //通过id查找地址
    @RequestMapping("/findById")
    public ModelAndView findById(String id) {
        Address address = addressService.findById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModelMap().addAttribute("address",address);
        modelAndView.setViewName("address_update");
        return modelAndView;
    }
}
