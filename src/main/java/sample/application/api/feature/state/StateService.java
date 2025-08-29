package sample.application.api.feature.state;

import org.springframework.stereotype.Service;
import sample.application.api.shared.service.AbstractCrudService;

@Service
public class StateService extends AbstractCrudService<State, StateRepository> {
    public StateService(final StateRepository repository) {
        super(repository);
    }

    @Override
    public State save(final State state) {
        if(state.name.equalsIgnoreCase(state.abbreviation))
            throw new IllegalStateException("The country state description cannot be equal to its abbreviation");

        return super.save(state);
    }
}
