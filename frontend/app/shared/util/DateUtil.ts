import dayjs from 'dayjs';

export const BR_DATE_FORMAT = 'DD/MM/YYYY';
export const ISO_DATE_FORMAT = 'YYYY-MM-DD';

/**
 * Valor mínimo para uma data ser considerada válida ao executar o método coerce do zod.
 * Esta é uma data posterior a data criada quando new Date(null) é chamado,
 * que cria uma data 31/12/1969.
 * Isto ocorre quando campos data não são preenchidos e recebem null.
 * O comando coerse do zod irá criar então uma data com este valor acima.
 */
export const MIN_VALID_DATE = new Date('01/01/1970');

/**
 * Formata a data para o padrão brasileiro.
 * @param date data a ser formatada
 * @see #BR_DATE_FORMAT
 */
export function formatDate(date: Date | undefined | null) {
    return date ? dayjs(date).format(BR_DATE_FORMAT) : null;
}

/**
 * Formata a data para o padrão ISO 8601.
 * @param date data a ser formatada
 * @see #ISO_DATE_FORMAT
 */
export function formatDateToISO(date: Date | undefined | null) {
    return date ? dayjs(date).format(ISO_DATE_FORMAT) : null;
}

/**
 * Converte uma String contendo uma data, no formato brasileiro, para um objeto Date
 * @param dateStr
 * @see #BR_DATE_FORMAT
 */
export function parseDate(dateStr: any) {
    return dateStr ? dayjs(dateStr, BR_DATE_FORMAT).toDate() : null;
}

export function getCurrentYearMonth() {
    const currentDate = new Date();
    const year = currentDate.getFullYear();
    const month = (currentDate.getMonth() + 1).toString().padStart(2, '0');
    return `${year}-${month}`;
}

/**
 * Verifica se a data de Término é maior que a data de Início
 * @param dataInicio
 * @param dataTermino
 * @return boolean
 */
export function validDateInterval(dataInicio: Date, dataTermino: Date | undefined) {
    return !dataTermino || dataTermino <= MIN_VALID_DATE || dataTermino > dataInicio;
}
