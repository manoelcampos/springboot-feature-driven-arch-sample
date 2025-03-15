package sample.application.api.feature.state;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sample.application.api.shared.controller.AbstractController;

@RestController
@RequestMapping("/estado")
public class StateController extends AbstractController<State, StateRepository, StateService> {
    public StateController(final StateService service) {
        super(service);
    }
}
