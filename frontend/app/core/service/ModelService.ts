import { CrudService } from '@/app/core/service/CrudService';
import { BaseModel } from '@/app/model/models.generated';

/**
 * Classe base para implementação de serviços que manipulam classes model,
 * mas não DTOs. Assim, os tipos T e D na superclasse são os mesmos.
 * @param T tipo da classe model que o serviço manipula
 */
export abstract class ModelService<T extends BaseModel> extends CrudService<T, T> {}
