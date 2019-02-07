package org.ivanov.Controllers;

import org.ivanov.Domain.entity.Brand;
import org.ivanov.Domain.entity.Car;
import org.ivanov.Domain.entity.Payment;
import org.ivanov.Domain.entity.Person;
import org.ivanov.Domain.repositories.CarRepository;
import org.ivanov.Domain.repositories.ComingRepository;
import org.ivanov.Domain.repositories.PaymentRepository;
import org.ivanov.Domain.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/payment")
public class PaymentController {
    List<Payment> paymentPost;
    @Autowired
    ComingRepository comingRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    PersonRepository personRepository;
    @Autowired
    PaymentRepository paymentRepository;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("comings", comingRepository.findAll());
        model.addAttribute("persons", personRepository.findAll());
        model.addAttribute("cars", carRepository.findAll());
        model.addAttribute("payments", paymentPost);
        return "payment/index";
    }
    @PostMapping()
    public String index(Model model, @RequestParam Integer personId) {

        if (personId == 0) {
            paymentPost=paymentRepository.findAll();
            model.addAttribute("payments",paymentPost);
            return "redirect:/payment";
        }
        else  {
            List<Payment> payments = paymentRepository.findAll();
            paymentPost = new ArrayList<>();
            for (Payment payment : payments) {
                if (payment.getPerson().getId() == personId) {
                    paymentPost.add(payment);
                }
            }
            model.addAttribute("payments", paymentPost);
        }

        return "redirect:/payment";
    }

    @Secured("ROLE_Manager")
    @GetMapping("/add")
    public String addPayment(Model model) {
        model.addAttribute("persons", personRepository.findAll());
        model.addAttribute("payments", paymentRepository.findAll());
        return "payment/add";
    }


    @PostMapping("/add")
    public String addPayment(@ModelAttribute Payment payment, BindingResult result,
                         @RequestParam Integer personId, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            for (ObjectError err : result.getAllErrors()) {
                System.out.println(err);
            }
            redirectAttributes.addFlashAttribute("payment", "Binding error");
        } else {
            redirectAttributes.addFlashAttribute("payment", "Added successfully");
            Person person = personRepository.findById(personId).get();
//            payment.setStartDate(LocalDate.parse(payment.getStartDateDto(),
//                    DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            payment.setPerson(person);
           // Date parsedDate = new SimpleDateFormat("dd.MM.yyyy HH:mm").parse(value);
           // payment.setTime(new Timestamp(timestamp.getTime()));//вылетает Request processing failed; nested exception is java.lang.NullPointerException

            paymentRepository.save(payment);
        }
        return "redirect:/payment";
    }

    @GetMapping("/delete/{paymentId}")
    public String delete(@PathVariable Integer paymentId) {
        paymentRepository.deleteById(paymentId);
        return "redirect:/payment";
    }
}
