package guru.springframework.sfgpetclinic;

import guru.springframework.sfgpetclinic.services.map.OwnerServiceMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SfgPetClinicApplication {

    public static void main(String[] args) {
        SpringApplication.run(SfgPetClinicApplication.class, args);

        OwnerServiceMap sv = new OwnerServiceMap();
        sv.findAll();

    }



}
