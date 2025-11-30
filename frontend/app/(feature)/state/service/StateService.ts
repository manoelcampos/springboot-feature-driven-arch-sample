import { CrudService } from '@/app/core/service/CrudService';
import { State, StateDTO } from '@/app/model/models.generated';

export class StateService extends CrudService<State, StateDTO> {
    protected getResourceUrl(): string {
        return '/state';
    }
}
