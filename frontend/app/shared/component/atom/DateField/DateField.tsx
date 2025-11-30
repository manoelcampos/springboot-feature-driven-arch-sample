import { DateInput, DateInputProps } from '@mantine/dates';

import { BR_DATE_FORMAT, parseDate } from '@/app/shared/util/DateUtil';

export interface DateProps extends DateInputProps {
    label: string;
}

export function DateField({ label, value, ...rest }: DateProps) {
    return (
        <DateInput
            label={label}
            placeholder={BR_DATE_FORMAT}
            valueFormat={BR_DATE_FORMAT}
            value={parseDate(value)}
            clearable
            {...rest}
        />
    );
}
