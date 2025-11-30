import { z } from 'zod';

export const CityValidationSchema = z.object({
    name: z.string().min(1, { message: 'Name não pode ser vazia' }),
    stateId: z
        .string({ invalid_type_error: 'State precisa ser válido' })
        .min(1, { message: 'State é obrigatório' })
        .or(z.number())
        .nullable()
        .refine(val => val !== null && val !== undefined && val !== 0, { message: 'Setor precisa ser selecionado' }),
});
