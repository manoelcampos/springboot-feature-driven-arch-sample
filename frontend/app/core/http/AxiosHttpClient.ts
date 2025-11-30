import axios, { AxiosError, AxiosInstance, AxiosRequestConfig, AxiosResponse } from 'axios';

import { API_URL } from '@/app/shared/const/environment';

export class AxiosHttpClient {
    private readonly _baseUrl: string;
    private _axiosInstance: AxiosInstance;

    constructor() {
        this._axiosInstance = axios.create();
        this._baseUrl = API_URL;
        this._initializeAxios();
    }

    private _initializeAxios() {
        this._axiosInstance.defaults.baseURL = this._baseUrl;
        this._axiosInstance.defaults.timeout = 5000;
        this._axiosInstance.defaults.withCredentials = false;
        // TODO: Interceptors, etc
    }

    private async _handleRequest<T>(
        method: string,
        path: string,
        data?: any,
        options?: AxiosRequestConfig
    ): Promise<T> {
        try {
            const response: AxiosResponse<T> = await this._axiosInstance.request<T>({
                method,
                url: path,
                data,
                ...options,
            });

            return response.data;
        } catch (error) {
            throw this._handleError(error as AxiosError);
        }
    }

    private _handleError(error: AxiosError): Error {
        if (axios.isAxiosError(error) && error.response) {
            const responseData = error?.response?.data;
            const unknownError = 'Erro desconhecido';
            const hasResponseData =
                responseData !== null && typeof responseData === 'object' && 'message' in responseData;
            const errorMessage = hasResponseData ? responseData?.message || unknownError : unknownError;

            return new Error(errorMessage.toString());
        }

        if (error.request) {
            return new Error('Nenhuma resposta recebida');
        }

        return new Error(error.message || 'Requisição falhou');
    }

    public async post<T>(path: string, data?: any, options?: AxiosRequestConfig): Promise<T> {
        return this._handleRequest<T>('post', path, data, options);
    }

    public async get<T>(path: string, options?: AxiosRequestConfig): Promise<T> {
        return this._handleRequest<T>('get', path, undefined, options);
    }

    public async put<T>(path: string, data?: any, options?: AxiosRequestConfig): Promise<T> {
        return this._handleRequest<T>('put', path, data, options);
    }

    public async patch<T>(path: string, data?: any, options?: AxiosRequestConfig): Promise<T> {
        return this._handleRequest<T>('patch', path, data, options);
    }

    public async delete<T>(path: string, options?: AxiosRequestConfig): Promise<T> {
        return this._handleRequest<T>('delete', path, undefined, options);
    }
}
