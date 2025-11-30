'use client';

import { FormType } from '@/app/shared/component/template/FormPage/FormPage';

export function parseActionsPageParams(actions: string[] | string | undefined) {
    const slugItems = Array.isArray(actions) ? actions : [actions ?? ''];
    const type = slugItems.length > 1 ? slugItems[1] : slugItems[0];
    const id = slugItems.length > 1 ? slugItems[0] : undefined;

    if (id && type === 'editar') {
        return { action: FormType.Edit, id };
    }
    if (id && type === 'visualizar') {
        return { action: FormType.View, id };
    }
    if (type === 'criar') {
        return { action: FormType.Create };
    }

    throw new Error('Invalid params');
}

/**
 * Promise a ser usada em rotas de páginas que possuem um ID como parâmetro.
 * (se tornou Promise a partir do NextJS 15).
 */
export type IdParamsPromise = Promise<{ id: string }>;

/**
 * Promise a ser usada em rotas dinâmicas que podem receber vários parâmetros.
 * Estas rodas são definidas com `[...actions]` e podem receber um ou mais parâmetros.
 */
export type ActionParamsPromise = Promise<{ actions: string[] | string }>;
