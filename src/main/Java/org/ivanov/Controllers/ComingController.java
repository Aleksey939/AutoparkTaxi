package org.ivanov.Controllers;

import lombok.var;
import org.ivanov.Domain.entity.Car;
import org.ivanov.Domain.entity.Coming;

import org.ivanov.Domain.entity.Person;
import org.ivanov.Domain.repositories.CarRepository;
import org.ivanov.Domain.repositories.ComingRepository;
import org.ivanov.Domain.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/coming")
public class ComingController {

    List<Coming> comingsPost;

    @Autowired
    ComingRepository comingRepository;

    @Autowired
    CarRepository carRepository;
    @Autowired
    PersonRepository personRepository;

    @GetMapping
    public String index(Model model) {

        model.addAttribute("comings", comingsPost);
        model.addAttribute("persons", personRepository.findAll());
        model.addAttribute("cars", carRepository.findAll());
        return "coming/index2";
    }

    @PostMapping()
    public String index(Model model, @RequestParam Integer personId, @RequestParam Integer carId,
                        @RequestParam String date1, @RequestParam  String date2) {

        LocalDate datestart =  LocalDate.parse(date1);
        LocalDate dateend =  LocalDate.parse(date2);

        List<Coming> comings = comingRepository.findAll();
        comingsPost = new ArrayList<>();

        if (personId == 0 && carId == 0 ) {
//            comingsPost = comingRepository.findAll();
            for (Coming coming : comings) {
                if (coming.getStartDate().isAfter(datestart) && coming.getStartDate().isBefore(dateend))
                    comingsPost.add(coming);
            }
            model.addAttribute("comings", comingsPost);
            return "redirect:/coming";



        } else if (personId != 0 && carId == 0) {
            for (Coming coming : comings) {
                if (coming.getCar().getPerson().getId() == personId &&coming.getStartDate().isAfter(datestart)&&coming.getStartDate().isBefore(dateend)) {
                    comingsPost.add(coming);
                }
            }
        } else if (personId == 0 && carId != 0) {
            for (Coming coming : comings) {
                if (coming.getCar().getId() == carId &&coming.getStartDate().isAfter(datestart)&&coming.getStartDate().isBefore(dateend)) {
                    comingsPost.add(coming);
                }
            }
        } else {
            for (Coming coming : comings) {
                if (coming.getCar().getPerson().getId() == personId && coming.getCar().getId() == carId&&coming.getStartDate().isAfter(datestart)&&coming.getStartDate().isBefore(dateend)) {
                    comingsPost.add(coming);
                }
            }

        }
        //model.addAttribute("cars", carRepository.findAll());
        model.addAttribute("comings", comingsPost);
        //model.addAttribute("persons", personRepository.findAll());
        return "redirect:/coming";
    }
//    @PostMapping()
//    public String index2(Model model,@RequestParam Integer carId) {
//        List<Coming> comings=comingRepository.findAll();
//
//        //comingsPost=new ArrayList<>();
//        for (Coming coming :comings) {
//            if(coming.getCar().getId()==carId) {
//                comingsPost.add(coming);
//            }
//        }
//        model.addAttribute("cars", carRepository.findAll());
//        model.addAttribute("comings",comingsPost);
//         model.addAttribute("persons", personRepository.findAll());
//        return "redirect:/coming";
//    }


    @GetMapping("/add")
    public String addComing(Model model) {
        model.addAttribute("cars", carRepository.findAll());
        return "coming/add";
    }

    @PostMapping("/add")
    public String addComing(@ModelAttribute Coming coming, BindingResult result, @RequestParam Integer carId,
                            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            for (ObjectError err : result.getAllErrors()) {
                System.out.println(err);
            }
            redirectAttributes.addFlashAttribute("coming", "Binding error");
        } else {

             coming.setStartDate(LocalDate.parse(coming.getStartDateDto(),
                  DateTimeFormatter.ofPattern("yyyy-MM-dd")));


            redirectAttributes.addFlashAttribute("coming", "Added successfully");
            Car car = carRepository.findById(carId).get();
            coming.setCar(car);




            coming.setFundMaintenance(Round2(coming.getMileage() * 0.13));
            coming.setFundRepairs(Round2(coming.getMileage() * 0.35));
            coming.setCommissionPartner(Round2(coming.getIncome() * 0.07+coming.getBonus()*0.07));
            coming.setDriverSalary(Round2(coming.getIncome() * 0.35));
            coming.setConsumptionOneKm(Round2(coming.getFuelCosts() / (double) coming.getMileage()));
            coming.setProfitOneKm(Round2(coming.getIncome() / (double) coming.getMileage()));
            var allCommingsbyCar = comingRepository.findAllByCar(car);

            if(allCommingsbyCar.size()==0) {
                coming.setCapitalizationMaintenanceStart(0);
                coming.setCapitalizationRepairsStart(0);
            }
            else {
                coming.setCapitalizationMaintenanceStart(allCommingsbyCar.get(allCommingsbyCar.size() - 1).getCapitalizationMaintenanceEnd());
                coming.setCapitalizationRepairsStart(allCommingsbyCar.get(allCommingsbyCar.size() - 1).getCapitalizationRepairsEnd());
            }
            coming.setCapitalizationMaintenanceEnd(Round2 (coming.getCapitalizationMaintenanceStart() + coming.getFundMaintenance() - coming.getCostsOfMaintenance()));
            coming.setCapitalizationRepairsEnd(Round2(coming.getCapitalizationRepairsStart() + coming.getFundRepairs() - coming.getCostsOfRepairs()));



            coming.setProfit(Round2(coming.getIncome()
                    + coming.getBonus()
                    - coming.getCommissionPartner()
                    - coming.getDriverSalary()
                    - coming.getFuelCosts()
                    - coming.getFundMaintenance()
                    - coming.getFundRepairs()
                    - coming.getCurrentExpenses()
                    - coming.getCarWash()));

            coming.setCommissionControl(Round2(coming.getProfit() * 0.25));
            coming.setInvestorIncome(Round2(coming.getProfit() - coming.getCommissionControl()));
            coming.setDepreciation((coming.getCar().getPriceStart()*28-coming.getCar().getPriceEnd()*28)/104);
            coming.setNetinvestorIncome(coming.getInvestorIncome()-coming.getDepreciation());
           // coming.setBonus(coming.getBonus()*0.93*0.75);
            comingRepository.save(coming);
        }
        return "redirect:/coming";
    }

    @GetMapping("/delete/{comingId}")
    public String delete(@PathVariable Integer comingId) {
        comingRepository.deleteById(comingId);
        return "redirect:/coming";
    }

    @GetMapping("/edit/{comingId}")
    public String edit(Model model, @PathVariable Integer comingId ) {
        model.addAttribute("coming", comingRepository.findById(comingId).get());
        model.addAttribute("cars", carRepository.findAll());

        return "coming/edit";
    }

    @PostMapping("/edit/{comingId}")
    public String edit(Model model, @ModelAttribute Coming coming, @PathVariable Integer comingId, @RequestParam Integer carId) {

        Car car = carRepository.findById(carId).get();
        //List<Coming> allCommingsbyCar = comingRepository.findAllByCar(car);
        coming.setStartDate(LocalDate.parse(coming.getStartDateDto(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        coming.setCar(car);

        coming.setFundMaintenance(Round2(coming.getMileage() * 0.13));
        coming.setFundRepairs(Round2(coming.getMileage() * 0.35));
        coming.setCommissionPartner(Round2(coming.getIncome() * 0.07+coming.getBonus()*0.07));
        coming.setDriverSalary(Round2(coming.getIncome() * 0.35));
        coming.setConsumptionOneKm(Round2(coming.getFuelCosts() / (double) coming.getMileage()));
        coming.setProfitOneKm(Round2(coming.getIncome() / (double) coming.getMileage()));
        //coming.setCapitalizationMaintenanceStart(allCommingsbyCar.get(allCommingsbyCar.size() - 1).getCapitalizationMaintenanceEnd());
        coming.setCapitalizationMaintenanceEnd (Round2(coming.getCapitalizationMaintenanceStart() + coming.getFundMaintenance() - coming.getCostsOfMaintenance()));
        //coming.setCapitalizationRepairsStart(allCommingsbyCar.get(allCommingsbyCar.size() - 1).getCapitalizationRepairsEnd());
        coming.setCapitalizationRepairsEnd(Round2(coming.getCapitalizationRepairsStart() + coming.getFundRepairs() - coming.getCostsOfRepairs()));


        coming.setProfit(Round2(coming.getIncome()
                + coming.getBonus()
                - coming.getCommissionPartner()
                - coming.getDriverSalary()
                - coming.getFuelCosts()
                - coming.getFundMaintenance()
                - coming.getFundRepairs()
                - coming.getCurrentExpenses()
                - coming.getCarWash()));

        coming.setCommissionControl(Round2(coming.getProfit() * 0.25));
        coming.setInvestorIncome(Round2(coming.getProfit() - coming.getCommissionControl()));
        coming.setDepreciation((coming.getCar().getPriceStart()*28-coming.getCar().getPriceEnd()*28)/104);
        coming.setNetinvestorIncome(coming.getInvestorIncome()-coming.getDepreciation());
        comingRepository.save(coming);
        return "redirect:/coming";
    }

    public static double Round2(double value) {
        int iValue = (int) (value * 100);
        double dValue = value * 100;
        if (dValue - iValue >= 0.5) {
            iValue += 1;
        }
        dValue = (double) iValue;
        return dValue / 100;
    }
}
// coming.setFundMaintenance(Math.round(coming.getMileage() * 0.13 * 100) / 100);
//         coming.setFundRepairs(Math.round(coming.getMileage() * 0.35 * 100) / 100);
//         coming.setCommissionPartner(Math.round(coming.getIncome() * 0.07 * 100) / 100);
//         coming.setDriverSalary(Math.round(coming.getIncome() * 0.35 * 100) / 100);
//         coming.setConsumptionOneKm((Math.round((coming.getFuelCosts() / coming.getMileage()) * 100)) / 100);
//         coming.setProfitOneKm((Math.round((coming.getIncome() / coming.getMileage()) * 100)) / 100);


//  for (Coming coming :comings
//          ) {
//          if(coming.getCar().getId()==carId) {
//          Car car = carRepository.findById(carId).get();
//          coming.setCar(car);
//          comings2.add(coming);
//          }
//
//          }