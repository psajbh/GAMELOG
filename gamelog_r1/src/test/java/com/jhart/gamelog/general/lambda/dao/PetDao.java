package com.jhart.gamelog.general.lambda.dao;

import java.util.ArrayList;
import java.util.List;

import com.jhart.gamelog.general.lambda.model.Pet;

public class PetDao {
    
    public List<Pet> getPets(){
        List<Pet> pets = new ArrayList<>();
        Pet pet1 = new Pet();
        pet1.setId(1L);
        pet1.setName("Bella");
        pet1.setType("Dog");
        pets.add(pet1);

        Pet pet2 = new Pet();
        pet2.setId(2L);
        pet2.setName("Lexy");
        pet2.setType("Dog");
        pets.add(pet2);

        Pet pet3 = new Pet();
        pet3.setId(3L);
        pet3.setName("Jason");
        pet3.setType("Cat");
        pets.add(pet3);
            
        return pets;
        
        
    }

}
