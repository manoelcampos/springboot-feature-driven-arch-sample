import React from 'react';

import { Stack } from '@mantine/core';
import { UseFormReturnType } from '@mantine/form/lib/types';

import { BaseModel, DTORecord } from '@/app/model/models.generated';
import { PageHeader } from '@/app/shared/component';
import { BreadcrumbItem } from '@/app/shared/component/molecule/Breadcrumb/Breadcrumb';
import DataForm from '@/app/shared/component/organism/DataForm/DataForm';
import { APP_NAME } from '@/app/shared/const/environment';

interface EditFormPageProps<T extends BaseModel, D extends DTORecord<T>> {
    title: string;
    breadcrumbItems: BreadcrumbItem[];
    fetchMethod: (data: D) => Promise<T>;
    form: UseFormReturnType<D, (value: D) => D>;
    backRoute: string;
    isSubmitDisabled?: boolean;
    confirmationMessage?: string;
}

function EditFormPage<T extends BaseModel, D extends DTORecord<T>>(
    props: React.PropsWithChildren<EditFormPageProps<T, D>>
) {
    const { title, breadcrumbItems, fetchMethod, form, backRoute, isSubmitDisabled, confirmationMessage, children } =
        props;

    const successMsg = 'Cadastro atualizado com sucesso';
    const errorMsg = 'Houve um erro ao atualizar o cadastro';

    return (
        <>
            <>
                <title>{`Atualizar ${title} - ${APP_NAME}`}</title>
            </>
            <Stack gap="lg">
                <PageHeader
                    title={`Atualizar ${title}`}
                    breadcrumbItems={[...breadcrumbItems, { title: `Atualizar ${title}`, href: '#' }]}
                />

                <DataForm
                    form={form}
                    fetchMethod={fetchMethod}
                    title={title}
                    backRoute={backRoute}
                    submit={{
                        title: 'Atualizar',
                        successMsg,
                        errorMsg,
                        disabled: isSubmitDisabled,
                        confirmationMessage,
                    }}
                >
                    {children}
                </DataForm>
            </Stack>
        </>
    );
}

export default EditFormPage;
