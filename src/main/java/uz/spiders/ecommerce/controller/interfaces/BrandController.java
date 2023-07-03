package uz.spiders.ecommerce.controller.interfaces;

import org.springframework.web.bind.annotation.RequestMapping;
import uz.spiders.ecommerce.utils.Constants;

@RequestMapping(BrandController.BASE_PATH)
public interface BrandController {
    String BASE_PATH = Constants.BASE_PATH + "/brand";
}
