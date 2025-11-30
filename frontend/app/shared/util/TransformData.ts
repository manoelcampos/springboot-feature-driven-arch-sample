import { formatDateToISO } from '@/app/shared/util/DateUtil';

export function formatValues(obj: Record<string, any>): Record<string, any> {
    const newObj: Record<string, any> = {};

    Object.keys(obj).forEach(key => {
        if (obj[key] instanceof Date) {
            newObj[key] = formatDateToISO(obj[key]); // Chama a função formatDate para formatar a data
        } else if (typeof obj[key] === 'object' && obj[key] !== null) {
            newObj[key] = formatValues(obj[key]); // Chama recursivamente para objetos aninhados
        } else {
            newObj[key] = obj[key];
        }
    });

    return newObj;
}

/**
 * Se não existir uma prop placeholder num conjunto de props de um componente, usa o valor da prop label como placeholder
 * @param defaultMsg Mensagem padrão a ser exibida no antes do texto da prop label, caso não exista a prop placeholder
 * @param rest props de um determinado componente, de onde será verifica se existe um placeholder
 */
export const useLabelFromPlaceholder = (defaultMsg: string, ...rest: any[]) => {
    const modifiedRest = { ...rest[0] };
    modifiedRest.placeholder = modifiedRest.placeholder || `${defaultMsg} ${modifiedRest.label}`;
    return modifiedRest;
};
