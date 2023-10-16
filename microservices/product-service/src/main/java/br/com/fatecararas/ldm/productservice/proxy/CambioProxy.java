package br.com.fatecararas.ldm.productservice.proxy;

import br.com.fatecararas.ldm.productservice.domain.Cambio;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cambio-service")
public interface CambioProxy {
    @GetMapping(value = "/cambio-service/{value}/BRL/USD")
    Cambio getCambio(@PathVariable("value") Double value);
}
