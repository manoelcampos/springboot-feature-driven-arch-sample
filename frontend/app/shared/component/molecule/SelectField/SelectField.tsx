import { Select, SelectProps } from '@mantine/core';

import { useLabelFromPlaceholder } from '@/app/shared/util';

interface SelectFieldProps<T> extends Omit<SelectProps, 'data'> {
    /**
     * Vetor com os dados a serem usados pelo componente
     */
    data: T[];

    /**
     * Função para obter a descrição a ser exibida para cada item do select.
     * A função recebe o item atual do vetor {@link #data} e deve retornar algum atributo de tal item
     * que identifique o item unicamente (como o id).
     */
    getId: (item: T) => any;

    /**
     * Função para obter o valor a ser usado como value do campo select.
     * A função recebe o item atual do vetor {@link #data} e deve retornar algum atributo de tal item
     * para ser exibido.
     */
    getDescription: (item: T) => string;
    nothingFoundMessage?: string;
}

export function SelectField<T>({ data, getId, getDescription, nothingFoundMessage, ...rest }: SelectFieldProps<T>) {
    const transformedData = data.map(item => ({
        value: String(getId(item)),
        label: getDescription(item),
    }));

    const modifiedRest = useLabelFromPlaceholder('Selecione o(a)', { ...rest });
    const defaultNothingFoundMessage = `${modifiedRest.label} não encontrado(a)`;
    return (
        <Select
            data={transformedData}
            nothingFoundMessage={nothingFoundMessage || defaultNothingFoundMessage}
            {...modifiedRest}
        />
    );
}
