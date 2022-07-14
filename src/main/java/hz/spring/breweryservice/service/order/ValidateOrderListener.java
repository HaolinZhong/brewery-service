package hz.spring.breweryservice.service.order;

import hz.spring.breweryservice.config.JmsConfig;
import hz.spring.common.event.ValidateBeerOrderRequest;
import hz.spring.common.event.ValidateBeerOrderResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ValidateOrderListener {

    private final BeerOrderValidator validator;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_QUEUE)
    public void listen(ValidateBeerOrderRequest request) {
        Boolean isValid = validator.validateOrder(request.getBeerOrderDTO());
        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE,
                ValidateBeerOrderResult.builder()
                        .orderId(request.getBeerOrderDTO().getId())
                        .isValid(isValid)
                        .build()
                );
    }
}

