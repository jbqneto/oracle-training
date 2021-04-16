package io.jbqneto.oracle.university.learning.service;

import java.util.List;
import java.util.stream.Collectors;

public class ProductService {

    public static void main(String[] args) {
        List<Double> list = List.of(1D,2D,3D,4D,5D,6D);

        var result = list.stream()
                .collect(Collectors.summarizingDouble((value -> (value * 10))));

        System.out.println(result.getAverage());

    }
}
