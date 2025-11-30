import { CrudService } from '@/app/core/service/CrudService';
import { Customer, CustomerDTO } from '@/app/model/models.generated';

export class CustomerService extends CrudService<Customer, CustomerDTO> {
    protected getResourceUrl(): string {
        return '/customer';
    }
}
