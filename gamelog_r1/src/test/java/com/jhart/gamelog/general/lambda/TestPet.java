package com.jhart.gamelog.general.lambda;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.jhart.gamelog.general.lambda.dao.PetDao;
import com.jhart.gamelog.general.lambda.model.Pet;

public class TestPet {
    
    private List<Pet> pets;
    
    @Before
    public void setup() {
        PetDao petDao = new PetDao();
        pets = petDao.getPets();
        
    }

    @Test
    public void testPet() throws Exception{
        List<Pet> dogs = pets.stream().filter(x -> x.getType().equals("Dog")).collect(Collectors.toList());
        List<Pet> cats = pets.stream().filter(x -> x.getType().equals("Cat")).collect(Collectors.toList());
        List<String> names = pets.stream().map(Pet :: getName).collect(Collectors.toList());
        
        Assert.assertEquals(dogs.size(),2);
        Assert.assertEquals(cats.size(),1);
        Assert.assertEquals(names.size(),3);
    }


}

