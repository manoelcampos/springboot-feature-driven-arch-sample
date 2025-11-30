import { CrudService } from '@/app/core/service/CrudService';
import { City, CityDTO } from '@/app/model/models.generated';

export class CityService extends CrudService<City, CityDTO> {
    protected getResourceUrl(): string {
        return '/city';
    }
}
