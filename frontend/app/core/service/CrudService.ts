import { BaseService } from '@/app/core/service/BaseService';
import { BaseModel, DTORecord } from '@/app/model/models.generated';

/**
 * Uma clase base para implementação de serviços que manipulam classes model
 * (e opcionalmente DTOs). Tal classe fornece todas as operações CRUD
 * (algumas herdadas da superclasse {@link BaseService}).
 * Se um tipo DTO for passado em D, os métodos {@link #create} e {@link #update}
 * vão receber um DTO, não um model.
 * @param T tipo da classe model que o serviço manipula
 * @param D tipo de DTO referente à classe model que o serviço manipula.
 *          Se não for usado DTO para o serviço, o tipo pode ser o mesmo de T.
 */
export abstract class CrudService<T extends BaseModel, D extends DTORecord<BaseModel>> extends BaseService<T> {
    async getById(id: string): Promise<T> {
        return this.get<T>(this.validate(id));
    }

    async getDtoById(id: number | string): Promise<D> {
        return this.get<D>(`/dto/${this.validate(id)}`);
    }

    async save(id: number | string, data: D): Promise<T> {
        return id ? this.update(id, data) : this.create(data);
    }

    async create(data: D): Promise<T> {
        return this.httpClient.post<T>(this.validatedResourceUrl, data);
    }

    async update(id: number | string, data: D): Promise<T> {
        return this.httpClient.put<T>(this.joinPath(this.validate(id)), data);
    }
}
