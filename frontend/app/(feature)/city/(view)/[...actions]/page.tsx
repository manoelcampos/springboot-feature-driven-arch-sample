'use client';

import { useCallback, useEffect } from 'react';

import { Textarea, TextInput } from '@mantine/core';
import { useForm, zodResolver } from '@mantine/form';
import { useParams } from 'next/navigation';

import { CityService } from '@/app/(feature)/city/service/CityService';
import { SelectField } from '@/app/shared/component/molecule/SelectField/SelectField';
import { FormPage } from '@/app/shared/component/template/FormPage';
import { FormType } from '@/app/shared/component/template/FormPage/FormPage';
import useService from '@/app/shared/hook/useService';
import { parseActionsPageParams } from '@/app/shared/util';
import { StateService } from '@/app/(feature)/state/service/StateService';
import { CityDTO } from '@/app/model/models.generated';
import { CityValidationSchema } from '@/app/(feature)/city/validation/CityValidationSchema';

function Page() {
    const stateService = new StateService();
    const cityService = new CityService();

    const params = useParams();
    const { action, id } = parseActionsPageParams(params.actions);

    const { data, loading: isLoading } = useService(
        useCallback(() => (id ? cityService.getDtoById(id) : Promise.resolve(null)), [id])
    );
    const { data: statesData, loading: statesLoading } = useService(useCallback(() => stateService.getAll(), []));

    const form = useForm<CityDTO>({
        initialValues: { name: '', stateId: 0 },
        validate: zodResolver(CityValidationSchema),
    });

    useEffect(() => {
        data?.id && form.initialize(data);
    }, [data]);

    const isReadOnly = action === FormType.View;

    return (
        <FormPage
            title="City"
            service={stateService}
            form={form}
            formType={action}
            isLoading={isLoading || statesLoading}
        >
            <TextInput type="hidden" {...form.getInputProps('id')} disabled={isReadOnly} />

            <Textarea
                label="Name"
                autosize
                minRows={2}
                maxRows={5}
                {...form.getInputProps('name')}
                disabled={isReadOnly}
            />

            <SelectField
                label="State"
                data={statesData}
                getId={state => state.id}
                getDescription={state => state.name}
                searchable
                nothingFoundMessage="State não encontrado..."
                clearable
                {...form.getInputProps('stateId')}
                value={`${form.getInputProps('stateId').value}`}
                disabled={isReadOnly}
            />
        </FormPage>
    );
}

export default Page;
