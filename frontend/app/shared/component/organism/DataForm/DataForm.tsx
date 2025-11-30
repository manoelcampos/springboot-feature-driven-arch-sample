import React, { FormEvent, useState } from 'react';

import { Box, Button, Grid, Group, Stack, Text } from '@mantine/core';
import { UseFormReturnType } from '@mantine/form/lib/types';
import { modals } from '@mantine/modals';
import { showNotification } from '@mantine/notifications';
import { IconArrowBack, IconDeviceFloppy } from '@tabler/icons-react';
import Link from 'next/link';
import { useRouter } from 'next/navigation';

import { BaseModel, DTORecord } from '@/app/model/models.generated';
import { PaperCard } from '@/app/shared/component';
import { formatValues } from '@/app/shared/util/TransformData';

/**
 * Props para o botão de submit.
 */
interface SubmitProps extends Record<string, any> {
    title: string;
    successMsg?: string;
    errorMsg: string;
    disabled?: boolean;
    confirmationMessage?: string;
}

interface FormProps<T extends BaseModel, D extends DTORecord<T>> {
    title: string;
    backRoute?: string;
    fetchMethod: ((data: D) => Promise<T>) | ((data: D) => Promise<any>);
    form: UseFormReturnType<D, (value: D) => D>;
    submit: SubmitProps;
}

function DataForm<T extends BaseModel, D extends DTORecord<T>>(props: React.PropsWithChildren<FormProps<T, D>>) {
    const {
        title,
        submit: { title: submitBtnTitle, successMsg, errorMsg, disabled: submitDisabled, confirmationMessage, ...rest },
        backRoute,
        fetchMethod,
        form,
        children,
    } = props;

    const [loading, setLoading] = useState(false);
    const router = useRouter();

    const handleSubmit = (data: D, evt: FormEvent<HTMLFormElement> | undefined) => {
        setLoading(true);
        const effectiveData = formatValues(data) as D;
        fetchMethod(effectiveData)
            .then(() => {
                successMsg &&
                    showNotification({
                        withBorder: true,
                        color: 'green',
                        title: 'Sucesso',
                        message: successMsg,
                    });

                backRoute && router.push(backRoute);
            })
            .catch((error: any) => {
                showNotification({
                    withBorder: true,
                    color: 'red',
                    title: 'Erro',
                    message: `${errorMsg}: ${error}`,
                });
            })
            .finally(() => setLoading(false));

        !backRoute && evt?.preventDefault();
    };

    const openModal = () =>
        modals.openConfirmModal({
            title: 'Confirme sua ação',
            centered: true,
            children: <Text size="sm">{confirmationMessage}</Text>,
            labels: { confirm: 'Confirmar', cancel: 'Cancelar' },
            onConfirm: () => handleSubmit(form.values, undefined),
        });

    return (
        <>
            <Grid>
                <Grid.Col span={{ base: 12, lg: 12 }}>
                    <PaperCard>
                        <Box component="form" onSubmit={form.onSubmit(confirmationMessage ? openModal : handleSubmit)}>
                            <Stack>
                                <Text size="lg" fw={600}>
                                    {title}
                                </Text>

                                {children}

                                <Group>
                                    {backRoute && (
                                        <Button
                                            leftSection={<IconArrowBack size={16} />}
                                            component={Link}
                                            href={backRoute}
                                            variant="default"
                                        >
                                            Voltar
                                        </Button>
                                    )}
                                    <Button
                                        type="submit"
                                        leftSection={<IconDeviceFloppy size={16} />}
                                        loading={loading}
                                        disabled={submitDisabled}
                                        {...rest}
                                    >
                                        {submitBtnTitle}
                                    </Button>
                                </Group>
                            </Stack>
                        </Box>
                    </PaperCard>
                </Grid.Col>
            </Grid>
        </>
    );
}

export default DataForm;
