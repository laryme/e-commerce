package uz.spiders.ecommerce.payload;

import java.util.List;

public record DetailCategory(
        String name,
        List<Detail> detailList
) {}
