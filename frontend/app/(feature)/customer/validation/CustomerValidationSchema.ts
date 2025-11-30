import { z } from 'zod';

export const CustomerValidationSchema = z.object({
    name: z.string().min(1, { message: 'Name não pode ser vazia' }),
    cityId: z
        .string({ invalid_type_error: 'City precisa ser válido' })
        .min(1, { message: 'City é obrigatório' })
        .or(z.number())
        .nullable()
        .refine(val => val !== null && val !== undefined && val !== 0, { message: 'City precisa ser selecionado' }),
});
