import { posix } from 'path';

import { AxiosHttpClient } from '@/app/core/http/AxiosHttpClient';
import { BaseModel } from '@/app/model/models.generated';

/**
 * Classe base para implementação de serviços que manipulam classes model, mas não DTOs.
 * Ela fornece apenas as operações que não precisam de um DTO.
 * Para um service com todas as operações CRUD, veja {@link CrudService}.
 */
export abstract class BaseService<T extends BaseModel> {
    protected readonly httpClient: AxiosHttpClient;
    protected readonly validatedResourceUrl: string;

    constructor() {
        const url = this.getResourceUrl();
        this.validatedResourceUrl = url.startsWith('/') ? url : `/${url}`;
        this.httpClient = new AxiosHttpClient();
    }

    /**
     * Envia uma requisição get para o endpoint especificado em endPath.
     * @param endPath o caminho do endpoint a ser adicionado ao final de {@link #validatedResourceUrl}.
     * @protected
     */
    protected async get<R>(...endPath: string[]): Promise<R> {
        const path = this.joinPath(...endPath);
        return this.httpClient.get<R>(path);
    }

    async getAll(): Promise<T[]> {
        return this.get<T[]>();
    }

    async delete(id: number | string): Promise<void> {
        await this.httpClient.delete<void>(this.joinPath(this.validate(id)));
    }

    /**
     * Retorna o endpoint do recurso.
     * O método não deve ser chamado fora do construtor.
     * O construtor valida o retorno do método e armazena em {@link #validatedResourceUrl}.
     * O valor poderia ser passado como parâmetro para o construtor,
     * mas o TypeScript não obriga a inclusão de tal construtor (ou um padrão sem parâmetros).
     * @protected
     */
    protected abstract getResourceUrl(): string;

    /**
     * Converte um id para string, retornando um valor padrão se não for informado.
     * Aceita number ou string como parâmetro pois dependendo de onde o valor
     * é obtido, ele pode vir em um destes dois tipos.
     * @param id id a ser convetido
     * @private
     */
    protected validate(id: number | string): string {
        // Como o valor pode vir como string, tenta converter para number par confirmar que é um número válido
        return ((id as number) || 0).toString();
    }

    protected joinPath(...endPath: string[]): string {
        return posix.join(this.validatedResourceUrl, ...endPath);
    }
}
