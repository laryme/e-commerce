package uz.spiders.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.spiders.ecommerce.repository.BrandRepository;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;
}
