import { z } from 'zod';

export const StateValidationSchema = z.object({
    name: z.string().min(1, { message: 'Name não pode ser vazia' }),
    abbreviation: z.string().min(1, { message: 'Abbreviation não pode ser vazia' }),
});
