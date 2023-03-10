package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.entity.Services;
import com.tfs.demo.tfs_crud_demo.service.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ServiceRestController {
    private ServicesService servicesService;

    @Autowired
    public ServiceRestController(ServicesService theServicesService){
        servicesService = theServicesService;
    }

    @GetMapping("/services")
    public List<Services> getAllServices(){
        return servicesService.getAllServices();
    }

    @GetMapping("/services/{serviceId}")
    public Services getById(@PathVariable int serviceId){
        return servicesService.getServiceById(serviceId);
    }

    @GetMapping("/services/byEvent/{eventId}")
    public List<Services> getAllByEventId(@PathVariable int eventId){
        return servicesService.getAllByEventId(eventId);
    }

    @PostMapping("/services")
    public Services addNewService(@RequestBody Services services){
        servicesService.save(services);
        return services;
    }

    @PutMapping("/services")
    public Services updateService(@RequestBody Services services){
        Services existService = servicesService.getServiceById(services.getId());

        if(services.getServiceName()==null){
            services.setServiceName(existService.getServiceName());
        }
        if(services.getServiceDescription()==null){
            services.setServiceDescription(existService.getServiceDescription());
        }
        if(services.getServiceImage()==null){
            services.setServiceImage(existService.getServiceImage());
        }
        if(services.getStatus()==null){
            services.setStatus(existService.getStatus());
        }

        servicesService.save(services);

        return services;
    }

    @DeleteMapping("/service/{serviceId}")
    public String disableService(@PathVariable int serviceId){
        servicesService.disableService(serviceId);
        return "disabled!";
    }

}
