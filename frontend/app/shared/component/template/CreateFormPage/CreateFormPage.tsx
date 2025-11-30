import React from 'react';

import { Stack } from '@mantine/core';
import { UseFormReturnType } from '@mantine/form/lib/types';

import { BaseModel, DTORecord } from '@/app/model/models.generated';
import { PageHeader } from '@/app/shared/component';
import { BreadcrumbItem } from '@/app/shared/component/molecule/Breadcrumb/Breadcrumb';
import DataForm from '@/app/shared/component/organism/DataForm/DataForm';
import { APP_NAME } from '@/app/shared/const/environment';

interface CreateFormPageProps<T extends BaseModel, D extends DTORecord<T>> {
    title: string;
    breadcrumbItems: BreadcrumbItem[];
    fetchMethod: (data: D) => Promise<T>;
    form: UseFormReturnType<D, (value: D) => D>;
    backRoute: string;
    isSubmitDisabled?: boolean;
    confirmationMessage?: string;
}

function CreateFormPage<T extends BaseModel, D extends DTORecord<T>>(
    props: React.PropsWithChildren<CreateFormPageProps<T, D>>
) {
    const { title, breadcrumbItems, fetchMethod, form, backRoute, isSubmitDisabled, children, confirmationMessage } =
        props;

    const successMsg = 'Registro cadastrado com sucesso';
    const errorMsg = 'Houve um erro ao realizar o cadastro!';

    return (
        <>
            <>
                <title>{`Criar ${title} - ${APP_NAME}`}</title>
            </>
            <Stack gap="lg">
                <PageHeader
                    title={`Criar ${title}`}
                    breadcrumbItems={[...breadcrumbItems, { title: `Criar ${title}`, href: '#' }]}
                />

                <DataForm
                    form={form}
                    fetchMethod={fetchMethod}
                    title={title}
                    backRoute={backRoute}
                    submit={{ title: 'Salvar', successMsg, errorMsg, disabled: isSubmitDisabled, confirmationMessage }}
                >
                    {children}
                </DataForm>
            </Stack>
        </>
    );
}

export default CreateFormPage;
